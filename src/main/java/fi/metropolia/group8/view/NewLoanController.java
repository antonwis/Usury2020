package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
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
    private LoanDataModel loanDataModel;
    private AliasDataModel aliasDataModel;
    private LoanCalculator loanCalculator;

    @FXML
    void loanCancel() {
        System.out.println(loanDataModel);
        System.out.println(loanListController);
        System.out.println(stage);
        stage.close();
    }

    @FXML
    void loanConfirm() {
        Victim victim = new Victim(nameField.getText(), addressField.getText(), descriptionField.getText());
        Loan loan = new Loan(
                aliasDataModel.getCurrentAlias(),
                Float.parseFloat(amountField.getText()),
                victim,
                LocalDate.now(),
                LocalDate.from(dueDatepicker.getValue()),
                Float.parseFloat(interestField.getText())
            );
        loanDataModel.saveData(loan, victim, aliasDataModel.getCurrentAlias());

        loanCalculator = new LoanCalculator(loanDataModel, aliasDataModel);
        loanCalculator.updateEquity(aliasDataModel.getCurrentAlias(), loan);
        loanListController.refreshLoans();
        stage.close();
    }

    public void TransferMemes(LoanListController loanListController, LoanDataModel loanDataModel, AliasDataModel aliasDataModel, Stage stage) {
        this.loanListController = loanListController;
        this.loanDataModel = loanDataModel;
        this.aliasDataModel = aliasDataModel;
        this.stage = stage;
    }
}
