package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanCalculator;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Month;

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
    private LineChart profitChart;

    @FXML
    private ComboBox<String> overviewCombo;

    @FXML
    private CategoryAxis chartX;

    @FXML
    private NumberAxis chartY;

    /**
     * Changes the view to reflect the selected item in combobox
     */
    @FXML
    void changedCombo() {
        if (!overviewCombo.getSelectionModel().isEmpty()){
            if (overviewCombo.getValue().equals("All")) {
                showAllAliases();
            } else {
                FilteredList<Alias> alias = DataModel.getInstance().getAliasList().filtered(a -> a.getName().equals(overviewCombo.getValue()) && DataModel.getInstance().getCurrentUser().getName().equals(a.getUser().getName()));
                currentAlias(alias.get(0));
            }
        }
    }


    /**
     * Updates overview based on currently active alias
     */
    public void updateOverview() {

        // Filter aliases for current user
        //FilteredList<Alias> filteredList = new FilteredList<>(DataModel.getInstance().getAliasList());
        //Predicate<Alias> aliasFilter = fil -> fil.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        //filteredList.setPredicate(aliasFilter);

        // Update ComboBox
        updateCombo();

        /// Current User
        user.setText("Summary for " + DataModel.getInstance().getCurrentUser().getName());

        // Current alias
        if (DataModel.getInstance().getCurrentAlias() != null) {
            overviewCombo.setVisible(true);
            alias.setText("Selected: ");
            // loans active
            loansActive.setText(String.valueOf(DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName()) && loan.isCompleted() == false).size()));
            // Loans Completed
            loansComplete.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getCompletedLoans()));
            // Active loans past due date
            loansDue.setText(String.valueOf(DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).filtered(loan -> loan.getDueDate().isBefore(LocalDate.now()) && loan.isCompleted() == false).size()));
            // Total Loans
            loans.setText(String.valueOf(Integer.sum(DataModel.getInstance().getCurrentAlias().getCompletedLoans(), DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName()) && loan.isCompleted() == false).size())));
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
        user.setText("Welcome " + DataModel.getInstance().getCurrentUser().getName() + "!");
        updateCombo();
        profitChart.setAnimated(false);
        //WIP
        //forecast.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
    }

    /**
     * Updates the combobox when new alias or loan is added.
     */
    public void updateCombo() {
        overviewCombo.getItems().clear(); // aiheuttaa nullpointereita, pitää fix jossain vaiheessa
        overviewCombo.getItems().add("All");
        for (Alias a : DataModel.getInstance().getAliasList().filtered(a -> a.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName()))) {
            overviewCombo.getItems().add(a.getName());
        }
    }

    /**
     *  Shows the profits of all aliases in the linechart
     */
    public void showAllAliases() {
        profitChart.getData().clear();
        FilteredList<Alias> meme = DataModel.getInstance().getAliasList().filtered(a -> a.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName()));
        for (Alias a : meme) {
            FilteredList<Loan> aliasLoans = DataModel.getInstance().getLoanList().filtered(b -> b.isCompleted() && b.getOwner().getName().equals(a.getName()));
            XYChart.Series set = new XYChart.Series();
            set.setName(a.getName());
            for (Month m : Month.values())
                set.getData().add(new XYChart.Data<>(m.toString(), calculateProfit(aliasLoans, m)));
            profitChart.getData().addAll(set);
        }
    }

    /**
     * Shows the profits of current alias in the linechart
     * @param alias
     */
    public void currentAlias(Alias alias) {
        profitChart.getData().clear();
        FilteredList<Loan> aliasLoans = DataModel.getInstance().getLoanList().filtered(a -> a.getOwner().getName().equals(alias.getName()) && a.isCompleted());
        XYChart.Series set = new XYChart.Series();
        set.setName(alias.getName());
        for (Month m : Month.values())
            set.getData().add(new XYChart.Data<>(m.toString(), calculateProfit(aliasLoans, m)));
        profitChart.getData().addAll(set);
    }

    /**
     * Calculates the total profit of the month
     * @param aliasLoans
     * @param m
     * @return
     */
    public Float calculateProfit(FilteredList<Loan> aliasLoans, Month m) {
        float sum = 0;
        LoanCalculator loan = new LoanCalculator();
        for (Loan l : aliasLoans) {
            if ((l).getCompleteDate().getMonth() == m) {
                sum += loan.getInterestProfit(l);
            }
        }
        return sum;
    }
}
