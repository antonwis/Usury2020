package fi.metropolia.group8.view.Main.Loans;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanCalculator;
import fi.metropolia.group8.model.VictimGenerator;
import fi.metropolia.group8.view.Main.Loans.LoanListController;
import fi.metropolia.group8.view.Overview.OverviewController;
import fi.metropolia.group8.view.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;

public class VictimDetailController {

    @FXML
    private VBox victimDetailVbox;

    @FXML
    private Label VictimDetailHeader;

    @FXML
    private Label loanValue;

    @FXML
    private Label Interest;

    @FXML
    private Label DueDate;

    @FXML
    private Label ProjectedEarnings;

    @FXML
    private ImageView ProfileImage;

    @FXML
    private Label VictimName;

    @FXML
    private Label VictimAddress;

    @FXML
    private HBox modifyHbox1;

    @FXML
    private Button acceptLoan;

    @FXML
    private Button declineLoan;

    private LoanListController loanListController;
    private PrimaryController primaryController;
    private OverviewController overviewController;

    @FXML
    void acceptLoan() throws IOException {
        Loan loan = DataModel.getInstance().createLoan(
                DataModel.getInstance().getCurrentAlias(),
                VictimGenerator.getInstance().getCurrentVictim().getValue(),
                DataModel.getInstance().createGeneratedVictim(VictimGenerator.getInstance().getCurrentVictim()),
                DataModel.getInstance().getCurrentUser().getCurrentDate(),
                VictimGenerator.getInstance().getCurrentVictim().getDueDate(),
                VictimGenerator.getInstance().getCurrentVictim().getInterest()
        );
        LoanCalculator.getInstance().updateEquity(loan);
        VictimGenerator.getInstance().deleteVictim(VictimGenerator.getInstance().getCurrentVictim());
        loanListController.refreshVictimDetails();
        loanListController.refreshVictimList();
        loanListController.refreshLoans();
        primaryController.setCurrentAliasText();
        overviewController.updateOverview();

    }

    @FXML
    void declineLoan() {
        VictimGenerator.getInstance().deleteVictim(VictimGenerator.getInstance().getCurrentVictim());
    }

    public void display(LoanListController loanListController, PrimaryController primaryController, OverviewController overviewController) {
        this.primaryController = primaryController;
        this.overviewController = overviewController;
        this.loanListController = loanListController;

        // Detail header
        VictimDetailHeader.setText(String.format("%s's Loan Application", VictimGenerator.getInstance().getCurrentVictim().getName()));

        // Dates
        DueDate.setText(VictimGenerator.getInstance().getCurrentVictim().getDueDate().toString());

        //Loan value
        loanValue.setText(String.valueOf(VictimGenerator.getInstance().getCurrentVictim().getValue()));

        // Interest percentage
        Interest.setText(String.valueOf(VictimGenerator.getInstance().getCurrentVictim().getInterest()));

        // Interest profit
        ProjectedEarnings.setText(String.valueOf(LoanCalculator.getInstance().getOfferInterestProfit(VictimGenerator.getInstance().getCurrentVictim())));

        // Victim details
        VictimName.setText(VictimGenerator.getInstance().getCurrentVictim().getName());
        VictimAddress.setText(VictimGenerator.getInstance().getCurrentVictim().getAddress());
    }
}