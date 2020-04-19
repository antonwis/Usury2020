package fi.metropolia.group8.view.Menu.Alias;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.view.Main.Loans.LoanListController;
import fi.metropolia.group8.view.Menu.MenubarController;
import fi.metropolia.group8.view.Overview.OverviewController;
import fi.metropolia.group8.view.PrimaryController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.Predicate;

/**
 * Controller class for creating new alias
 */
public class AliasController {


    @FXML
    public Button AddAlias;
    @FXML
    public Button CancelAlias;
    @FXML
    private TextField nameField;

    @FXML
    private TextField equityField;

    @FXML
    private TextArea descriptionArea;

    private MenubarController menubarController;
    private Stage stage;
    private PrimaryController primaryController;
    private OverviewController overviewController;
    private LoanListController loanListController;

    /**
     * Method for creating new alias from add new alias window when add alias button is clicked where the user input is also checked
     * and if the alias already exists
     */
    @FXML
    void addNewAlias() {

        try {
            String name = nameField.getText();
            int equity = Integer.parseInt(equityField.getText());
            String description = descriptionArea.getText();
            boolean alreadyExists = false;
            FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());

            Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
            aliasList.setPredicate(aliasFilter);
            for (Alias alias : aliasList) {

                if (alias.getName().equals(name)) {
                    alreadyExists = true;
                }
            }

            if (nameField.getText().isEmpty()) {
                nameField.setPromptText("You must choose a name");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));

            } else if (alreadyExists) {
                nameField.setText("");
                nameField.setPromptText("Alias already exists");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
            } else {
                DataModel.getInstance().createAlias(name, description, equity);
                DataModel.getInstance().loadAliasData();
                ObservableList<Alias> list = DataModel.getInstance().getAliasList();
                primaryController.setCurrentAliasText();
                menubarController.updateView();
                overviewController.updateOverview();
                loanListController.refreshLoans();
                stage.close();
            }

        } catch (NumberFormatException numE) {
            equityField.setText("");
            equityField.setPromptText("Equity must be a number");
            equityField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
        }

    }

    /**
     * method for closing add new alias window when cancel is clicked
     */
    @FXML
    void closeAliasWindow() {
        stage.close();
    }

    /**
     * Method that bring all needed instances of other controllers
     *
     * @param menubarController Menubar Controller
     * @param stage Stage
     * @param primaryController Primary Controller
     * @param overviewController Overview Controller
     */
    public void display(LoanListController loanListController, MenubarController menubarController, Stage stage, PrimaryController primaryController, OverviewController overviewController) {
        if (this.menubarController == null) {
            this.menubarController = menubarController;
            this.stage = stage;
            this.primaryController = primaryController;
            this.overviewController = overviewController;
            this.loanListController = loanListController;
        }
    }


}