package fi.metropolia.group8.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Temporary victim objects created by Victim Generator. These are not saved into the database. Contains template data for
 * Victim and Loan object creation. Generated victims are not assigned a trait or an id. Those are assigned when
 * the actual Victim objects are properly created by the DataModel class. Generated Victims are used as a base
 * for actual Victim and Loan creation. This is used as a helper class to prevent temporary objects from flooding the database.
 */
public class GeneratedVictim {

    // Template data for Victim object creation
    private StringProperty name;
    private StringProperty address;

    // Template data for Loan object creation
    private FloatProperty value;
    private FloatProperty interest;
    private LocalDate startDate;
    private LocalDate dueDate;

    /**
     * Constructor for the Generated Victim object. Unlike Victim objects, GeneratedVictims are not assigned a trait or
     * an id, and are not added to the database.
     * @param name name of the victim
     * @param address victim's address
     * @param value generated loan value
     * @param interest generated loan interest value
     * @param startDate generated issue date. This is typically the current user's current working date
     * @param dueDate generated default due date. Typically 14 days after issue date.
     */
    public GeneratedVictim(String name, String address, float value, float interest, LocalDate startDate, LocalDate dueDate) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.value = new SimpleFloatProperty(value);
        this.interest = new SimpleFloatProperty(interest);
        this.startDate = startDate;
        this.dueDate = dueDate;
    }


    /**
     * @return returns the name of the victim
     */
    public String getName() {
        return nameProperty().get();
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    /**
     * Set a new name
     * @param name new name
     */
    public void setName(String name) {
        nameProperty().set(name);
    }

    /**
     * @return gets the victim's address
     */
    public String getAddress() {
        return addressProperty().get();
    }

    /**
     * Set a new address
     * @param address victim's address
     */
    public void setAddress(String address) {
        addressProperty().set(address);
    }

    /**
     * @return returns the address property value
     */
    public StringProperty addressProperty() {
        if (address == null) address = new SimpleStringProperty(this, "address");
        return address;
    }

    /**
     * @return returns loan value property value
     */
    public FloatProperty valueProperty() {
        if (value == null) value = new SimpleFloatProperty(this, "value");
        return value;
    }

    /**
     * @return returns the generated float value of the potential loan
     */
    public float getValue() {
        return valueProperty().get();
    }

    /**
     * @param value Sets a new value for the potential loan
     */
    public void setValue(float value) {
        valueProperty().set(value);
    }

    /**
     * @return returns the interest property value
     */
    public FloatProperty interestProperty() {
        if (interest == null) interest = new SimpleFloatProperty(this, "interest");
        return interest;
    }

    /**
     * @return retruns the interest property value
     */
    public float getInterest() {
        return interestProperty().get();
    }

    /**
     * @param value sets a new interest value for the potential loan
     */
    public void setInterest(float value) {
        interestProperty().set(value);
    }

    /**
     * @return returns the potential loan start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate sets a new issue date for the potential loan
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return returns the potential loan's due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate sets a new due date for the potential loan
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * Override for toString()
     * @return returns a string containing the relevant information about the temporary victim object
     */
    @Override
    public String toString() {
        return getName() + ", " + getAddress();
    }

}


