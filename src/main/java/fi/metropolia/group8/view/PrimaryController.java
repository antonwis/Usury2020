package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.EventManager;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Main controller. Currently initializes other controllers and handles the status bar elements.
 */
public class PrimaryController {


    @FXML
    private Tab Loans;
    @FXML
    private Tab Overview;
    @FXML
    private Tab Calendar;
    @FXML
    private Button newAlias;
    @FXML
    private AnchorPane primaryAnchor;
    @FXML
    private MenuBar menuBar;
    @FXML
    private HBox primaryHbox;
    @FXML
    private Label primaryCurrentAlias;
    @FXML
    private Label primaryCurrentEquity;
    @FXML
    private Label primaryCurrentDate;

    private Scene scene;
    private MenubarController menubarController;

    /**
     * Initializes other controllers
     *
     * @param loginManager Login manager
     * @param sessionID SessionID
     */
    public void init(LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loanList = new FXMLLoader(getClass().getResource("loans.fxml"));
            Loans.setContent(loanList.load());
            LoanListController loanListController = loanList.getController();
            DataModel.getInstance().loadAliasData();

            /// ei välttämät tarvii tehä mut nii...
            FXMLLoader overview = new FXMLLoader(getClass().getResource("Overview.fxml"));
            Overview.setContent(overview.load());
            OverviewController overviewController = overview.getController();
            overviewController.initModel();

            // aloitusviesti (näyttää event log:ssa vasta loanlistControllerissä koska pepega tier structure projektilla)
            //EventManager.getInstance().printWelcome(DataModel.getInstance().getCurrentUser());

            loanListController.initModel(this, overviewController);
            AliasController aliasController = new AliasController();

            FXMLLoader menuBarF = new FXMLLoader(getClass().getResource("menubar.fxml"));
            VBox menuBar = menuBarF.load();

            primaryAnchor.getChildren().add(menuBar);
            AnchorPane.setLeftAnchor(menuBar, 0d);
            AnchorPane.setTopAnchor(menuBar, 0d);

            menubarController = menuBarF.getController();

            menubarController.init(loginManager, aliasController, this, loanListController, overviewController);
            menubarController.updateView();
            menubarController.initListener();

            setCurrentAliasText();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {

    }

    /**
     * Method for updating the status bar with updated alias selection, equity and current working date
     */
    public void setCurrentAliasText() {

        // Check if current alias has a selection
        if (DataModel.getInstance().getCurrentAlias() == null) {
            primaryCurrentAlias.setText("None");
            primaryCurrentEquity.setText("");
            primaryCurrentDate.setText(DataModel.getInstance().getCurrentUser().getCurrentDate().toString());
        } else {
            try {
                primaryCurrentAlias.setText(DataModel.getInstance().getCurrentAlias().getName());
                primaryCurrentEquity.setText(Float.toString(DataModel.getInstance().getCurrentAlias().getEquity()));
                primaryCurrentDate.setText(DataModel.getInstance().getCurrentUser().getCurrentDate().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for skipping one day ahead in time. Updates current user's working date and notifies event log manager.
     */
    public void fastForward() {
        DataModel.getInstance().getCurrentUser().setCurrentDate(DataModel.getInstance().getCurrentUser().getCurrentDate().plusDays(1));
        setCurrentAliasText();
        EventManager.getInstance().dateChanged(DataModel.getInstance().getCurrentUser().getCurrentDate());
    }

    /**
     * Method for setting the current working date manually
     * @param newDate new current date
     */
    public void selectCurrentDate(LocalDate newDate) {
        DataModel.getInstance().getCurrentUser().setCurrentDate(newDate);
        setCurrentAliasText();
        EventManager.getInstance().dateChanged(newDate);
    }

    @FXML
    private void handleExitAction(final ActionEvent event) {
        exitFunction();
    }

    private void exitFunction() {
        Platform.exit();
    }
}
