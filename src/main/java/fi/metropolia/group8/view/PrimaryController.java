package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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

    private AnchorPane primaryAnchor;
    @FXML
    private MenuBar menuBar;


    @FXML
    void createNewAlias(ActionEvent e) throws IOException {
        AliasController.display();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {


            FXMLLoader loanList = new FXMLLoader(getClass().getResource("loans.fxml"));
            Loans.setContent(loanList.load());
            LoanListController loanListController = loanList.getController();
            LoanDataModel loanDataModel = new LoanDataModel();
            loanDataModel.loadTestData(); // test
            loanListController.initModel(loanDataModel);




            FXMLLoader menuBarF = new FXMLLoader(getClass().getResource("menubar.fxml"));
            VBox menuBar = menuBarF.load();

            primaryAnchor.getChildren().add(menuBar);
            AnchorPane.setLeftAnchor(menuBar,0d);
            AnchorPane.setTopAnchor(menuBar,0d);

            MenubarController menubarController = menuBarF.getController();


            AliasDataModel aliasDataModel = new AliasDataModel();
            aliasDataModel.loadData();
            ObservableList<Alias> aliasList = aliasDataModel.getAliasList();

            AliasController aliasController = new AliasController();
            aliasController.initModel(aliasDataModel);

            menubarController.init(aliasList);

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
