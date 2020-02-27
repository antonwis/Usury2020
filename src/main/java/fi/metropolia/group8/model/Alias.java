package fi.metropolia.group8.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;


@Entity
@Table(name = "Alias")
@Access(value = AccessType.PROPERTY)
public class Alias {

    private long id;
    private StringProperty name;
    private StringProperty description;
    private IntegerProperty equity;


    public Alias() {
        // Empty constructor for hibernate
    }

    public Alias(String name, String description, int equity) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.equity = new SimpleIntegerProperty(equity);
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

    @Column(name = "equity")
    public int getEquity() {
        return equity.get();
    }

    public IntegerProperty equityProperty() {
        if (equity == null) equity = new SimpleIntegerProperty(this, "equity");
        return equity;
    }

    public void setEquity(int equity) {
        this.equity.set(equity);
    }

}
