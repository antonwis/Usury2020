package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * Controller class for creating new alias
 */
public class AliasController {



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
     * @param e
     */
    @FXML
    void addNewAlias(ActionEvent e) {

        try {
            String name = nameField.getText();
            Integer equity = Integer.parseInt(equityField.getText());
            String description = descriptionArea.getText();
            Boolean alreadyExists = false;
            FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());

            Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
            aliasList.setPredicate(aliasFilter);
            for(Alias alias: aliasList){

                if(alias.getName().equals(name)){
                    alreadyExists = true;
                }
            }
            System.out.println(name+":tässä on nimi");
            if(nameField.getText().isEmpty()) {
                nameField.setPromptText("You must choose a name");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));

            }
            else if(alreadyExists == true){
                nameField.setText("");
                nameField.setPromptText("Alias already exists");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
            }
            else{
                DataModel.getInstance().createAlias(name, description, equity);
                DataModel.getInstance().loadAliasData();
                ObservableList<Alias> list = DataModel.getInstance().getAliasList();
                primaryController.setCurrentAliasText();
                menubarController.updateView();
                overviewController.updateOverview();
                loanListController.refreshLoans();
                stage.close();
            }

        }catch (NumberFormatException numE){
            equityField.setText("");
            equityField.setPromptText("Equity must be a number");
            equityField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
        }

    }

    /**
     * method for closing add new alias window when cancel is clicked
     * @param e
     * @throws IOException
     */
    @FXML
    void closeAliasWindow(ActionEvent e) throws IOException {
        stage.close();
    }

    /**
     * Method that bring all needed instances of other controllers
     * @param menubarController
     * @param stage
     * @param primaryController
     * @param overviewController
     * @throws IOException
     */
    public void display(LoanListController loanListController, MenubarController menubarController, Stage stage,PrimaryController primaryController, OverviewController overviewController) throws IOException {
        if (this.menubarController == null) {
            this.menubarController = menubarController;
            this.stage = stage;
            this.primaryController = primaryController;
            this.overviewController = overviewController;
            this.loanListController = loanListController;
        }
    }


}