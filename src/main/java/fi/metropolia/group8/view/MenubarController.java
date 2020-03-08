package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;


public class MenubarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu aliasMenu;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem logoutButton;

    private ObservableList<Alias> aliasList;
    private LoginManager loginManager;
    private AliasController aliasController;
    private PrimaryController primaryController;
    private LoanListController loanListController;
    private Menu sub;


    public void init( LoginManager loginManager, AliasController aliasController, PrimaryController primaryController, LoanListController loanListController){
        sub = new Menu("Select Alias");
        aliasMenu.getItems().add(sub);
        this.loginManager = loginManager;
        this.aliasController = aliasController;
        this.primaryController = primaryController;
        this.loanListController = loanListController;

        DataModel.getInstance().loadAliasData();

        FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());

        // ID:tä ei voinu verrata suoraan jostain syystä. Pitäs tehä oma DB kutsu koko paskalle mut tämäkin toimii.
        Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        aliasList.setPredicate(aliasFilter);

        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        logoutButton.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        int i = 0;

        for(Alias alias : aliasList) {

            CheckMenuItem menuItem = new CheckMenuItem("Item");
            menuItem.setText(aliasList.get(i).getName());
            String s = ""+(i+1);
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.getKeyCode(s), KeyCombination.CONTROL_DOWN));
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    for(int i = 0; i<sub.getItems().size();i++){
                        CheckMenuItem c = (CheckMenuItem) sub.getItems().get(i);
                        c.setSelected(false);
                    }
                    DataModel.getInstance().setCurrentAlias(alias);
                    primaryController.setCurrentAliasText();
                    loanListController.refreshLoans();
                    menuItem.setSelected(true);
                }
            });
            sub.getItems().add(menuItem);
            i++;
        }
    }
    public void updateView(){
        DataModel.getInstance().loadAliasData();
        FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());

        // ID:tä ei voinu verrata suoraan jostain syystä. Pitäs tehä oma DB kutsu koko paskalle mut tämäkin toimii.
        Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        aliasList.setPredicate(aliasFilter);
        sub.getItems().clear();
        int i = 0;

        for(Alias alias : aliasList) {

            CheckMenuItem menuItem = new CheckMenuItem("Item");
            menuItem.setText(aliasList.get(i).getName());
            String s = ""+(i+1);
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.getKeyCode(s), KeyCombination.CONTROL_DOWN));
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    for(int i = 0; i<sub.getItems().size();i++){
                        CheckMenuItem c = (CheckMenuItem) sub.getItems().get(i);
                        c.setSelected(false);
                    }
                    DataModel.getInstance().setCurrentAlias(alias);
                    primaryController.setCurrentAliasText();
                    loanListController.refreshLoans();
                    menuItem.setSelected(true);
                }
            });
            sub.getItems().add(menuItem);
            i++;
        }
    }

    public void exitApp(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }

    public void logout(javafx.event.ActionEvent actionEvent) {
        loginManager.logout();
    }

    public void addNewAlias(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader alias = new FXMLLoader(getClass().getResource("newAlias.fxml"));
        Parent root = alias.load();
        aliasController = alias.getController();
        aliasController.display(this, stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void modifyAliases(javafx.event.ActionEvent actionEvent) throws  IOException{
        Stage stage = new Stage();
        FXMLLoader modifyAlias = new FXMLLoader(getClass().getResource("modifyAliases.fxml"));
        Parent root = modifyAlias.load();
        ModifyAliasesController modifyAliasesController = modifyAlias.getController();
        modifyAliasesController.init(aliasController, stage, this, primaryController);
        stage.setScene(new Scene(root));
        stage.show();
    }


}
