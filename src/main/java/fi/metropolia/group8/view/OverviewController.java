package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;

import java.time.LocalDate;

/**
 * controller for overview view
 */
public class OverviewController {

    @FXML
    private Label user;

    @FXML
    private Label alias;

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
    private BarChart profitChart;

    /**
     *Updates overview based on currently active alias
     */
    public void updateOverview(){

        // Filter aliases for current user
        //FilteredList<Alias> filteredList = new FilteredList<>(DataModel.getInstance().getAliasList());
        //Predicate<Alias> aliasFilter = fil -> fil.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        //filteredList.setPredicate(aliasFilter);

        /// Current User
        user.setText("Summary for " + DataModel.getInstance().getCurrentUser().getName());
        if(DataModel.getInstance().getCurrentAlias() == null){
            alias.setText("No alias selected");
            loansActive.setText("");
            loansComplete.setText("");
            loansDue.setText("");
            loans.setText("");
            profits.setText("");
            enforcerActions.setText("");
            balance.setText("");
        }
        // Current alias
        else {
            alias.setText("Selected alias: " + DataModel.getInstance().getCurrentAlias().getName());
            // loans active
            loansActive.setText(String.valueOf(DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).size()));
            // Loans Completed
            loansComplete.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getCompletedLoans()));
            // Total Loans
            loansDue.setText((String.valueOf(DataModel.getInstance().getLoanList().filtered(
                    loan -> loan.getOwner().getName().equals(
                            DataModel.getInstance().getCurrentAlias().getName())).filtered(
                    loan -> loan.getDueDate().isBefore(LocalDate.now())).size()
            )));
            // Active loans past due date
            loans.setText(String.valueOf(Integer.sum(DataModel.getInstance().getCurrentAlias().getCompletedLoans(),DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).size())));
            // Profits
            profits.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getTotalProfits()));
            // Enforcer actions
            enforcerActions.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEnforcerActions()));
            // balance
            balance.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEquity()));
        }

    }

    /**
     * Initializes overview during startup
     */
    public void initModel() {
        if (DataModel.getInstance().getCurrentAlias() != null) updateOverview();
        user.setText("Welcome " + DataModel.getInstance().getCurrentUser().getName() +"!");
        //WIP
/*

        forecast.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));*/
    }
}
