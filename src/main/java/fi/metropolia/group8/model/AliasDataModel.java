package fi.metropolia.group8.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class AliasDataModel {

    //alias -> new Observable[] {alias.nameProperty(), alias.equityProperty()}
    private final ObservableList<Alias> aliasList = FXCollections.observableArrayList();

    private final ObjectProperty<Alias> currentAlias = new SimpleObjectProperty<>(null);

    public ObjectProperty<Alias> currentAliasProperty() {
        return currentAlias;
    }

    public final Alias getCurrentAlias() {
        return currentAliasProperty().get();
    }

    public final void setCurrentAlias(Alias alias) {
        currentAliasProperty().set(alias);
    }

    public ObservableList<Alias> getAliasList() {
        return aliasList;
    }

    public void loadData(File file) {
        // tänne DAOsetit tai luku tiedostosta tms.

        // Dummy aliases for testing purposes
        aliasList.setAll(
                new Alias("Ben Shapiro", "Professional jew", 2000),
                new Alias("Dr. Sheckelstein", "Usury M.D", 5000)
        );
    }

    public void saveData(File file) {
        // kys
    }

}
