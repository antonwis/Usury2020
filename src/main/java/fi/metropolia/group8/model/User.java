package fi.metropolia.group8.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * model for user
 */
@Entity
@Table(name="User")
@Access(value= AccessType.PROPERTY)
public class User {

    private long id;
    private StringProperty name;

    public User(){
        // Empty Constructor for hibernate
    }
    public User(String name){
        setName(name);
    }

    /**
     *
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
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
     * @param value
     */
    public void setName(String value) { nameProperty().set(value); }

    /**
     *
     * @return
     */
    @Column(name="name")
    public String getName() { return nameProperty().get(); }

    /**
     *
     * @return
     */
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this,"name") ;
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }
}
