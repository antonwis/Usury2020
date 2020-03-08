package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Builds lists of data from database for other components to use. Used as a global singleton instance.
 */
public class DataModel {

    private static DataModel instance;

    public DataModel() { }

    public static DataModel getInstance() {
        if(instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    // User data group
    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);
    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }
    public final User getCurrentUser() {
        return currentUserProperty().get();
    }
    public final void setCurrentUser(User user) {
        currentUserProperty().set(user);
    }
    public ObservableList<User> getUserList() {
        return userList;
    }

    // Alias data group
    private final ObservableList<Alias> aliasList = FXCollections.observableArrayList();
    private final ObjectProperty<Alias> currentAlias = new SimpleObjectProperty<>(null);
    public ObjectProperty<Alias> currentAliasProperty() {
        return currentAlias;
    }
    public final Alias getCurrentAlias() {
        return currentAliasProperty().get();
    }
    public final void setCurrentAlias(Alias alias) {
        currentAliasProperty().set(alias);
    }
    public ObservableList<Alias> getAliasList() {
        return aliasList;
    }

    // Loan data group
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

    /*
        Database interaction methods
    */

    public void loadData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        userList.setAll(usuryDAO.readUsers());
        aliasList.setAll(usuryDAO.readAliases());
        loanList.setAll(usuryDAO.readLoans());
    }

    public void loadAliasData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        aliasList.setAll(usuryDAO.readAliases());
    }

    public void saveAliasData(Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
    }

    public void addNewAlias(User user, String name, String description, int equity){
        Alias alias = new Alias(user, name, description, equity);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createAlias(alias);
        aliasList.addAll(alias);
    }
    public void deleteAlias(Alias alias){
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteAlias(alias);
    }

    public void loadLoanData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        loanList.setAll(usuryDAO.readLoans());
    }

    public void saveLoanData(Loan loan, Victim victim, Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
        usuryDAO.updateVictim(victim);
        usuryDAO.updateLoan(loan);
    }

    public void saveLoanData(Loan loan) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateLoan(loan);
    }

    public void deleteLoan(Loan loan) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteLoan(loan);
    }

    public void loadUserData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        userList.setAll(usuryDAO.readUsers());
    }

    public void saveUserData(User user) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateUser(user);
    }

    public void addNewUser(String name){
        User user = new User(name);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createUser(user);
        userList.addAll(user);
    }




}
