package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {



    @FXML
    private Tab Loans;
    @FXML
    private Tab Summary;
    @FXML
    private Tab Calendar;
    @FXML
    private Button newAlias;
    @FXML
    private MenuBar menuBar;

    @FXML
    void createNewAlias(ActionEvent e) throws IOException {
        AliasController.display();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            menuBar.setFocusTraversable(true);

            FXMLLoader loanList = new FXMLLoader(getClass().getResource("loans.fxml"));
            Loans.setContent(loanList.load());
            LoanListController loanListController = loanList.getController();
            LoanDataModel loanDataModel = new LoanDataModel();
            //////
            loanDataModel.loadTestData();
            ////
            loanListController.initModel(loanDataModel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleKeyInput(final InputEvent event) {
        if (event instanceof KeyEvent) {
            final KeyEvent keyEvent = (KeyEvent) event;
            if(keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.Q) {
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
