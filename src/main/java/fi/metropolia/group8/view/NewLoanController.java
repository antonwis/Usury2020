package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import fi.metropolia.group8.model.Victim;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private Stage stage;
    private LoanListController loanListController;
    private LoanDataModel model;

    @FXML
    void loanCancel() {
        System.out.println(model);
        System.out.println(loanListController);
        System.out.println(stage);
        stage.close();
    }

    @FXML
    void loanConfirm() {
        Alias alias = new Alias("Teppo Testi", "Nenäklubin jäsen", 9001);
        Victim victim = new Victim(nameField.getText(), addressField.getText(), descriptionField.getText());
        Loan loan = new Loan(alias, Float.parseFloat(amountField.getText()), victim, LocalDate.now(), LocalDate.from(dueDatepicker.getValue()), Float.parseFloat(interestField.getText()));
        model.saveData(loan, victim, alias);
        loanListController.updateView();
        stage.close();
    }

    public void TransferMemes(LoanListController loanListController, LoanDataModel model, Stage stage) {
        this.loanListController = loanListController;
        this.model = model;
        this.stage = stage;
    }
}
