package fi.metropolia.group8.model;

import java.time.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanCalculator {
    private float amount;
    private float intrest;
    private Loan loan;
    private long daysBetween;
    private float finalSum;

    public void CalculateInterest(Loan loan){
        this.amount = loan.getValue();
        this.intrest = loan.getIntrest();
        this.daysBetween = DAYS.between(loan.getLoanTakenDate(),loan.getDueDate());
        for(int i = 0; i<daysBetween;i++) {
            finalSum = this.amount * this.intrest;
        }
    }
    public void setLoan(Loan loan){
        this.loan = loan;
    }
    public void setAmount(){
        this.amount = loan.getValue();
    }
}
