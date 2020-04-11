package fi.metropolia.group8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Temporary victim objects created by Victim Generator. These are not saved into the database.
 * Generated victims are not assigned a trait or an id. Those are assigned when the actual Victim objects
 * are properly created by the DataModel class. Generated Victims are used as a base for actual Victim creation.
 */
public class GeneratedVictim {

    private StringProperty name;
    private StringProperty address;
    private StringProperty description;
    private boolean alive;


    /**
     * Constructor for the Generated Victim object. Unlike Victim objects, GeneratedVictims are not assigned a trait or
     * an id, and are not added to the database.
     * @param name name of the victim
     * @param address victim's address
     * @param description string describing the victim
     */
    public GeneratedVictim(String name, String address, String description) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.description = new SimpleStringProperty(description);
        this.alive = true;
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
     * @return returns the description of the temporary victim object
     */
    public String getDescription() {
        return descriptionProperty().get();
    }

    /**
     * Set a new description
     * @param value new value for the description
     */
    public void setDescription(String value) {
        descriptionProperty().set(value);
    }

    /**
     * @return returns the description property value
     */
    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this, "description");
        return description;
    }

    /**
     * @return checks if the victim is still alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set the alive-status
     * @param alive true if alive, false if dead
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Override for toString()
     * @return returns a string containing the relevant information about the temporary victim object
     */
    @Override
    public String toString() {
        return getName() + ", " + getAddress() + ", " + getDescription();
    }



}


