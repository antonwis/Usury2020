package fi.metropolia.group8.model;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanCalculator {
    private float amount;
    private float interest;
    private Loan loan;
    private long daysBetween;
    private float finalSum;

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

    // Add loan total value to alias equity and delete loan from database
    public void completeLoan(Alias alias, Loan loan) {
        float totalProfit = getLoanTotalSum(loan);
        float newEquity = alias.getEquity() + totalProfit;
        int newCompletedLoans = alias.getCompletedLoans() + 1;
        alias.setEquity(newEquity);
        alias.setCompletedLoans(newCompletedLoans);
        DataModel.getInstance().saveAliasData(alias);
        DataModel.getInstance().deleteLoan(loan);
    }

    public void modifyLoan(Loan loan, float newInterest) {
        loan.setInterest(newInterest);
        DataModel.getInstance().saveLoanData(loan);
    }

    public void CalculateInterest(Loan loan){
        this.amount = loan.getValue();
        this.interest = loan.getInterest();
        this.daysBetween = DAYS.between(loan.getStartDate(),loan.getDueDate());
        for(int i = 0; i<daysBetween;i++) {
            finalSum = this.amount * this.interest;
        }
    }
    public void setLoan(Loan loan){
        this.loan = loan;
    }
    public void setAmount(){
        this.amount = loan.getValue();
    }
}
