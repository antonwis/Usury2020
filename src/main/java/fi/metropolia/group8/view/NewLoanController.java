package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class NewLoanController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField interestField;

    @FXML
    private DatePicker dueDatepicker;

    @FXML
    private Button loanConfirmButton;

    @FXML
    private Button loanCancelButton;

    private static Stage newLoan;

    @FXML
    void loanCancel(ActionEvent event) {
        newLoan.close();
    }

    @FXML
    void loanConfirm(ActionEvent event) {
        UsuryDAO u = new UsuryDAO();
        Alias a = new Alias("Teppo Testi","Nenäklubin jäsen",9001);
        Victim v = new Victim(nameField.getText(),addressField.getText(),descriptionField.getText());
        Loan loan = new Loan(a,Float.parseFloat(amountField.getText()),v,LocalDate.now(),LocalDate.from(dueDatepicker.getValue()),Float.parseFloat(interestField.getText()));
        u.createAlias(a);
        u.createVictim(v);
        u.createLoan(loan);
        newLoan.close();
    }

    public void display() throws IOException {
        newLoan = new Stage();
        newLoan.initModality(Modality.APPLICATION_MODAL);
        newLoan.setResizable(false);
        FXMLLoader loan = new FXMLLoader();
        AnchorPane newLoanFxml = FXMLLoader.load(NewLoanController.class.getResource("newLoan.fxml"));
        NewLoanController newLoanController = loan.getController();
        Scene scene = new Scene(newLoanFxml, 430, 525);
        newLoan.setScene(scene);
        newLoan.show();
    }
}
