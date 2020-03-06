package fi.metropolia.group8.model;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanCalculator {
    private float amount;
    private float interest;
    private Loan loan;
    private long daysBetween;
    private float finalSum;

    private LoanDataModel loanDataModel;
    private AliasDataModel aliasDataModel;

    public LoanCalculator(LoanDataModel loanDataModel, AliasDataModel aliasDataModel) {
        this.loanDataModel = loanDataModel;
        this.aliasDataModel = aliasDataModel;
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
        aliasDataModel.saveData(alias);
    }

    // Add loan total value to alias equity and delete loan from database
    public void completeLoan(Alias alias, Loan loan) {
        float totalProfit = getLoanTotalSum(loan);
        float newEquity = alias.getEquity() + totalProfit;
        alias.setEquity(newEquity);
        aliasDataModel.saveData(alias);
        loanDataModel.deleteLoan(loan);
    }

    public void modifyLoan(Loan loan, float newInterest) {
        loan.setInterest(newInterest);
        loanDataModel.saveData(loan);
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
