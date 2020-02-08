package fi.metropolia.group8.model;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanCalculator {
    private float amount;
    private float interest;
    private Loan loan;
    private long daysBetween;
    private float finalSum;

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
