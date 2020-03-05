package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


public class LoanListController {


    @FXML
    private VBox LoanDetailsVbox;
    @FXML
    private TableView<Loan> LoanTableView;
    @FXML
    private TableColumn<Loan, Long> Id;
    @FXML
    private TableColumn<Loan, Alias> Lender;
    @FXML
    private TableColumn<Loan, Float> Amount;
    @FXML
    private TableColumn<Loan, Victim> Debtor;
    @FXML
    private TableColumn<Loan, Float> Interest;
    @FXML
    private TableColumn<Loan, LocalDate> Date;
    @FXML
    private TableColumn<Loan, LocalDate> DueDate;
    @FXML
    private Button newLoanButton;

    private NewLoanController newLoanController;

    private LoanDataModel loanDataModel;
    private AliasDataModel aliasDataModel;

    @FXML
    void newLoan() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        FXMLLoader loan = new FXMLLoader(getClass().getResource("newLoan.fxml"));
        Parent root = loan.load();
        NewLoanController newLoanController = loan.getController();
        newLoanController.TransferMemes(this, loanDataModel, aliasDataModel, stage);
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void updateView() {
        loanDataModel.loadData();
        LoanTableView.setItems(loanDataModel.getLoanList());
    }


    public void initModel(LoanDataModel loanDataModel, AliasDataModel aliasDataModel) throws IOException {
        if (this.loanDataModel != null && this.aliasDataModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.loanDataModel = loanDataModel;
        this.aliasDataModel = aliasDataModel;
        updateView();

        LoanTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> loanDataModel.setCurrentLoan(newSelection));
        loanDataModel.currentLoanProperty().addListener((obs, oldLoan, newLoan) -> {
            if (newLoan == null) {
                LoanTableView.getSelectionModel().clearSelection();
            } else {
                LoanTableView.getSelectionModel().select(newLoan);
                try {
                    FXMLLoader loanDetails = new FXMLLoader(getClass().getResource("loanDetails.fxml"));
                    LoanDetailsVbox.getChildren().setAll((Node) loanDetails.load());
                    LoanDetailController loanDetailController = loanDetails.getController();
                    loanDetailController.display(loanDataModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Debtor.setCellValueFactory(victim -> new SimpleObjectProperty(victim.getValue().getVictim().getName()));
        Amount.setCellValueFactory(amount -> amount.getValue().valueProperty().asObject());
        DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }
}


