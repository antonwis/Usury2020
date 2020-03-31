package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;

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


    // Method to setup listener
    public void addChangeListener(ListChangeListener<String> newListener) {
        eventList.addListener(newListener);
    }


    // ** General events ** //

    //MOTD - Welcome text
    public void printWelcome(User user) {
        eventList.setAll("Welcome " + user.getName() + "!\n" +
                "Start by choosing or creating a new alias.");
    }

    //Alias created
    public void aliasCreated(Alias alias) {
        eventList.add("New alias created: " + alias.getName() + ", Starting equity: " + alias.getEquity());
    }

    //Alias modified
    public void aliasModified(Alias alias) {
        eventList.add("Alias modified: " + alias.getName());
    }

    //Alias selected
    public void aliasSelected(Alias alias) {
        eventList.add("Selected alias: " + alias.getName());
    }

    //Loan created
    public void loanCreated(Loan loan) {
        eventList.add("New loan. Value: " + loan.getValue() + ", Debtor: " + loan.getVictim().getName());
    }

    //Loan complete
    public void loanCompleted(Loan loan) {
        eventList.add("Loan #" + loan.getId() + " completed. Total amount earned: " + LoanCalculator.getInstance().getLoanTotalSum(loan));
    }

    //Loan modified
    public void loanModified(Loan loan) {
        eventList.add("Loan modified.");
    }

    //Loan forfeited
    public void loanForfeited(Loan loan) {
        eventList.add("Loan lost.");
    }

    //Loan repossessed
    public void loanRepossessed(Loan loan) {
        eventList.add("Recovered amount: " + loan.getValue());
    }

    //Victim created
    public void victimCreated(Victim victim) {
        eventList.add("New victim: " + victim.getName());
    }

    // ** Enforcement actions ** //

    // Threatening event
    public void threatEvent(VictimTraits trait) {
        switch (trait){
            case NORMIE:
                eventList.add("Threatening proved successful. Victim agreed to pay back 15% more");
            case SCARED:
                eventList.add("Scared victim agreed to pay you back tomorrow.");
            case JUNKIE:
                eventList.add("Victim was too high to even notice you.");
            case VIOLENT:
                eventList.add("Victim violently drove you away.");
            default:
                eventList.add("You threaten the debtor with violence. They agree to pay you back sooner.");
        }
    }

    // Extortion event
    public void extortionEvent(VictimTraits trait) {
        switch (trait){
            case SCARED:
                eventList.add("Victim was so scared they paid you back right on the spot.");
            default:
                eventList.add("After blackmailing the debtor they instantly pay you back half the remaining loan.");
        }
    }

    // Torture event
    public void tortureEvent(VictimTraits trait, float modifier) {
        switch (trait){
            case SCARED:
                eventList.add("Victim didn't survive. You manage to retrieve your initial investment.");
            default:
                eventList.add("You torture the poor debtor. They agree to pay an extra "+ modifier +"% over the original sum.");
        }
    }

    // Assassination event
    public void assassinationEvent(VictimTraits trait) {
        switch (trait){
            case SNEAKY:
                eventList.add("Victim was too sneaky. He got away. Your money was lost.");
            case VIOLENT:
                eventList.add("Victim fought back. Your money was lost.");
            default:
                eventList.add("Victim was murdered. Your money was retrieved.");
        }
    }



}
