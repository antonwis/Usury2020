package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class LoanDataModel {

    private final ObservableList<Loan> loanList = FXCollections.observableArrayList();
    private final ObjectProperty<Loan> currentLoan = new SimpleObjectProperty<>(null);


    public ObjectProperty<Loan> currentLoanProperty() {
        return currentLoan;
    }

    public final Loan getCurrentLoan() {
        return currentLoanProperty().get();
    }

    public final void setCurrentLoan(Loan loan) {
        currentLoanProperty().set(loan);
    }

    public ObservableList<Loan> getLoanList() {
        return loanList;
    }

    public void loadData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        loanList.setAll(usuryDAO.readLoans());
    }

    public void saveData(Loan loan, Victim victim, Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
        usuryDAO.updateVictim(victim);
        usuryDAO.updateLoan(loan);
    }

    public void testDao() {
        UsuryDAO usuryDAO = new UsuryDAO();

        Alias testAlias = new Alias("Seppo", "Testimies", 1000);
        Victim testVictim = new Victim("Kake", "Piritori", "Nisti");
        Victim testVictim2 = new Victim("Keijo", "Narkkikatu 2", "Työtön");
        Loan testLoan1 = new Loan(testAlias, 500, testVictim, LocalDate.now(), LocalDate.now(), 2);
        Loan testLoan2 = new Loan(testAlias, 800, testVictim2, LocalDate.now(), LocalDate.now(), 3);
        Loan testLoan3 = new Loan(testAlias, 2200, testVictim, LocalDate.now(), LocalDate.now(), 1);
        Loan testLoan4 = new Loan(testAlias, 50, testVictim2, LocalDate.now(), LocalDate.now(), 1);
        Loan testLoan5 = new Loan(testAlias, 9000, testVictim, LocalDate.now(), LocalDate.now(), 1);

        // lel
        usuryDAO.createAlias(testAlias);
        usuryDAO.createVictim(testVictim);
        usuryDAO.createVictim(testVictim2);
        usuryDAO.createLoan(testLoan1);
        usuryDAO.createLoan(testLoan2);
        usuryDAO.createLoan(testLoan3);
        usuryDAO.createLoan(testLoan4);
        usuryDAO.createLoan(testLoan5);

        loanList.setAll(testLoan1, testLoan2, testLoan3, testLoan4, testLoan5);
    }

    // Loanlist for testing
/*    public void loadTestData() {
        Alias testAlias = new Alias("Seppo", "Testimies", 1000);
        Victim testVictim = new Victim("Kake", "Piritori", "Nisti");
        Victim testVictim2 = new Victim("Keijo", "Narkkikatu 2", "Työtön");
        Loan testLoan1 = new Loan(testAlias, 500, testVictim, LocalDate.now(), LocalDate.now(), 2);
        Loan testLoan2 = new Loan(testAlias, 800, testVictim2, LocalDate.now(), LocalDate.now(), 3);
        Loan testLoan3 = new Loan(testAlias, 2200, testVictim, LocalDate.now(), LocalDate.now(), 1);
        Loan testLoan4 = new Loan(testAlias, 50, testVictim2, LocalDate.now(), LocalDate.now(), 1);
        Loan testLoan5 = new Loan(testAlias, 9000, testVictim, LocalDate.now(), LocalDate.now(), 1);

        loanList.setAll(testLoan1, testLoan2, testLoan3, testLoan4, testLoan5);
    }*/

}
