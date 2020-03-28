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
     * @param name
     * @param address
     * @param description
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
     *
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    @Column(name = "name")
    public String getName() { return nameProperty().get(); }
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this,"name") ;
        return name;
    }

    /**
     *
     * @param value
     */
    public void setName(String value) { nameProperty().set(value); }

    /**
     *
     * @return
     */
    @Column(name="address")
    public String getAddress() {
        return addressProperty().get();
    }

    /**
     *
     * @param value
     */
    public void setAddress(String value) {
        addressProperty().set(value);
    }

    /**
     *
     * @return
     */
    public StringProperty addressProperty() {
        if (address == null) address = new SimpleStringProperty(this,"address") ;
        return address;
    }

    /**
     *
     * @return
     */
    @Column(name="description")
    public String getDescription() {
        return descriptionProperty().get();
    }

    /**
     *
     * @param value
     */
    public void setDescription(String value) {
        descriptionProperty().set(value);
    }

    /**
     *
     * @return
     */
    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this,"description") ;
        return description;
    }

    /**
     *
     * @return
     */
    @Column(name="trait")
    public String getTrait() {
        return traitProperty().get();
    }

    /**
     *
     * @param value
     */
    public void setTrait(String value) {
        traitProperty().set(value);
    }

    /**
     *
     * @return
     */
    public StringProperty traitProperty() {
        if (trait == null) trait = new SimpleStringProperty(this,"trait") ;
        return trait;
    }

    /**
     * @return
     */
    @Column(name = "alive")
    public boolean isAlive() {
        return alive;
    }

    /**
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * to string method for victim
     * @return
     */
    @Override
    public String toString(){
        return this.name+", "+this.address+", "+this.description;
    }
}
