package fi.metropolia.group8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    // ** General events ** //

    //MOTD - Welcome text
    public void printWelcome(User user) {
        eventList.setAll("Welcome " + DataModel.getInstance().getCurrentUser().getName() + "!\n" +
                "Start by choosing or creating a new alias.");
    }

    //Alias created
    public void aliasCreated(Alias alias) {
        eventList.add("New alias created: " + alias.getName() + ", Starting equity: " + alias.getEquity());
    }

    //Alias modified
    public String aliasModified() {
        return "";
    }

    //Alias selected
    public String aliasSelected() {
        return "";
    }

    //Loan created
    public String loanCreated(Loan loan) {
        return "";
    }

    //Loan complete
    public String loanCompleted(Loan loan) {
        return "";
    }

    //Loan modified
    public String loanModified(Loan loan) {
        return "";
    }

    //Loan forfeited
    public String loanForfeited(Loan loan) {
        return "";
    }

    //Loan repossessed
    public String loanRepossessed(Loan loan) {
        return "";
    }

    //Victim created
    public String victimCreated(Victim victim) {
        return "";
    }

    // ** Enforcement actions ** //

    // Threatening event
    public String threatEvent(VictimTraits trait) {
        switch (trait){
            case NORMIE:
                return "Threatening proved successful. Victim agreed to pay back 15% more";
            case SCARED:
                return "Scared victim agreed to pay you back tomorrow.";
            case JUNKIE:
                return "Victim was too high to even notice you.";
            case VIOLENT:
                return "Victim violently drove you away.";
            default:
                return "You threaten the debtor with violence. They agree to pay you back sooner.";
        }
    }

    // Extortion event
    public String extortionEvent(VictimTraits trait) {
        switch (trait){
            case SCARED:
                return "Victim was so scared they paid you back right on the spot.";
            default:
                return "After blackmailing the debtor they instantly pay you back half the remaining loan.";
        }
    }

    // Torture event
    public String tortureEvent(VictimTraits trait, float modifier) {
        switch (trait){
            case SCARED:
                return "Victim didn't survive. You manage to retrieve your initial investment.";
            default:
                return "You torture the poor debtor. They agree to pay an extra "+ modifier +"% over the original sum.";
        }
    }

    // Assassination event
    public String assassinationEvent(VictimTraits trait) {
        switch (trait){
            case SNEAKY:
                return "Victim was too sneaky. He got away. Your money was lost.";
            case VIOLENT:
                return "Victim fought back. Your money was lost.";
            default:
                return "Victim was murdered. Your money was retrieved.";
        }
    }



}
