package fi.metropolia.group8.model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javax.swing.event.ChangeListener;
import java.time.LocalDate;
import java.util.Random;

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
        EventManager.getInstance().aliasSelected(alias);
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

    // Victim data group
    private final ObservableList<Victim> victimList = FXCollections.observableArrayList();
    private final ObjectProperty<Victim> currentVictim = new SimpleObjectProperty<>(null);
    public ObjectProperty<Victim> currentVictimProperty() {
        return currentVictim;
    }
    public final Victim getCurrentVictim() {
        return currentVictimProperty().get();
    }
    public final void setCurrentVictim(Victim victim) {
        currentVictimProperty().set(victim);
    }
    public ObservableList<Victim> getVictimList() {
        return victimList;
    }

    /*
        Observer & listener methods
    */

    /**
     * Method to add a new listener to userList
     * @param newListener
     */
    public void addUserListChangeListener(ListChangeListener<User> newListener) {
        userList.addListener(newListener);
    }

    /**
     * Remove an existing listener from userList
     * @param listener
     */
    public void removeUserChangeListener(ListChangeListener<User> listener) { userList.removeListener(listener);
    }

    /**
     * Method to add a new listener to aliasList
     * @param newListener
     */
    public void addAliasListChangeListener(ListChangeListener<Alias> newListener) { aliasList.addListener(newListener);
    }

    /**
     * Remove an existing listener from aliasList
     * @param listener
     */
    public void removeAliasChangeListener(ListChangeListener<Alias> listener) { aliasList.removeListener(listener);
    }

    /**
     * Method to add a new listener to loanList
     * @param newListener
     */
    public void addLoanListChangeListener(ListChangeListener<Loan> newListener) {
        loanList.addListener(newListener);
    }

    /**
     * Remove an existing listener from loanList
     * @param listener
     */
    public void removeLoanChangeListener(ListChangeListener<Loan> listener) { loanList.removeListener(listener); }

    /**
     * Method to add a new listener to victimList
     * @param newListener
     */
    public void addVictimListChangeListener(ListChangeListener<Victim> newListener) { victimList.addListener(newListener); }

    /**
     * Remove an existing listener from victimList
     * @param listener
     */
    public void removeVictimChangeListener(ListChangeListener<Victim> listener) { victimList.removeListener(listener);
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
     * Sends modified alias to the DAO for saving. Calls event log manager.
     * @param alias alias to be updated in database
     */
    public void saveAliasData(Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
        //EventManager.getInstance().aliasModified(alias);
        loadAliasData();
    }

    /**
     * Creates a new alias object and sends it to the DAO. Also calls event log manager.
     * @param name name of the alias
     * @param description description for the alias
     * @param equity amount of money the new alias has
     */
    public void createAlias(String name, String description, int equity){
        User user = getCurrentUser();
        Alias alias = new Alias(user, name, description, equity);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createAlias(alias);
        EventManager.getInstance().aliasCreated(alias);
        loadAliasData();
        setCurrentAlias(alias);
    }

    /**
     * Sends an alias object to the DAO for deletion. Also calls event log manager.
     * @param alias alias to be deleted
     */
    public void deleteAlias(Alias alias){
        UsuryDAO usuryDAO = new UsuryDAO();
        if(getCurrentAlias() != null && alias.getName().equals(getCurrentAlias().getName())){
            setCurrentAlias(null);
        }
        usuryDAO.deleteAlias(alias);
        loadAliasData();
        EventManager.getInstance().aliasDeleted(alias);
    }

    /**
     * Refreshes loan list from database
     */
    public void loadLoanData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        loanList.setAll(usuryDAO.readLoans());
    }

    /**
     * Creates a new loan and sends it to the DAO. Notifies event log manager.
     * @param alias
     * @param value
     * @param victim
     * @param startDate
     * @param dueDate
     * @param interest
     * @return Returns the new loan object
     */
    public Loan createLoan(Alias alias, float value, Victim victim, LocalDate startDate, LocalDate dueDate, float interest) {
        Loan loan = new Loan(alias, value, victim, startDate, dueDate, interest);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createLoan(loan);
        EventManager.getInstance().loanCreated(loan);
        loadLoanData();
        return loan;
    }

    /**
     * Sends modified loan the DAO to be updated. Notifies event log manager.
     * @param loan loan to be updated in database
     */
    public void saveLoanData(Loan loan) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateLoan(loan);
        //EventManager.getInstance().loanModified(loan);
        loadLoanData();
    }

    /**
     * Sends a loan object the DAO for deletion. Notifies event log manager.
     * @param loan alias to be deleted
     */
    public void deleteLoan(Loan loan) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteLoan(loan);
        EventManager.getInstance().loanDeleted(loan);
        loadLoanData();
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
        loadUserData();
    }

    /**
     * Creates a new user object and sends it to the data access object to be added to database
     * @param name name of the user
     */
    public User createUser(String name){
        User user = new User(name);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createUser(user);
        loadUserData();
        return user;
    }

    /**
     * Deletes the user object from database
     * @param user
     */
    public void deleteUser(User user) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteUser(user);
        loadUserData();
    }

    /**
     * Refreshes victim list from database
     */
    public void loadVictimData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        victimList.setAll(usuryDAO.readVictims());
    }

    /**
     * Sends modified victim object to the DAO
     * @param victim victim to be updated in database
     */
    public void saveVictimData(Victim victim) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateVictim(victim);
        loadVictimData();
    }

    /**
     * Sends a new victim object to the DAO. Notifies event log manager.
     * @param name victim's name
     * @param address victim's address
     * @param description description for the victim
     */
    public Victim createVictim(String name, String address, String description){
        Victim victim = new Victim(name, address, description);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createVictim(victim);
        EventManager.getInstance().victimCreated(victim);
        loadVictimData();
        return victim;
    }

    /**
     * Deletes a victim from database. Notifies event log manager.
     * @param victim
     */
    public void deleteVictim(Victim victim) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteVictim(victim);
        EventManager.getInstance().victimDeleted(victim);
        loadVictimData();
    }




}
