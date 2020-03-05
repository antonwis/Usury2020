package fi.metropolia.group8.view;

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



    public void display(LoanDataModel m) {
        LoanDetailHeader.setText(String.format("Details for loan %s", m.getCurrentLoan().getId()));
        IssueDate.setText(m.getCurrentLoan().getStartDate().toString());
        DebtRemaining.setText(String.valueOf(m.getCurrentLoan().getValue()));
        ProjectedEarnings.setText(String.valueOf(m.getCurrentLoan().getValue() + ((m.getCurrentLoan().getInterest()/100)*m.getCurrentLoan().getValue())));
        TotalDebt.setText(String.valueOf(m.getCurrentLoan().getValue()));
        Interest.setText(String.valueOf(m.getCurrentLoan().getInterest()));
        DueDate.setText(m.getCurrentLoan().getDueDate().toString());
        VictimName.setText(m.getCurrentLoan().getVictim().getName());
        VictimAddress.setText(m.getCurrentLoan().getVictim().getAddress());
        VictimDescription.setText(m.getCurrentLoan().getVictim().getDescription());
    }
}
