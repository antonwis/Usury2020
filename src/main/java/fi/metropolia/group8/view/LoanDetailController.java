package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LoanDetailController {

    @FXML
    private ImageView ProfileImage;
    @FXML
    private Label LoanDetailHeader;
    @FXML
    private Label IssueDate;
    @FXML
    private Label TotalDebt;
    @FXML
    private Spinner Interest;
    @FXML
    private Label DueDate;
    @FXML
    private Label DebtRemaining;
    @FXML
    private Label ProjectedEarnings;
    @FXML
    private Label VictimName;
    @FXML
    private Label VictimAddress;
    @FXML
    private Label VictimDescription;

    private LoanDataModel loanDataModel;
    private AliasDataModel aliasDataModel;
    private LoanCalculator loanCalculator;
    private LoanListController loanListController;
    private PrimaryController primaryController;

    @FXML
    private Button enforceP;
    @FXML
    private Button modifyL;
    @FXML
    private Button completeL;

    @FXML
    void completeLoan() {
        loanCalculator.completeLoan(aliasDataModel.getCurrentAlias(),loanDataModel.getCurrentLoan());
        aliasDataModel.loadData();
        primaryController.setCurrentAliasText();
        loanListController.refreshLoans();
    }

    @FXML
    void enforcePayment() {

    }

    @FXML
    void modifyLoan() throws IOException {
        Interest.commitValue();
        loanCalculator.modifyLoan(loanDataModel.getCurrentLoan(), (float) (double) Interest.getValue());
        loanListController.refreshDetails();
    }

    public void display(LoanDataModel loanDataModel, AliasDataModel aliasDataModel, LoanListController loanListController, PrimaryController primaryController) {

        this.loanDataModel = loanDataModel;
        this.aliasDataModel = aliasDataModel;
        this.loanListController = loanListController;
        this.primaryController = primaryController;
        loanCalculator = new LoanCalculator(loanDataModel, aliasDataModel);

        // Detail header
        LoanDetailHeader.setText(String.format("Details for loan %s", loanDataModel.getCurrentLoan().getId()));

        // Dates
        IssueDate.setText(loanDataModel.getCurrentLoan().getStartDate().toString());
        DueDate.setText(loanDataModel.getCurrentLoan().getDueDate().toString());

        // Original loaned sum
        TotalDebt.setText(String.valueOf(loanDataModel.getCurrentLoan().getValue()));

        // Total Debt remaining
        DebtRemaining.setText(String.valueOf(loanCalculator.getLoanTotalSum(loanDataModel.getCurrentLoan())));

        // Interest percentage
        //Interest.setText(String.valueOf(loanDataModel.getCurrentLoan().getInterest()));
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0,1000.0,loanDataModel.getCurrentLoan().getInterest());
        Interest.setValueFactory(spinnerValueFactory);

        // Interest profit
        ProjectedEarnings.setText(String.valueOf(loanCalculator.getInterestProfit(loanDataModel.getCurrentLoan())));

        // Victim details
        VictimName.setText(loanDataModel.getCurrentLoan().getVictim().getName());
        VictimAddress.setText(loanDataModel.getCurrentLoan().getVictim().getAddress());
        VictimDescription.setText(loanDataModel.getCurrentLoan().getVictim().getDescription());
    }
}
