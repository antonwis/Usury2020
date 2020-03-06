package fi.metropolia.group8.model;

import javafx.beans.property.*;

import javax.persistence.*;


@Entity
@Table(name="Alias")
@Access(value= AccessType.PROPERTY)
public class Alias {

    private long id;
    private StringProperty name;
    private StringProperty description;
    private FloatProperty equity;


    public Alias() {
        // Empty constructor for hibernate
    }

    public Alias(String name, String description, int equity) {
        setName(name);
        setDescription(description);
        setEquity(equity);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long getId() { return id; }

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


    public void setDescription(String value) { descriptionProperty().set(value); }

    @Column(name="description")
    public String getDescription() { return descriptionProperty().get(); }

    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this,"description") ;
        return description;
    }

    public void setEquity(float value) { equityProperty().set(value); }

    @Column(name="equity")
    public float getEquity() { return equityProperty().get(); }

    public FloatProperty equityProperty() {
        if(equity == null) equity = new SimpleFloatProperty(this, "equity");
        return equity;
    }


}
