package fi.metropolia.group8.view;

import com.sun.javafx.fxml.FXMLLoaderHelper;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.EventManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Main controller
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

    /**
     * initializes all controllers for software
     *
     * @param loginManager
     * @param sessionID
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

            MenubarController menubarController = menuBarF.getController();

            menubarController.init(loginManager, aliasController, this, loanListController, overviewController);
            menubarController.updateView();

            setCurrentAliasText();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {

    }

    /**
     * method for setting text for currently selected alias
     */
    public void setCurrentAliasText() {

        // Check if current alias exists
        if (DataModel.getInstance().getCurrentAlias() != null) {
            try {
                primaryCurrentAlias.setText(DataModel.getInstance().getCurrentAlias().getName());
                primaryCurrentEquity.setText(Float.toString(DataModel.getInstance().getCurrentAlias().getEquity()));
                primaryCurrentDate.setText(DataModel.getInstance().getCurrentUser().getCurrentDate().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            primaryCurrentAlias.setText("None");
            primaryCurrentEquity.setText("");
        }
    }

    @FXML
    private void handleExitAction(final ActionEvent event) {
        exitFunction();
    }

    private void exitFunction() {
        Platform.exit();
    }
}
