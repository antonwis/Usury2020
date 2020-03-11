package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.LoanCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller class for showing and modifying loan details.
 */
public class LoanDetailController {

    @FXML
    private VBox loanDetailVbox;

    @FXML
    private Label LoanDetailHeader;

    @FXML
    private Label TotalDebt;

    @FXML
    private Label IssueDate;

    @FXML
    private Label DebtRemaining;

    @FXML
    private Label ProjectedEarnings;

    @FXML
    private Label Interest;

    @FXML
    private Label DueDate;

    @FXML
    private ImageView ProfileImage;

    @FXML
    private Label VictimName;

    @FXML
    private Label VictimAddress;

    @FXML
    private Label VictimDescription;

    @FXML
    private HBox modifyHbox1;
    @FXML
    private Spinner interestSpinner;
    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private Button enforceP;

    @FXML
    private Button modifyL;

    @FXML
    private Button completeL;

    @FXML
    private HBox modifyHbox2;

    @FXML
    private Button applyModify;

    @FXML
    private Button cancelModify;

    private LoanCalculator loanCalculator;
    private LoanListController loanListController;
    private PrimaryController primaryController;
    private OverviewController overviewController;

    /**
     * Method for rip
     *
     */
    @FXML
    void applyModify() throws IOException {
        if (interestSpinner.getValue() != null && dueDatePicker.getValue() != null) {
            interestSpinner.commitValue();
            loanCalculator.modifyLoan(DataModel.getInstance().getCurrentLoan(), (float) (double) interestSpinner.getValue(), dueDatePicker.getValue());
            loanListController.refreshDetails();
        }
        modifyHbox1.setVisible(!false);
        modifyHbox2.setVisible(!true);
        DueDate.setVisible(!false);
        dueDatePicker.setVisible(!true);
        interestSpinner.setVisible(!true);
        Interest.setVisible(!false);
        loanListController.refreshLoans();
        overviewController.updateOverview();
        loanListController.updateView();
    }

    @FXML
    void cancelModify() {
        modifyHbox1.setVisible(!false);
        modifyHbox2.setVisible(!true);
        DueDate.setVisible(!false);
        dueDatePicker.setVisible(!true);
        interestSpinner.setVisible(!true);
        Interest.setVisible(!false);
    }

    @FXML
    void completeLoan() {
        loanCalculator.completeLoan(DataModel.getInstance().getCurrentAlias(), DataModel.getInstance().getCurrentLoan());
        DataModel.getInstance().loadAliasData();
        primaryController.setCurrentAliasText();
        loanListController.refreshLoans();
        overviewController.updateOverview();
    }

    @FXML
    void enforcePayment() {
        loanCalculator.updateEnforcedActions(DataModel.getInstance().getCurrentAlias());
        overviewController.updateOverview();
    }

    @FXML
    void modifyLoan() {
        modifyHbox1.setVisible(false);
        modifyHbox2.setVisible(true);
        DueDate.setVisible(false);
        dueDatePicker.setVisible(true);
        interestSpinner.setVisible(true);
        Interest.setVisible(false);
        dueDatePicker.setValue(DataModel.getInstance().getCurrentLoan().getDueDate());
        dueDatePicker.setEditable(false);
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 1000.0, DataModel.getInstance().getCurrentLoan().getInterest());
        interestSpinner.setValueFactory(spinnerValueFactory);
        interestSpinner.setEditable(false);
        dueDatePicker.setValue(DataModel.getInstance().getCurrentLoan().getDueDate());
    }

    public void display(LoanListController loanListController, PrimaryController primaryController, OverviewController overviewController) {

        this.loanListController = loanListController;
        this.primaryController = primaryController;
        this.overviewController = overviewController;

        loanCalculator = new LoanCalculator();

        // Detail header
        LoanDetailHeader.setText(String.format("Details for loan %s", DataModel.getInstance().getCurrentLoan().getId()));

        // Dates
        IssueDate.setText(DataModel.getInstance().getCurrentLoan().getStartDate().toString());
        DueDate.setText(DataModel.getInstance().getCurrentLoan().getDueDate().toString());

        // Original loaned sum
        TotalDebt.setText(String.valueOf(DataModel.getInstance().getCurrentLoan().getValue()));

        // Total Debt remaining
        DebtRemaining.setText(String.valueOf(loanCalculator.getLoanTotalSum(DataModel.getInstance().getCurrentLoan())));

        // Interest percentage
        Interest.setText(String.valueOf(DataModel.getInstance().getCurrentLoan().getInterest()));

        // Interest profit
        ProjectedEarnings.setText(String.valueOf(loanCalculator.getInterestProfit(DataModel.getInstance().getCurrentLoan())));

        // Victim details
        VictimName.setText(DataModel.getInstance().getCurrentLoan().getVictim().getName());
        VictimAddress.setText(DataModel.getInstance().getCurrentLoan().getVictim().getAddress());
        VictimDescription.setText(DataModel.getInstance().getCurrentLoan().getVictim().getDescription());
    }
}
