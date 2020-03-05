package fi.metropolia.group8.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

/** Controls the login screen */
public class LoginController {

    /*
    * TODO replace with a drop down selection which pulls every user from database
    */

    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;

    private LoginManager loginManager;

    public void initialize() {}

    public void initManager(final LoginManager loginManager) {
        this.loginManager = loginManager;
        loginButton.setOnAction(event -> {
            String sessionID = authorize();
            if (sessionID != null) {
                loginManager.authenticated(sessionID);
            }
        });
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */
    private String authorize() {
        return
                "open".equals(user.getText()) && "sesame".equals(password.getText())
                        ? generateSessionID()
                        : null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "session id: " + sessionID;
    }

    public void createNewUser() throws IOException {
        NewUserController.display();
    }

    public void updateTextField(String newUserName) {
        user.setText(newUserName);
        password.setText("");
    }

}