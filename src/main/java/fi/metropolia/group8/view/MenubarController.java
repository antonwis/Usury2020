package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * Controller class for the menubar
 */
public class MenubarController {

    @FXML
    public VBox vbox;
    @FXML
    public MenuBar menuBar;
    @FXML
    public Menu systemMenu;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu aliasMenu;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem logoutButton;
    @FXML
    private MenuItem settings;

    private LoginManager loginManager;
    private AliasController aliasController;
    private PrimaryController primaryController;
    private LoanListController loanListController;
    private OverviewController overviewController;
    private Menu sub;

    ListChangeListener<Alias> changeListener;

    /**
     * Method that initializes menubar and gets all the necessary controllers
     * @param loginManager Login Manager
     * @param aliasController Alias Controller
     * @param primaryController Primary Controller
     * @param loanListController LoanList Controller
     * @param overviewController Overview Controller
     */
    public void init(LoginManager loginManager, AliasController aliasController, PrimaryController primaryController, LoanListController loanListController, OverviewController overviewController){

        if (this.loginManager == null && this.primaryController == null) {

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
        }
    }

    public void initListener() {
        changeListener = change -> {
            if(change.next()) {
                updateView();
            }

        };
        DataModel.getInstance().addAliasListChangeListener(changeListener);
    }

    /**
     * updates the choose alias menu when a new alias is added or deleted and creates a handler for alias menuItems
     */
    public void updateView() {

        //DataModel.getInstance().loadAliasData();

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
            menuItem.setOnAction(new EventHandler<>() {
                /**
                 * Handler for alias menuItems that set every other checkMenuItem not selected when one is clicked and sets an active alias
                 *
                 */
                @Override
                public void handle(ActionEvent actionEvent) {
                    for (int j = 0; j < sub.getItems().size(); j++) {
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

    /**
     * method for exiting closing the app when exit is clicked
     */
    public void exitApp() {
        System.exit(0);
    }

    /**
     * method for logging out when logout is clicked
     */
    public void logout() {
        DataModel.getInstance().setCurrentAlias(null);
        primaryController.setCurrentAliasText();
        loginManager.logout();
    }

    /**
     * method for opening add new alias window
     * @throws IOException Exception
     */
    public void addNewAlias() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader alias = new FXMLLoader(getClass().getResource("newAlias.fxml"));
        Parent root = alias.load();
        aliasController = alias.getController();
        aliasController.display(loanListController,this, stage,primaryController, overviewController);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * method for opening alias manage window
     * @throws IOException Exception
     */
    @FXML
    public void modifyAliases() throws  IOException{
        Stage stage = new Stage();
        FXMLLoader modifyAlias = new FXMLLoader(getClass().getResource("modifyAliases.fxml"));
        Parent root = modifyAlias.load();
        ManageAliasesController manageAliasesController = modifyAlias.getController();
        manageAliasesController.init(loanListController,aliasController, stage, this, primaryController, overviewController);
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void openSettings() throws IOException{
        Stage stage = new Stage();
        FXMLLoader settings = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent root = settings.load();
        SettingsController settingsController = settings.getController();
        settingsController.init();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void debtorBook() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader book = new FXMLLoader(getClass().getResource("BookOfDebtors.fxml"));
        Parent root = book.load();
        BookOfDebtorsController b = book.getController();
        b.init();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
