package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    private OverviewController overviewController;
    private Menu sub;


    public void init(LoginManager loginManager, AliasController aliasController, PrimaryController primaryController, LoanListController loanListController, OverviewController overviewController){
        sub = new Menu("Select Alias");
        aliasMenu.getItems().add(sub);
        this.loginManager = loginManager;
        this.aliasController = aliasController;
        this.primaryController = primaryController;
        this.loanListController = loanListController;
        this.overviewController = overviewController;
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        logoutButton.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        updateView();


    }
    public void updateView() {

        DataModel.getInstance().loadAliasData();

        FilteredList<Alias> filteredList = new FilteredList<>(DataModel.getInstance().getAliasList());
        Predicate<Alias> aliasFilter = fil -> fil.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        filteredList.setPredicate(aliasFilter);

        sub.getItems().clear();


        for(int i = 0; i<filteredList.size();i++) {

            CheckMenuItem menuItem = new CheckMenuItem("Item");
            menuItem.setText(filteredList.get(i).getName());
            String s = ""+(i+1);
            Alias alias = filteredList.get(i);
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.getKeyCode(s), KeyCombination.CONTROL_DOWN));
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    for(int j = 0; j<sub.getItems().size();j++){
                        CheckMenuItem c = (CheckMenuItem) sub.getItems().get(j);
                        c.setSelected(false);
                    }
                    DataModel.getInstance().setCurrentAlias(alias);
                    primaryController.setCurrentAliasText();
                    loanListController.refreshLoans();
                    overviewController.updateOverview();
                    menuItem.setSelected(true);
                }
            });
            sub.getItems().add(menuItem);
        }
        for(MenuItem menuItem : sub.getItems()){
            if(DataModel.getInstance().getCurrentAlias() != null && menuItem.getText().equals(DataModel.getInstance().getCurrentAlias().getName())) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) menuItem;
                checkMenuItem.setSelected(true);
            }
        }
    }

    public void exitApp(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }

    public void logout(javafx.event.ActionEvent actionEvent) {
        DataModel.getInstance().setCurrentAlias(null);
        loginManager.logout();
    }

    public void addNewAlias(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader alias = new FXMLLoader(getClass().getResource("newAlias.fxml"));
        Parent root = alias.load();
        aliasController = alias.getController();
        aliasController.display(this, stage,primaryController, overviewController);
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
