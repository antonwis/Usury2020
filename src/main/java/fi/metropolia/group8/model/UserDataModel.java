package fi.metropolia.group8.model;

import fi.metropolia.group8.view.MenubarController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserDataModel {

    private final ObservableList<User> userList = FXCollections.observableArrayList();

    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public final User getCurrentUser() {
        return currentUserProperty().get();
    }

    public final void setCurrentUser(User user) {
        currentUserProperty().set(user);
    }

    public ObservableList<User> getUserList() {
        return userList;
    }

    public void loadData() {
        UsuryDAO usuryDAO = new UsuryDAO();
        userList.setAll(usuryDAO.readUsers());
    }

    public void saveData(User user) {
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.updateUser(user);
    }

    public void addNewUser(String name){
        User user = new User(name);
        UsuryDAO usuryDAO = new UsuryDAO();
        usuryDAO.createUser(user);
        userList.addAll(user);
    }

}
