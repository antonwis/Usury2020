package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class LoanListController {


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
    private NewLoanController newLoanController = new NewLoanController();

    @FXML
    void newLoan(ActionEvent event) throws IOException {
        newLoanController.display();
    }

    private LoanDataModel model;
    private UsuryDAO usuryDAO = new UsuryDAO();
    private ObservableList<Loan> plsno;


    public void initModel(LoanDataModel model) throws IOException {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
        plsno = FXCollections.observableList(usuryDAO.readLoans());
        LoanTableView.setItems(plsno);
        //LoanTableView.setItems(model.getLoanList());
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

        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Debtor.setCellValueFactory(victim -> new SimpleObjectProperty(victim.getValue().getVictim().getName()));
        Amount.setCellValueFactory(amount -> amount.getValue().valueProperty().asObject());
        DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        //Date.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        //DueDate.setCellValueFactory(dueDate -> dueDate.getValue().dueDateProperty());
        //Lender.setCellValueFactory(lender -> new SimpleObjectProperty(lender.getValue().getOwner().getName()));
        //Interest.setCellValueFactory(interest -> interest.getValue().interestProperty().asObject());
    }
}


