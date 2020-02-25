package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
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

            VBox menuBar = FXMLLoader.load(getClass().getResource("menubar.fxml"));
            MenubarController menubarController = new MenubarController();


            primaryAnchor.getChildren().add(menuBar);
            AnchorPane.setLeftAnchor(menuBar,0d);
            AnchorPane.setTopAnchor(menuBar,0d);

            AliasController aliasController = new AliasController();
            AliasDataModel aliasDataModel = new AliasDataModel();


            aliasDataModel.loadData();
            aliasController.initModel(aliasDataModel);
            ObservableList<Alias> aliasList = aliasDataModel.getAliasList();
            menubarController.init(aliasList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
