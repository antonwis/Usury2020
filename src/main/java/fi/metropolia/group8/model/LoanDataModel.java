package fi.metropolia.group8.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;

public class LoanDataModel {

    private final ObservableList<Loan> loanList = FXCollections.observableArrayList(loan ->
            new Observable[] {loan.getOwner().nameProperty(), loan.valueProperty(), loan.getVictim().nameProperty(), loan.interestProperty()});

    private final ObjectProperty<Loan> currentLoan = new SimpleObjectProperty<>(null);


    public ObjectProperty<Loan> currentLoanProperty() {
        return currentLoan ;
    }

    public final Loan getCurrentLoan() {
        return currentLoanProperty().get();
    }

    public final void setCurrentLoan(Loan loan) {
        currentLoanProperty().set(loan);
    }

    public ObservableList<Loan> getLoanList() {
        return loanList ;
    }

    public void loadData(File file) {

        // newit pois ja tilalle viitteet oikeisiin juttuihin
        loanList.setAll(
                new Loan(new Alias("Ben Shapiro", "Law Jew", 9000), 2000, new Victim("Jani Toivola", "Homokuja 2", "Ajeli taksilla"), LocalDate.now(), LocalDate.now(), 2)
        );
    }
    public void kyslol(){
        loanList.setAll(
                new Loan(new Alias("Ben Shapiro", "Law Jew", 9000), 2000, new Victim("Jani Toivola", "Homokuja 2", "Ajeli taksilla"), LocalDate.now(), LocalDate.now(), 2)
        );
    }
    public void saveData(File file) {
        // kys2
    }

    // Loanlist for testing
    public void loadTestData() {
        Alias testAlias = new Alias("Seppo", "Testimies", 1000);
        Victim testVictim = new Victim("Kake", "Piritori", "Nisti");
        Loan testLoan = new Loan(testAlias, 500, testVictim, LocalDate.now(), LocalDate.now(), 2);

        loanList.setAll(testLoan);
    }

}
