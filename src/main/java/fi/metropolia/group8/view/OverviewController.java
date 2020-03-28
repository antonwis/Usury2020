package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanCalculator;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
    private ComboBox overviewCombo;

    @FXML
    private CategoryAxis chartX;

    @FXML
    private NumberAxis chartY;

    @FXML
    void changedCombo(ActionEvent event) {
        System.out.println("selected:");
        if (overviewCombo.getSelectionModel().getSelectedItem().equals("All")) {
            showAllAliases();
        } else {
            overviewCombo.getSelectionModel().getSelectedItem();
            FilteredList<Alias> alias = DataModel.getInstance().getAliasList().filtered(a -> a.getName().equals(overviewCombo.getSelectionModel().getSelectedItem().toString()) && DataModel.getInstance().getCurrentUser().getName().equals(a.getUser().getName()));
            currentAlias(alias.get(0));
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
            // Total Loans
            loansDue.setText((String.valueOf(DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).filtered(loan -> loan.getDueDate().isBefore(LocalDate.now())).size())));
            // Active loans past due date
            loans.setText(String.valueOf(Integer.sum(DataModel.getInstance().getCurrentAlias().getCompletedLoans(), DataModel.getInstance().getLoanList().filtered(loan -> loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).size())));
            // Profits
            profits.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getTotalProfits()));
            // Enforcer actions
            enforcerActions.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEnforcerActions()));
            // balance
            balance.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEquity()));
            initCombo();
            overviewCombo.getSelectionModel().clearSelection();
        }

    }

    /**
     * Initializes overview during startup
     */
    public void initModel() {
        if (DataModel.getInstance().getCurrentAlias() != null) updateOverview();
        user.setText("Welcome " + DataModel.getInstance().getCurrentUser().getName() + "!");
        initCombo();
        profitChart.setAnimated(false);
        //WIP
        //forecast.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
    }

    public void initCombo() {
        overviewCombo.getItems().clear();
        overviewCombo.getItems().add("All");
        for (Alias a : DataModel.getInstance().getAliasList().filtered(a -> a.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName()))) {
            overviewCombo.getItems().add(a.getName());
        }
    }

    public void showAllAliases() {
        profitChart.getData().clear();
        FilteredList<Alias> meme = DataModel.getInstance().getAliasList().filtered(a -> a.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName()));
        for (Alias a : meme) {
            FilteredList<Loan> aliasLoans = DataModel.getInstance().getLoanList().filtered(b -> b.isCompleted() == true && b.getOwner().getName().equals(a.getName()));
            XYChart.Series set = new XYChart.Series();
            set.setName(a.getName());
            for (Month m : Month.values())
                set.getData().add(new XYChart.Data<>(m.toString(), calculateProfit(aliasLoans, m)));
            profitChart.getData().addAll(set);
        }
    }

    public void currentAlias(Alias alias) {
        profitChart.getData().clear();
        FilteredList<Loan> aliasLoans = DataModel.getInstance().getLoanList().filtered(a -> a.getOwner().getName().equals(alias.getName()) && a.isCompleted() == true);
        XYChart.Series set = new XYChart.Series();
        set.setName(alias.getName());
        for (Month m : Month.values())
            set.getData().add(new XYChart.Data<>(m.toString(), calculateProfit(aliasLoans, m)));
        profitChart.getData().addAll(set);
    }

    public Float calculateProfit(FilteredList aliasLoans, Month m) {
        float sum = 0;
        LoanCalculator loan = new LoanCalculator();
        for (Object l : aliasLoans) {
            if (((Loan) l).getCompleteDate().getMonth() == m) {
                sum += loan.getInterestProfit((Loan) l);
            }
        }
        return sum;
    }
}
