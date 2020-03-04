package fi.metropolia.group8.model;

import fi.metropolia.group8.view.AliasController;
import fi.metropolia.group8.view.MenubarController;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

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

    public void loadData() {
        // tänne DAOsetit tai luku tiedostosta tms.
        UsuryDAO usuryDAO = new UsuryDAO();
        List<Alias> list =  usuryDAO.readAliases();

        // Dummy aliases for testing purposes
        aliasList.setAll(
                list
        );


    }

    public void saveData(File file) {
        // kys
    }
    public void addNewAlias(String name, String description, int equity){
        Alias alias = new Alias(name,description,equity);
        loadTestData();
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createAlias(alias);

    }

    public void loadTestData() {
        UsuryDAO usuryDAO = new UsuryDAO();

        aliasList.setAll(

        );
    }

}

