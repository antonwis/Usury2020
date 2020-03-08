package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Predicate;

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

        // Filter aliases for current user
        //FilteredList<Alias> filteredList = new FilteredList<>(DataModel.getInstance().getAliasList());
        //Predicate<Alias> aliasFilter = fil -> fil.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        //filteredList.setPredicate(aliasFilter);

        /// Current User
        user.setText("Summary for " + DataModel.getInstance().getCurrentAlias().getName());

        // Current user alias list
        //filter.setItems(filteredList);

        // balance
        balance.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEquity()));

        // loans active
        loansActive.setText(String.valueOf(DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).size()));

        // Loans Completed
        loansComplete.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getCompletedLoans()));
        // Total Loans
        loans.setText(String.valueOf(Integer.sum(DataModel.getInstance().getCurrentAlias().getCompletedLoans(),DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).size())));

        // Active loans past due date
        loansDue.setText((String.valueOf(DataModel.getInstance().getLoanList().filtered(
                loan -> loan.getOwner().getName().equals(
                        DataModel.getInstance().getCurrentAlias().getName())).filtered(
                                loan -> loan.getDueDate().isBefore(LocalDate.now())).size()
        )));
        // Profits
        profits.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getTotalProfits()));

        // Enforcer actions
        enforcerActions.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEnforcerActions()));

    }

    public void initModel() {
        if (DataModel.getInstance().getCurrentAlias() != null) updateOverview();
        user.setText("Welcome " + DataModel.getInstance().getCurrentUser().getName() +"!");
        //WIP
/*

        forecast.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));*/
    }
}
