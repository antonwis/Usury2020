package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;

/**
 * Provides content for the event log window. Generates status messages for changes in the application state.
 */
public class EventManager {

    private static EventManager instance;

    public EventManager() {}

    /**
     * Retrieves the global instance
     * @return returns the singleton instance
     */
    public static EventManager getInstance() {
        if(instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private final ObservableList<String> eventList = FXCollections.observableArrayList();
    private final SimpleStringProperty currentEvent = new SimpleStringProperty();
    public SimpleStringProperty currentEventProperty() {
        return currentEvent;
    }
    public final String getCurrentEvent() {
        return currentEventProperty().get();
    }
    public final void setCurrentEvent(String string) {
        currentEventProperty().set(string);
    }
    public ObservableList<String> getEventList() {
        return eventList;
    }


    /**
     * Method to add a new listener to eventList
     * @param newListener new change listener
     */
    public void addChangeListener(ListChangeListener<String> newListener) {
        eventList.addListener(newListener);
    }

    /**
     * Remove an existing listener from eventList
     * @param listener new change listener
     */
    public void removeChangeListener(ListChangeListener<String> listener) {
        eventList.removeListener(listener);
    }

    // ** EVENT LOG MESSAGE GENERATION METHODS ** \\

    // ** GENERAL EVENTS ** \\

    /**
     * Overwrites the event log list with a welcome text.
     * @param user user object to be greeted
     */
    public void printWelcome(User user) {
        eventList.setAll("Welcome " + user.getName() + "!\n" +
                "Start by choosing or creating a new alias.");
    }

    /**
     * Appends the event log list with an alias creation message.
     * @param alias being created
     */
    public void aliasCreated(Alias alias) {
        eventList.add("New alias created: " + alias.getName() + ", Starting equity: " + alias.getEquity());
    }

    /**
     * Appends the event log list with a message of an alias being modified.
     * @param alias being modified
     */
    public void aliasModified(Alias alias) {
        eventList.add("Alias modified: " + alias.getName());
    }

    /**
     * Appends the event log list with a message of an alias being selected.
     * @param alias being selected
     */
    public void aliasSelected(Alias alias) {
        eventList.add("Selected alias: " + alias.getName());
    }

    /**
     * Appends the event log list with a message of an alias getting deleted.
     * @param alias being deleted
     */
    public void aliasDeleted(Alias alias) {
        eventList.add("Alias deleted: " + alias.getName());
    }

    /**
     * Appends the event log list with a message of a loan being created
     * @param loan being created
     */
    public void loanCreated(Loan loan) {
        eventList.add("New loan. Value: " + loan.getValue() + ", Debtor: " + loan.getVictim().getName());
    }

    /**
     * Appends the event log list with a message of a loan being marked as completed
     * @param loan getting completed
     */
    public void loanCompleted(Loan loan) {
        eventList.add(loan.getVictim().getName() + " paid back his loan. A total of " + LoanCalculator.getInstance().getLoanTotalSum(loan) + " Shekels were added to your account.");
    }

    /**
     * Appends the event log list with a message of a loan being modified
     * @param loan being modified
     */
    public void loanModified(Loan loan) {
        eventList.add("Loan #" + loan.getId() + " was updated: Value: " + loan.getValue() + " Interest%: " + loan.getInterest() +
                " Debtor: " + loan.getVictim().getName() + " Due: " + loan.getDueDate());
    }

    /**
     * Appends the event log list with a message of a loan being forfeited and thus lost.
     * @param loan being forfeitted
     */
    public void loanForfeited(Loan loan) {
        eventList.add("Loan lost.");
    }

    /**
     * Appends the event log list with a message of a loan getting deleted
     * @param loan being deleted
     */
    public void loanDeleted(Loan loan) {
        eventList.add("Loan id:" + loan.getId() + " was deleted.");
    }

    /**
     * Appends the event log list with a message of a loan being repossessed by the Alias.
     * @param loan being repossessed
     */
    public void loanRepossessed(Loan loan) {
        eventList.add("Recovered amount: " + loan.getValue());
    }

    /**
     * Appends the event log list with a message of a victim being created
     * @param victim being created
     */
    public void victimCreated(Victim victim) {
        eventList.add("New victim " + victim.getName() + " was added to the record.");
    }

    /**
     * Appends the event log list with a message of a victim getting deleted
     * @param victim being deleted
     */
    public void victimDeleted(Victim victim) {
        eventList.add(victim.getName() + " was erased from the records.");
    }

    /**
     * Appends the event log list with a date change message
     * @param localDate date being changed
     */
    public void dateChanged(LocalDate localDate) {
        eventList.add("Date changed to " + localDate);
    }

    // ** ENFORCEMENT ACTIONS ** \\

    /**
     * Appends the event log list with status messages related to the threatening enforcement action.
     * @param victim victim to be threatened
     */
    public void threatEvent(Victim victim) {
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());
        switch (trait){
            case NORMIE:
                eventList.add("Threatening proved successful. Victim agreed to pay back 15% more");
                break;
            case SCARED:
                eventList.add("Scared victim agreed to pay you back tomorrow.");
                break;
            case JUNKIE:
                eventList.add("Victim was too high to even notice you.");
                break;
            case VIOLENT:
                eventList.add("Victim violently drove you away.");
                break;
            default:
                eventList.add("You threaten the debtor with violence. They agree to pay you back sooner.");
                break;
        }
    }

    /**
     * Appends the event log list with status messages related to the torture enforcement action.
     * @param victim victim to be blackmailed
     */
    public void extortionEvent(Victim victim) {
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());
        switch (trait){
            case SCARED:
                eventList.add("Victim was so scared they paid you back right on the spot.");
                break;
            default:
                eventList.add("After blackmailing the debtor they instantly pay you back half the remaining loan.");
                break;
        }
    }

    /**
     * Appends the event log list with status messages related to the torture enforcement action.
     * @param victim victim to be tortured
     * @param modifier modifier of the extra money promised through torture
     */
    public void tortureEvent(Victim victim, float modifier) {
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());
        switch (trait){
            case SCARED:
                eventList.add("Victim didn't survive. You manage to retrieve your initial investment.");
                break;
            default:
                eventList.add("You torture the poor debtor. They agree to pay an extra "+ modifier +"% over the original sum.");
                break;
        }
    }

    /**
     * Appends the event log list with status messages related to the assassination enforcement action.
     * @param victim victim to be assassinated
     */
    public void assassinationEvent(Victim victim) {
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());
        switch (trait){
            case SNEAKY:
                eventList.add("Turns out " + victim.getName() + " was a sneaky little character. He got away with all of your money.");
                break;
            case VIOLENT:
                eventList.add("Victim fought back. Your money was lost.");
                break;
            default:
                eventList.add("Victim was murdered. Your money was retrieved.");
                break;
        }
    }



}
