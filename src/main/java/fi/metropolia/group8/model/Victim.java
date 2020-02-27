package fi.metropolia.group8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table
public class Victim {

    private long id;

    private StringProperty name;
    private StringProperty address;
    private StringProperty description;

    // Empty Constructor for hibernate
    public Victim() {

    }

    public Victim(String name, String address, String description) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.description = new SimpleStringProperty(description);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Column(name = "address")
    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        if (address == null) address = new SimpleStringProperty(this, "address");
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


    @Column(name = "description")
    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this, "description");
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }


    @Override
    public String toString() {
        return this.name + ", " + this.address + ", " + this.description;
    }
}
