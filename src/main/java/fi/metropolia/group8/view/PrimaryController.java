package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
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
            //////
            loanDataModel.loadTestData();
            ////
            loanListController.initModel(loanDataModel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
