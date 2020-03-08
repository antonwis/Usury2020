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
        UsuryDAO usuryDAO = new UsuryDAO();
        aliasList.setAll(usuryDAO.readAliases());
    }

    public void saveData(Alias alias) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateAlias(alias);
    }

    public void addNewAlias(User user, String name, String description, int equity){
        Alias alias = new Alias(user, name, description, equity);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createAlias(alias);
        aliasList.addAll(alias);
    }
    public void deleteAlias(Alias alias){
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.deleteAlias(alias);
    }
}

