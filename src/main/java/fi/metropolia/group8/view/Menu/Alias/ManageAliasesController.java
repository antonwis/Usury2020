package fi.metropolia.group8.view.Menu.Alias;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.view.Main.Loans.LoanListController;
import fi.metropolia.group8.view.Menu.MenubarController;
import fi.metropolia.group8.view.Menu.Settings.LanguageController;
import fi.metropolia.group8.view.Menu.Settings.SettingsController;
import fi.metropolia.group8.view.Overview.OverviewController;
import fi.metropolia.group8.view.PrimaryController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Controller class for alias management window
 */
public class ManageAliasesController {

    @FXML
    public Button CancelAlias;
    @FXML
    private VBox aliasBox;

    private AliasController aliasController;
    private Stage stage;
    private MenubarController menubarController;
    private PrimaryController primaryController;
    private OverviewController overviewController;
    private LoanListController loanListController;


    /**
     * Initialization method for Manage aliases window where all needed instances of controllers are initialized
     * @param aliasController Alias Controller
     * @param stage Stage
     * @param menubarController Menubar Controller
     * @param primaryController Primary Controller
     * @param overviewController Overview Controller
     */
    public void init(LoanListController loanListController, AliasController aliasController, Stage stage, MenubarController menubarController, PrimaryController primaryController, OverviewController overviewController){
        if(this.aliasController == null) {
            this.aliasController = aliasController;
            this.stage = stage;
            this.menubarController = menubarController;
            this.primaryController = primaryController;
            this.overviewController = overviewController;
            this.loanListController = loanListController;
        }
        updateView();
    }

    /**
     * Method that filters aliases from alias list based on user and creates all the needed javafx elements for managing aliases also set listeners for buttons delete and modify
     */
    public void updateView() {
        LanguageController languageController = new LanguageController();

        FilteredList<Alias> filteredList = new FilteredList<>(DataModel.getInstance().getAliasList());

        Predicate<Alias> aliasFilter = fil -> fil.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        filteredList.setPredicate(aliasFilter);
        aliasBox.getChildren().clear();
        for (Alias alias : filteredList) {

            HBox hBox1 = new HBox();
            hBox1.setSpacing(10);
            Label label = new Label(alias.getName());
            label.setPrefWidth(300);
            label.setMinWidth(50);
            Button modify = new Button(languageController.getTranslation("modify"));
            modify.setMinWidth(60);

            modify.setOnAction(new EventHandler<>() {
                /**
                 * Handler for modify button that opens new window where you can edit alias
                 *
                 * @param actionEvent Action Event
                 */
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        DataModel.getInstance().setCurrentAlias(alias);
                        stage.close();
                        Stage stage = new Stage();
                        Locale locale = Locale.getDefault();
                        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
                        FXMLLoader modifyAlias = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Menu/Alias/ModifyAlias.fxml"));
                        modifyAlias.setResources(resourceBundle);
                        Parent root = modifyAlias.load();
                        ModifyAliasController modifyAliasController = modifyAlias.getController();
                        modifyAliasController.init(loanListController, aliasController, stage, menubarController, primaryController, overviewController, alias);
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            Button delete = new Button(languageController.getTranslation("delete"));
            delete.setMinWidth(60);
            delete.setOnAction(new EventHandler<>() {
                /**
                 * Handler for delete button that gets the alias and deletes it from the list and the database also if the alias is current alias it set current alias null
                 *
                 * @param actionEvent Action Event
                 */
                @Override
                public void handle(ActionEvent actionEvent) {

                    DataModel.getInstance().loadLoanData();
                    DataModel.getInstance().loadAliasData();
                    FilteredList<Loan> filteredList = new FilteredList<>(DataModel.getInstance().getLoanList());

                    ObjectProperty<Predicate<Loan>> userFilter = new SimpleObjectProperty<>();
                    ObjectProperty<Predicate<Loan>> aliasFilter = new SimpleObjectProperty<>();

                    userFilter.bind(Bindings.createObjectBinding(() ->
                            i -> i.getOwner().getUser().getName().equals(alias.getUser().getName())));


                    aliasFilter.bind(Bindings.createObjectBinding(() ->
                            i -> i.getOwner().getName().equals(alias.getName())));

                    filteredList.predicateProperty().bind(Bindings.createObjectBinding(
                            () -> userFilter.get().and(aliasFilter.get()),
                            userFilter, aliasFilter));
                    for (Loan loan : filteredList) {
                        DataModel.getInstance().deleteLoan(loan);
                    }

                    DataModel.getInstance().deleteAlias(alias);
                    System.out.println(DataModel.getInstance().getCurrentAlias());
                    DataModel.getInstance().loadAliasData();
                    menubarController.updateView();
                    primaryController.setCurrentAliasText();
                    overviewController.updateOverview();
                    loanListController.refreshLoans();
                    updateView();
                }
            });
            hBox1.getChildren().addAll(label,modify,delete);
            aliasBox.getChildren().add(hBox1);
        }
    }

    /**
     * Closes alias manage window
     */
    public void closeWindow(){
        stage.close();
    }
}
