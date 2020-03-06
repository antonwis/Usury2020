package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.function.Predicate;

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
}
