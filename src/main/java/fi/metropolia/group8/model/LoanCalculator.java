package fi.metropolia.group8.model;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Handles updating loan-related data values, e.g. updating alias' equity after completing a loan.
 */
public class LoanCalculator {

    public LoanCalculator() {
    }

    public float getLoanTotalSum(Loan loan) {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        return loan.getValue() + interest;
    }

    public float getInterestProfit(Loan loan) {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        return interest;
    }

    // Update alias data in database
    public void updateEquity(Alias alias, Loan loan) {
        float newEquity = alias.getEquity() - loan.getValue();
        alias.setEquity(newEquity);
        DataModel.getInstance().saveAliasData(alias);
    }

    // Update alias with loan values and delete completed loan from database
    public void completeLoan(Alias alias, Loan loan) {
        float totalSum = getLoanTotalSum(loan);
        float totalProfit = getInterestProfit(loan);
        float newProfitTotal = alias.getTotalProfits() + totalProfit;
        float newEquity = alias.getEquity() + totalSum;
        int newCompletedLoans = alias.getCompletedLoans() + 1;
        alias.setEquity(newEquity);
        alias.setTotalProfits(newProfitTotal);
        alias.setCompletedLoans(newCompletedLoans);
        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().deleteLoan(loan);
    }

    public void modifyLoan(Loan loan, float newInterest) {
        loan.setInterest(newInterest);
        DataModel.getInstance().saveLoanData(loan);
    }

    public void updateEnforcedActions(Alias alias) {
        int newEnforcerTotal = alias.getEnforcerActions() + 1;
        alias.setEnforcerActions(newEnforcerTotal);
        DataModel.getInstance().saveAliasData(alias);
    }

}
