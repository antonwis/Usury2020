package fi.metropolia.group8.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alias {

    private StringProperty name;
    public void setName(String value) { nameProperty().set(value); }
    public String getName() { return nameProperty().get(); }
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this,"name") ;
        return name;
    }

    private StringProperty description;
    public void setDescription(String value) { descriptionProperty().set(value); }
    public String getDescription() { return descriptionProperty().get(); }
    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this,"description") ;
        return description;
    }

    private IntegerProperty equity;
    public void setEquity(int value) { equityProperty().set(value); }
    public int getEquity() { return equityProperty().get(); }
    public IntegerProperty equityProperty() {
        if(equity == null) equity = new SimpleIntegerProperty(this, "equity");
        return equity;
    }

    public Alias(String name, String description, int equity) {
        setName(name);
        setDescription(description);
        setEquity(equity);
    }
}
