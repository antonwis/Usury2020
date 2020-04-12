package fi.metropolia.group8.model;

import java.time.LocalDate;

/**
 * Handles updating loan-related data values, e.g. updating alias' equity after completing a loan.
 */
public class LoanCalculator {

    private static LoanCalculator instance;

    public LoanCalculator() {
    }

    /**
     * Retrieves the global instance
     * @return returns the singleton instance
     */
    public static LoanCalculator getInstance() {
        if(instance == null) {
            instance = new LoanCalculator();
        }
        return instance;
    }

    /**
     * calculates total sum after interest
     * @param loan
     * @return
     */
    public float getLoanTotalSum(Loan loan) {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        return loan.getValue() + interest;
    }

    /**
     * calculates total sum after interest for the generated loan proposal
     * @param generatedVictim
     * @return
     */
    public float getLoanOfferTotalSum(GeneratedVictim generatedVictim) {
        float interest = (generatedVictim.getInterest() / 100) * generatedVictim.getValue();
        return generatedVictim.getValue() + interest;
    }

    /**
     * calculates profit made from the loan
     * @param loan
     * @return
     */
    public float getInterestProfit(Loan loan) {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        return interest;
    }

    /**
     * calculates profit made from the loan proposal
     * @param generatedVictim
     * @return
     */
    public float getOfferInterestProfit(GeneratedVictim generatedVictim) {
        float interest = (generatedVictim.getInterest() / 100) * generatedVictim.getValue();
        return interest;
    }

    /**
     * sets new equity for alias after giving out a loan
     * @param loan
     */
    // Update alias data in database
    public void updateEquity(Loan loan) {
        Alias alias = DataModel.getInstance().getCurrentAlias();
        float newEquity = alias.getEquity() - loan.getValue();
        alias.setEquity(newEquity);
        DataModel.getInstance().saveAliasData(alias);
    }

    /**
     * Update alias with loan values and update loan status in database. Notifies event log manager.
     * @param loan
     */
    public void completeLoan(Loan loan) {
        Alias alias = loan.getOwner();
        float totalSum = getLoanTotalSum(loan);
        float totalProfit = getInterestProfit(loan);
        float newProfitTotal = alias.getTotalProfits() + totalProfit;
        float newEquity = alias.getEquity() + totalSum;
        int newCompletedLoans = alias.getCompletedLoans() + 1;
        alias.setEquity(newEquity);
        alias.setTotalProfits(newProfitTotal);
        alias.setCompletedLoans(newCompletedLoans);
        loan.setCompleted(true);

        // Completion date is the user-specific current working date -- NOT the current actual system date
        loan.setCompleteDate(DataModel.getInstance().getCurrentUser().getCurrentDate());

        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().saveLoanData(loan);
        DataModel.getInstance().setCurrentAlias(alias); // PEPEGA TIER FIX
        DataModel.getInstance().loadAliasData();
        DataModel.getInstance().loadLoanData();
        // Call event logger
        EventManager.getInstance().loanCompleted(loan);
    }

    /**
     * modifies loan in database
     * @param loan
     * @param newInterest
     * @param dueDate
     */
    public void modifyLoan(Loan loan, float newInterest, LocalDate dueDate) {
        loan.setDueDate(dueDate);
        loan.setInterest(newInterest);
        DataModel.getInstance().saveLoanData(loan);
    }

    /**
     * Sets the loan value to zero. Forfeits the loan and all its profits. Notifies event log manager.
     * Used when victim is no longer alive.
     * @param alias
     * @param loan
     */
    public void forfeitLoan(Alias alias, Loan loan) {
        loan.setValue(0f);
        int newCompletedLoans = alias.getCompletedLoans() + 1;
        alias.setCompletedLoans(newCompletedLoans);
        loan.setCompleted(true);
        loan.setCompleteDate(DataModel.getInstance().getCurrentUser().getCurrentDate());
        EventManager.getInstance().loanForfeited(loan);
        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().saveLoanData(loan);
    }

    /**
     * Forfeits all profits. Returns the loan value to the alias. Notifies event log manager.
     * Used while repossessing loans.
     * @param alias
     * @param loan
     */
    public void repoLoan(Alias alias, Loan loan) {
        float newEquity = alias.getEquity() + loan.getValue();
        int newCompletedLoans = alias.getCompletedLoans() + 1;
        alias.setEquity(newEquity);
        alias.setCompletedLoans(newCompletedLoans);
        loan.setCompleted(true);
        loan.setCompleteDate(DataModel.getInstance().getCurrentUser().getCurrentDate());
        EventManager.getInstance().loanRepossessed(loan);
        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().saveLoanData(loan);
    }

    /**
     * Pays back half of the loan value with interest to the Alias
     * @param loan
     */
    public void payHalfLoan(Loan loan) {
        float newValue = (loan.getValue() / 2);
        loan.setValue(newValue);
        Alias alias = loan.getOwner();
        float newSum = alias.getEquity() + LoanCalculator.getInstance().getLoanTotalSum(loan);
        alias.setEquity(newSum);
        DataModel.getInstance().saveLoanData(loan);
        DataModel.getInstance().saveAliasData(alias);
    }

    /**
     * Modifies the loan's value by a given modifier
     * @param loan
     * @param modifier percentage modifier ie. 20
     */
    public void modifyLoanValue(Loan loan, float modifier) {
        float newValue = (loan.getValue() * (1 +(modifier / 100)));
        loan.setValue(newValue);
        DataModel.getInstance().saveLoanData(loan);
    }

}
