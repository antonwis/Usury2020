package fi.metropolia.group8.view.Menu.Alias;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.view.Main.Loans.LoanListController;
import fi.metropolia.group8.view.Menu.MenubarController;
import fi.metropolia.group8.view.Overview.OverviewController;
import fi.metropolia.group8.view.PrimaryController;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Controller for alias manage window
 */
public class ModifyAliasController {

    @FXML
    public Button CancelAlias;
    @FXML
    public Button AddAlias;
    @FXML
    private TextField nameField;

    @FXML
    private TextField equityField;

    @FXML
    private TextArea descriptionArea;

    private AliasController aliasController;
    private Stage stage;
    private MenubarController menubarController;
    private PrimaryController primaryController;
    private OverviewController overviewController;
    private Alias alias;
    private LoanListController loanListController;

    /**
     * init method for alias modifying window sets needed controllers and sets aliases information to the fields
     * @param aliasController Alias Controller
     * @param stage Stage
     * @param menubarController Menubar Controller
     * @param primaryController Primary Controller
     * @param overviewController Overview Controller
     * @param alias Alias
     */
    public void init(LoanListController loanListController, AliasController aliasController, Stage stage, MenubarController menubarController, PrimaryController primaryController, OverviewController overviewController, Alias alias) {
        if (this.aliasController == null) {
            this.aliasController = aliasController;
            this.stage = stage;
            this.menubarController = menubarController;
            this.primaryController = primaryController;
            this.overviewController = overviewController;
            this.alias = alias;
            this.loanListController = loanListController;
        }

            nameField.setText(alias.getName());
            equityField.setText(Float.toString(alias.getEquity()));
            descriptionArea.setText(alias.getDescription());
    }

    /**
     * closes the alias modifying window and opens alias management window
     * @throws IOException Exception
     */
    public void closeAliasWindow() throws IOException {
        stage.close();
        Stage stage = new Stage();
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
        FXMLLoader modifyAlias = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Menu/Alias/ModifyAliases.fxml"));
        modifyAlias.setResources(resourceBundle);
        Parent root = modifyAlias.load();
        ManageAliasesController manageAliasesController = modifyAlias.getController();
        manageAliasesController.init(loanListController, aliasController, stage, menubarController, primaryController, overviewController);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Filters the alias list based on user and checks if the alias name already exists and checks all the other users inputs too then changes the details of alias and saves it to database
     */
    public void modifyAlias() {
        try {
            String name = nameField.getText();
            float equity = Float.parseFloat(equityField.getText());
            String description = descriptionArea.getText();

            FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());
            Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
            aliasList.setPredicate(aliasFilter);


            if(nameField.getText().isBlank()) {
                nameField.setPromptText("You must choose a name");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));

            }

            else{
                if(DataModel.getInstance().getCurrentAlias().getName().equals(alias.getName())){
                    DataModel.getInstance().setCurrentAlias(null);
                    alias.setName(name);
                    alias.setEquity(equity);
                    alias.setDescription(description);
                    DataModel.getInstance().saveAliasData(alias);
                    DataModel.getInstance().loadAliasData();
                    DataModel.getInstance().setCurrentAlias(alias);
                }else{
                    alias.setName(name);
                    alias.setEquity(equity);
                    alias.setDescription(description);
                    DataModel.getInstance().saveAliasData(alias);
                    DataModel.getInstance().loadAliasData();

                }
                primaryController.setCurrentAliasText();
                menubarController.updateView();
                overviewController.updateOverview();
                stage.close();

            }

        }catch (NumberFormatException numE){
            equityField.setText("");
            equityField.setPromptText("Equity must be a number");
            equityField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
        }

    }

}
