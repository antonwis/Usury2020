package fi.metropolia.group8.view.Overview;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanCalculator;
import fi.metropolia.group8.view.Menu.Settings.LanguageController;
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
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
    private LineChart<String, Float> profitChart;

    @FXML
    private ComboBox<String> overviewCombo;

    @FXML
    private CategoryAxis chartX;

    @FXML
    private NumberAxis chartY;

    private LanguageController languageController;

    /**
     * Changes the view to reflect the selected item in combobox
     */
    @FXML
    void changedCombo() {
        if (!overviewCombo.getSelectionModel().isEmpty()) {
            if (overviewCombo.getValue().equals(languageController.getTranslation("allC"))) {
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
        user.setText(languageController.getTranslation("summary") + " " + DataModel.getInstance().getCurrentUser().getName());

        // Current alias
        if (DataModel.getInstance().getCurrentAlias() != null) {
            overviewCombo.setVisible(true);
            alias.setText(languageController.getTranslation("selected"));
            loansActive();
            loansCompleted();
            loansDue();
            totalLoans();
            setProfits();
            setEnforcerActions();
            setBalance();
        }
    }

    /**
     * Initializes overview during startup
     */
    public void initModel() {
        if (DataModel.getInstance().getCurrentAlias() != null) updateOverview();
        languageController = new LanguageController();
        user.setText(languageController.getTranslation("welcome") + " " + DataModel.getInstance().getCurrentUser().getName() + "!");
        updateCombo();

        //chartX.setAutoRanging(false);

        chartY.setAutoRanging(false);
        chartY.setLowerBound(0);
        chartY.setUpperBound(10000);
        chartY.setTickUnit(1000);
    }

    /**
     * Sets active loans based on the filters
     */
    public void loansActive() {
        int active = DataModel.getInstance().getLoanList().filtered(loan ->
                loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName()) && !loan.isCompleted()).size();
        loansActive.setText(String.valueOf(active));
    }

    /**
     * Sets completed loans based on the filters
     */
    public void loansCompleted() {
        loansComplete.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getCompletedLoans()));
    }

    /**
     * Sets loans due based on the filters
     */
    public void loansDue() {
        int loans = DataModel.getInstance().getLoanList().filtered(loan ->
                loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName())).filtered(loan ->
                loan.getDueDate().isBefore(LocalDate.now()) && !loan.isCompleted()).size();
        loansDue.setText(String.valueOf(loans));
    }

    /**
     * Sets total loans based on the filters
     */
    public void totalLoans() {
        int y = DataModel.getInstance().getCurrentAlias().getCompletedLoans();
        int x = DataModel.getInstance().getLoanList().filtered(loan ->
                loan.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName()) && !loan.isCompleted()).size();
        loans.setText(String.valueOf(Integer.sum(y, x)));
    }

    /**
     * Sets profits based on the filters
     */
    public void setProfits() {
        profits.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getTotalProfits()));
    }

    /**
     * Sets enforcer actions based on the filters
     */
    public void setEnforcerActions() {
        enforcerActions.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEnforcerActions()));
    }

    /**
     * Sets balance based on the filters
     */
    public void setBalance() {
        balance.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEquity()));
    }

    /**
     * Updates the combobox when new alias or loan is added.
     */
    public void updateCombo() {
        overviewCombo.getItems().clear(); // aiheuttaa nullpointereita, pitää fix jossain vaiheessa
        overviewCombo.getItems().add(languageController.getTranslation("allC"));
        for (Alias a : DataModel.getInstance().getAliasList().filtered(a -> a.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName()))) {
            overviewCombo.getItems().add(a.getName());
        }
    }

    /**
     * Shows the profits of all aliases in the linechart
     */
    public void showAllAliases() {
        LanguageController lcont = new LanguageController();
        profitChart.getData().clear();
        FilteredList<Alias> meme = DataModel.getInstance().getAliasList().filtered(a -> a.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName()));
        for (Alias a : meme) {
            FilteredList<Loan> aliasLoans = DataModel.getInstance().getLoanList().filtered(b -> b.isCompleted() && b.getOwner().getName().equals(a.getName()));
            XYChart.Series<String, Float> set = new XYChart.Series<>();
            set.setName(a.getName());

            for (Month m : Month.values()) {
                set.getData().add(new XYChart.Data<>((languageController.getTranslation(m.toString().toLowerCase())), calculateProfit(aliasLoans, m)));
            }

            profitChart.getData().addAll(set);
        }
    }

    /**
     * Shows the profits of current alias in the linechart
     *
     * @param alias alias
     */
    public void currentAlias(Alias alias) {
        profitChart.getData().clear();
        FilteredList<Loan> aliasLoans = DataModel.getInstance().getLoanList().filtered(a -> a.getOwner().getName().equals(alias.getName()) && a.isCompleted());
        XYChart.Series<String, Float> set = new XYChart.Series<>();
        set.setName(alias.getName());
        for (Month m : Month.values())
            set.getData().add(new XYChart.Data<>(languageController.getTranslation(m.toString().toLowerCase()), calculateProfit(aliasLoans, m)));
        profitChart.getData().addAll(set);
    }

    /**
     * Calculates the total profit of the month
     *
     * @param aliasLoans list of aliases
     * @param m          Month value
     * @return returns the profit sum
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
