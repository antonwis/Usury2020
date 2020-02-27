package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import fi.metropolia.group8.model.Victim;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class LoanListController implements Initializable {


    @FXML
    private AnchorPane LoanDetailsAnchorPane;
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

    @FXML
    void newLoan(ActionEvent event) throws IOException {
      NewLoanController.display();
    }

    private LoanDataModel model;


    public void initModel(LoanDataModel model) throws IOException {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
        LoanTableView.setItems(model.getLoanList());
        ////////////////////////////////////////////
        LoanTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> model.setCurrentLoan(newSelection));
        model.currentLoanProperty().addListener((obs, oldLoan, newLoan) -> {
            if (newLoan == null) {
                LoanTableView.getSelectionModel().clearSelection();
            } else {
                LoanTableView.getSelectionModel().select(newLoan);
                try {
                    FXMLLoader loanDetails = new FXMLLoader(getClass().getResource("loanDetails.fxml"));
                    LoanDetailsAnchorPane.getChildren().setAll((Node) loanDetails.load());
                    LoanDetailController loanDetailController = loanDetails.getController();
                    loanDetailController.display(model);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Lender.setCellValueFactory(lender -> new SimpleObjectProperty(lender.getValue().getOwner().getName()));
        Debtor.setCellValueFactory(victim -> new SimpleObjectProperty(victim.getValue().getVictim().getName()));
        Amount.setCellValueFactory(amount -> amount.getValue().valueProperty().asObject());
        //Interest.setCellValueFactory(interest -> interest.getValue().interestProperty().asObject());
        //Date.setCellValueFactory(startDate -> startDate.getValue().startDateProperty());
        //DueDate.setCellValueFactory(dueDate -> dueDate.getValue().dueDateProperty());
    }

    //System.out.println(model.getCurrentLoan());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}


