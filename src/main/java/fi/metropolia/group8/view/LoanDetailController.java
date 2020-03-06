package fi.metropolia.group8.view;

import fi.metropolia.group8.model.AliasDataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanCalculator;
import fi.metropolia.group8.model.LoanDataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    private Label Interest;
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

    private LoanCalculator loanCalculator;

    public void display(LoanDataModel loanDataModel, AliasDataModel aliasDataModel) {

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
        Interest.setText(String.valueOf(loanDataModel.getCurrentLoan().getInterest()));

        // Interest profit
        ProjectedEarnings.setText(String.valueOf(loanCalculator.getInterestProfit(loanDataModel.getCurrentLoan())));

        // Victim details
        VictimName.setText(loanDataModel.getCurrentLoan().getVictim().getName());
        VictimAddress.setText(loanDataModel.getCurrentLoan().getVictim().getAddress());
        VictimDescription.setText(loanDataModel.getCurrentLoan().getVictim().getDescription());
    }
}
