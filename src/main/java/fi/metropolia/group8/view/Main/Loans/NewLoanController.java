package fi.metropolia.group8.view.Main.Loans;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanCalculator;
import fi.metropolia.group8.view.Menu.Settings.LanguageController;
import fi.metropolia.group8.view.Overview.OverviewController;
import fi.metropolia.group8.view.PrimaryController;
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
    private PrimaryController primaryController;
    private OverviewController overviewController;
    private LanguageController languageController;

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
        boolean name = nameField.getText().isEmpty();
        boolean address = addressField.getText().isEmpty();
        boolean description = descriptionField.getText().isEmpty();
        boolean amount = amountField.getText().isEmpty();
        boolean interest = interestField.getText().isEmpty();
        boolean duedate = dueDatepicker.getEditor().getText().isEmpty();

        prompt(nameField,languageController.getTranslation("required"));
        prompt(addressField,languageController.getTranslation("required"));
        prompt(amountField,languageController.getTranslation("required_n"));
        prompt(interestField,languageController.getTranslation("required_n"));
        if (descriptionField.getText().isEmpty()) {
            descriptionField.setPromptText(languageController.getTranslation("required"));
        } else {
            descriptionField.setPromptText("");
        }
        if (dueDatepicker.getEditor().getText().isEmpty()) {
            dueDatepicker.setPromptText(languageController.getTranslation("required_d"));
        } else {
            dueDatepicker.setPromptText("");
        }
        if (!name && !address && !description && !amount && !interest && !duedate) {
            try {
                if (Float.parseFloat(interestField.getText()) > 1000) {
                    interestField.setText("1000");
                }
                Loan loan = DataModel.getInstance().createLoan(
                        DataModel.getInstance().getCurrentAlias(),
                        Float.parseFloat(amountField.getText()),
                        DataModel.getInstance().createVictim(nameField.getText(), addressField.getText(), descriptionField.getText()),
                        LocalDate.now(),
                        LocalDate.from(dueDatepicker.getValue()),
                        Float.parseFloat(interestField.getText())
                );
                LoanCalculator.getInstance().updateEquity(loan);
                loanListController.refreshLoans();
                primaryController.setCurrentAliasText();
                overviewController.updateOverview();
                stage.close();
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }
        }
    }

    /**
     * Checks if message is empty and sets appropriate message
     * @param t Textfield to be checked
     * @param msg Message to be displayed
     */
    private void prompt(TextField t, String msg) {
        if (t.getText().isEmpty()) {
            t.setPromptText(msg);
        }
    }

        /**
         * gets required controllers
         *
         * @param loanListController Loanlist Controller
         * @param stage Stage
         * @param primaryController Primary Controller
         * @param overviewController Overview Controller
         */
        public void init(LoanListController loanListController, Stage stage, PrimaryController primaryController, OverviewController
        overviewController){
            this.loanListController = loanListController;
            this.primaryController = primaryController;
            this.stage = stage;
            this.overviewController = overviewController;
            languageController = new LanguageController();


            dueDatepicker.setEditable(false);
            interestField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*")) return;
                interestField.setText(newValue.replaceAll("[^\\d]", ""));
            });

            amountField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*")) return;
                amountField.setText(newValue.replaceAll("[^\\d]", ""));
            });

        }
    }
