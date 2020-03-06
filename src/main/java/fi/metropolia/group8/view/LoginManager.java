package fi.metropolia.group8.view;

import java.io.IOException;
import java.util.logging.*;

import fi.metropolia.group8.model.User;
import fi.metropolia.group8.model.UserDataModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/** Manages control flow for logins */
public class LoginManager {

    private Scene scene;
    private UserDataModel userDataModel;
    private User currentUser;

    public LoginManager(Scene scene, UserDataModel userDataModel) {
        this.scene = scene;
        this.userDataModel = userDataModel;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticated(String sessionID, User currentUser) {
        showMainView(sessionID, currentUser);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            scene.setRoot(loader.load());
            LoginController controller =
                    loader.getController();
            controller.initManager(this, userDataModel);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView(String sessionID, User currentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("primary.fxml")
            );
            scene.setRoot(loader.load());

            PrimaryController primaryController =
                    loader.getController();
            primaryController.init(this, currentUser, sessionID);

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showNewUserScreen() {

    }

}
