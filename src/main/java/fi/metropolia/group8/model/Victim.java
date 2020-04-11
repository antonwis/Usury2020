package fi.metropolia.group8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Random;

/**
 * model for victim
 */
@Entity
@Table
public class Victim {

    private long id;

    private StringProperty name;
    private StringProperty address;
    private StringProperty description;
    private StringProperty trait;
    private boolean alive;

    // Empty Constructor for hibernate
    public Victim() {

    }

    /**
     * constructor
     *
     * @param name name of the victim
     * @param address address of the victim
     * @param description small description about the victim
     */
    public Victim(String name, String address, String description) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.description = new SimpleStringProperty(description);
        this.alive = true;
        // Pulls a random trait from an enum list
        this.trait = new SimpleStringProperty(VictimTraits.values()[new Random().nextInt(VictimTraits.values().length)].name());
    }

    /**
     * @return returns victims id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     * @param id sets the victim id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return returns the name of the victim
     */
    @Column(name = "name")
    public String getName() {
        return nameProperty().get();
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    /**
     * @param value value of the new name
     */
    public void setName(String value) {
        nameProperty().set(value);
    }

    /**
     * @return gets the victims address
     */
    @Column(name = "address")
    public String getAddress() {
        return addressProperty().get();
    }

    /**
     * @param value new value for the victims adddress
     */
    public void setAddress(String value) {
        addressProperty().set(value);
    }

    /**
     * @return returns the address property value
     */
    public StringProperty addressProperty() {
        if (address == null) address = new SimpleStringProperty(this, "address");
        return address;
    }

    /**
     * @return returns the description of the victim
     */
    @Column(name = "description")
    public String getDescription() {
        return descriptionProperty().get();
    }

    /**
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
     * @return returns all the traits that the victim has
     */
    @Column(name = "trait")
    public String getTrait() {
        return traitProperty().get();
    }

    /**
     * @param value sets the trait for the victim
     */
    public void setTrait(String value) {
        traitProperty().set(value);
    }

    /**
     * @return returns the trait property value
     */
    public StringProperty traitProperty() {
        if (trait == null) trait = new SimpleStringProperty(this, "trait");
        return trait;
    }

    /**
     * @return checks if the victim is still alive
     */
    @Column(name = "alive")
    public boolean isAlive() {
        return alive;
    }

    /**
     * @param alive kill the victim
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * to string method for victim
     *
     * @return returns all the relevant information about the victim
     */
    @Override
    public String toString() {
        return getName() + ", " + getAddress() + ", " + getDescription();
    }
}
