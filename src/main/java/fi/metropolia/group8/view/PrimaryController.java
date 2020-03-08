package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

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

    private Scene scene;


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

    public void setCurrentAliasText() {

        // Check if current alias exists
        if (DataModel.getInstance().getCurrentAlias() != null) {
            try {
                primaryCurrentAlias.setText(DataModel.getInstance().getCurrentAlias().getName());
                primaryCurrentEquity.setText(Float.toString(DataModel.getInstance().getCurrentAlias().getEquity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            primaryCurrentAlias.setText("None");
            primaryCurrentEquity.setText("");
        }
    }

    @FXML
    private void handleKeyInput(final InputEvent event) {
        if (event instanceof KeyEvent) {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.Q) {
                exitFunction();
            }
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
