package fi.metropolia.group8.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table
public class Victim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty description;

    public Victim(String name, String address, String description) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.description = new SimpleStringProperty(description);
    }
    public void setName(String value) { nameProperty().set(value); }

    @Column(name = "name")
    public String getName() { return nameProperty().get(); }
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this,"name") ;
        return name;
    }

    @Column(name="address")
    public final String getAddress() {
        return addressProperty().get();
    }

    public final void setAddress(String value) {
        addressProperty().set(value);
    }

    //Interest property stuff
    public StringProperty addressProperty() {
        return address;
    }

    @Column(name="description")
    public final String getDescription() {
        return descriptionProperty().get();
    }

    public final void setDescription(String value) {
        descriptionProperty().set(value);
    }

    //Interest property stuff
    public StringProperty descriptionProperty() {
        return description;
    }


    @Override
    public String toString(){
        return this.name+", "+this.address+", "+this.description;
    }
}
