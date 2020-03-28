package fi.metropolia.group8.model;

import java.time.LocalDate;

/**
 * Handles updating loan-related data values, e.g. updating alias' equity after completing a loan.
 */
public class LoanCalculator {

    public LoanCalculator() {
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
     * calculates profit made from the loan
     * @param loan
     * @return
     */
    public float getInterestProfit(Loan loan) {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        return interest;
    }

    /**
     * sets new equity for alias after giving out a loan
     * @param alias
     * @param loan
     */
    // Update alias data in database
    public void updateEquity(Alias alias, Loan loan) {
        float newEquity = alias.getEquity() - loan.getValue();
        alias.setEquity(newEquity);
        DataModel.getInstance().saveAliasData(alias);
    }

    /**
     * Update alias with loan values and update loan status in database
     * @param alias
     * @param loan
     */

    public void completeLoan(Alias alias, Loan loan) {
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
        //DataModel.getInstance().deleteLoan(loan);
        DataModel.getInstance().saveLoanData(loan);
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
     * Sets the loan value to zero. Forfeits the loan and all its profits.
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
        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().saveLoanData(loan);
    }

    /**
     * Forfeits all profits. Returns the loan value to the alias.
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
        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().saveLoanData(loan);
    }

    /**
     * updates number of enforce actions
     * @param alias
     */
    public void updateEnforcedActions(Alias alias) {
        int newEnforcerTotal = alias.getEnforcerActions() + 1;
        alias.setEnforcerActions(newEnforcerTotal);
        DataModel.getInstance().saveAliasData(alias);
    }

}
