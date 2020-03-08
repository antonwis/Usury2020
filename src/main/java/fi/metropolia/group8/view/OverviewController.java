package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Map;

public class OverviewController {

    @FXML
    private Label user;

    @FXML
    private ComboBox<Alias> filter;

    @FXML
    private Label balance;

    @FXML
    private Label profits;

    @FXML
    private Label loans;

    @FXML
    private Label loansComplete;

    @FXML
    private Label loansActive;

    @FXML
    private Label enforcerActions;

    @FXML
    private Label loansDue;

    @FXML
    private Label forecast;

    @FXML
    private BarChart<?, ?> profitChart;

    public void updateOverview(){
        DataModel.getInstance().loadAliasData();
        filter.setItems(DataModel.getInstance().getAliasList());
        /// Current alias
        user.setText(DataModel.getInstance().getCurrentUser().getName());
        // balance
        balance.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEquity()));
        // loans active
        loansActive.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        // Loans Completed
        loansComplete.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getCompletedLoans()));
        // Total Loans
        loans.setText(String.valueOf(Integer.sum(DataModel.getInstance().getCurrentAlias().getCompletedLoans(),DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).size())));
        // loans due
        loansDue.setText(String.valueOf(DataModel.getInstance().getLoanList().filtered(loan -> loan.getDueDate().isBefore(LocalDate.now())).size()));
        // Profits

    }

    public void initModel() {
        if (DataModel.getInstance().getCurrentAlias() != null) updateOverview();
        //WIP
/*      profits.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        enforcerActions.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        forecast.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));*/
    }
}
