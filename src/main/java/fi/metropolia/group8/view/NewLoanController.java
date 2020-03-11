package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * controller for newLoanView
 */
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
    private LoanCalculator loanCalculator;
    private PrimaryController primaryController;
    private OverviewController overviewController;

    /**
     * closes newLoan window
     */
    @FXML
    void loanCancel() {
        stage.close();
    }

    /**
     * Takes input values from fields and creates a new victim and loan. Updates loanList view
     */
    @FXML
    void loanConfirm() {
        Victim victim = new Victim(nameField.getText(), addressField.getText(), descriptionField.getText());
        Loan loan = new Loan(
                DataModel.getInstance().getCurrentAlias(),
                Float.parseFloat(amountField.getText()),
                victim,
                LocalDate.now(),
                LocalDate.from(dueDatepicker.getValue()),
                Float.parseFloat(interestField.getText())
            );
        DataModel.getInstance().saveLoanData(loan, victim, DataModel.getInstance().getCurrentAlias());
        DataModel.getInstance().loadAliasData();
        loanCalculator = new LoanCalculator();
        loanCalculator.updateEquity(DataModel.getInstance().getCurrentAlias(), loan);
        loanListController.refreshLoans();
        //primaryController.setCurrentAliasText();
        overviewController.updateOverview();
        stage.close();
    }

    /**
     * gets required controllers
     * @param loanListController
     * @param stage
     * @param primaryController
     * @param overviewController
     */
    public void TransferMemes(LoanListController loanListController, Stage stage, PrimaryController primaryController, OverviewController overviewController) {
        this.loanListController = loanListController;
        this.primaryController = primaryController;
        this.stage = stage;
        this.overviewController = overviewController;
        dueDatepicker.setEditable(false);

    }
}
