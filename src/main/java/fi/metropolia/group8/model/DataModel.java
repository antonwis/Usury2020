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

    /**
     * Retrieves the global instance
     * @return returns the singleton instance
     */
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

    /**
     * Refreshes user, alias and loan lists from database
     */
    public void loadData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        userList.setAll(usuryDAO.readUsers());
        aliasList.setAll(usuryDAO.readAliases());
        loanList.setAll(usuryDAO.readLoans());
    }

    /**
     * Refreshes alias list from database
     */
    public void loadAliasData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        aliasList.setAll(usuryDAO.readAliases());
    }

    /**
     * Sends modified alias to data access object to be updated
     * @param alias alias to be updated in database
     */
    public void saveAliasData(Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
    }

    /**
     * Sends a new alias object to the data access object to be added to database
     * @param user user object for which the new alias belongs to
     * @param name name of the alias
     * @param description description for the alias
     * @param equity amount of money the new alias has
     */
    public void addNewAlias(User user, String name, String description, int equity){
        Alias alias = new Alias(user, name, description, equity);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createAlias(alias);
        aliasList.addAll(alias);
        setCurrentAlias(alias);
    }

    /**
     * Sends a alias object to the data access object to be deleted from database
     * @param alias alias to be deleted
     */
    public void deleteAlias(Alias alias){
        UsuryDAO usuryDAO = new UsuryDAO();
        if(getCurrentAlias() != null && alias.getName().equals(getCurrentAlias().getName())){
            setCurrentAlias(null);
        }
        usuryDAO.deleteAlias(alias);
    }

    /**
     * Refreshes loan list from database
     */
    public void loadLoanData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        loanList.setAll(usuryDAO.readLoans());
    }

    /**
     * Sends a new loan object with a new victim and the current alias object to data access object to be updated
     * @param loan loan to be updated in database
     * @param alias alias to be updated in database
     * @param victim victim object to be added to database
     */
    public void saveLoanData(Loan loan, Victim victim, Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
        usuryDAO.updateVictim(victim);
        usuryDAO.updateLoan(loan);
    }

    /**
     * Sends modified loan to data access object to be updated
     * @param loan loan to be updated in database
     */
    public void saveLoanData(Loan loan) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateLoan(loan);
    }

    /**
     * Sends a loan object to the data access object to be deleted from database
     * @param loan alias to be deleted
     */
    public void deleteLoan(Loan loan) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteLoan(loan);
    }

    /**
     * Refreshes user list from database
     */
    public void loadUserData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        userList.setAll(usuryDAO.readUsers());
    }

    /**
     * Sends modified user to data access object to be updated
     * @param user user to be updated in database
     */
    public void saveUserData(User user) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateUser(user);
    }

    /**
     * Creates a new user object and sends it to the data access object to be added to database
     * @param name name of the user
     */
    public void addNewUser(String name){
        User user = new User(name);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createUser(user);
        userList.addAll(user);
    }




}
