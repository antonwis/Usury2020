package fi.metropolia.group8.model;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorTest {
    private User user;
    private Alias alias;
    private Loan loan;
    private Victim victim;
    private GeneratedVictim generatedVictim;
    private LoanCalculator loanCalculator;
    @BeforeEach
    void setUp() {
        loanCalculator = new LoanCalculator();
        user = new User("testi");
        DataModel.getInstance().setCurrentUser(user);
        alias = new Alias(user,"testi","testi",100);
        victim = new Victim("testi","osoite");
        loan = new Loan(alias,50,victim, LocalDate.now(),LocalDate.now(),100);
        VictimGenerator gen = new VictimGenerator();
        gen.generateVictimList(1);
        ObservableList ob = gen.getGeneratedVictimList();
        generatedVictim = (GeneratedVictim) ob.get(0);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        LoanCalculator l = LoanCalculator.getInstance();
        assertNotNull(l);
    }

    @Test
    void getLoanTotalSum() {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        assertEquals(100, loan.getValue()+interest , "");
    }

    @Test
    void getLoanOfferTotalSum() {
    }

    @Test
    void getInterestProfit() {
        float interest = (loan.getInterest() / 100) * loan.getValue();
        assertEquals(50, interest , "");
    }

    @Test
    void getOfferInterestProfit() {
        float interest = (generatedVictim.getInterest() / 100) * generatedVictim.getValue();
        assertEquals((generatedVictim.getInterest() / 100) * generatedVictim.getValue(), interest , "");
    }

    @Test
    void updateEquity() {
        float newEquity = alias.getEquity() - loan.getValue();
        assertEquals(50, newEquity);
    }

    @Test
    void completeLoan() {
        float totalSum = loanCalculator.getLoanTotalSum(loan);
        float totalProfit = loanCalculator.getInterestProfit(loan);
        float newProfitTotal = alias.getTotalProfits()+totalProfit;
        float newEquity = alias.getEquity() + totalSum;
        int completedLoans = alias.getCompletedLoans() + 1;
        alias.setEquity(newEquity);
        alias.setTotalProfits(newProfitTotal);
        alias.setCompletedLoans(completedLoans);
        loan.setCompleted(true);
        loan.setCompleteDate(LocalDate.now());
        assertEquals(100, totalSum, "");
        assertEquals(50, totalProfit, "");
        assertEquals(50, alias.getTotalProfits(), "");
        assertEquals(200, alias.getEquity(), "");
        assertEquals(1, alias.getCompletedLoans(), "");
        assertEquals(true, loan.isCompleted(), "");
        assertEquals(LocalDate.now(), loan.getCompleteDate(), "");
        this.loan = new Loan(alias,50,victim, LocalDate.now(),LocalDate.now(),100);
        this.alias = new Alias(user,"testi","testi",100);
    }

    @Test
    void modifyLoan() {
        LocalDate l = LocalDate.of(2020, 12, 12);
        loan.setDueDate(l);
        loan.setInterest(150);
        assertEquals(l, loan.getDueDate(), "");
        assertEquals(150, loan.getInterest(), "");
        this.loan = new Loan(alias,50,victim, LocalDate.now(),LocalDate.now(),100);
    }

    @Test
    void forfeitLoan() {
    }

    @Test
    void repoLoan() {
    }

    @Test
    void payHalfLoan() {
    }

    @Test
    void modifyLoanValue() {
        float modifier = 100;
        float newValue = (loan.getValue() * (1 +(modifier / 100)));
        loan.setValue(newValue);
        assertEquals(100, loan.getValue(), "");
    }
}