package fi.metropolia.group8.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * model for user
 */
@Entity
@Table(name="User")
@Access(value= AccessType.PROPERTY)
public class User {

    private long id;
    private StringProperty name;
    private LocalDate currentDate;

    public User(){
        // Empty Constructor for hibernate
    }
    public User(String name){
        setName(name);
        this.currentDate = LocalDate.now();
    }

    /**
     *
     * @return user id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long getId() {
        return id;
    }

    /**
     *
     * @param id user id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @param value name of the user
     */
    public void setName(String value) { nameProperty().set(value); }

    /**
     *
     * @return name of the user
     */
    @Column(name="name")
    public String getName() { return nameProperty().get(); }

    /**
     *
     * @return name
     */
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this,"name") ;
        return name;
    }

    /**
     * Returns the current working date object
     * @return current working date
     */
    @Column(name = "currentDate")
    public LocalDate getCurrentDate() { return currentDate; }

    /**
     *
     * @param date LocalDate object to replace current working date
     */
    public void setCurrentDate(LocalDate date) { this.currentDate = date; }

    /**
     *
     * @return user info
     */
    @Override
    public String toString() {
        return getName();
    }
}
