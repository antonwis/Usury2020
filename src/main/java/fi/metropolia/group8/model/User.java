package fi.metropolia.group8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table
public class User {

    private long id;
    private StringProperty name;

    // TODO joku yhteys userille ja aliaksille
    private Alias[] aliases;


    public User(){
        // Empty Constructor for hibernate
    }
    public User(String name){
        setName(name);

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String value) { nameProperty().set(value); }

    @Column(name="name")
    public String getName() { return nameProperty().get(); }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this,"name") ;
        return name;
    }
    @Override
    public String toString() {
        return getName();
    }
}
