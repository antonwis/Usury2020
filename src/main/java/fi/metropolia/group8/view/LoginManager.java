package fi.metropolia.group8.view;

import java.io.IOException;
import java.util.logging.*;

import fi.metropolia.group8.model.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/** Manages control flow for logins */
public class LoginManager {

    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticated(String sessionID) {
        showMainView(sessionID);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        DataModel.getInstance().setCurrentUser(null);
        DataModel.getInstance().setCurrentAlias(null);
        DataModel.getInstance().setCurrentLoan(null);
        System.out.println("Logout: All current properties set to null");

        showLoginScreen();
    }

    /**
     * opens loginScreen view
     */
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            scene.setRoot(loader.load());
            LoginController controller =
                    loader.getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * opens MainView
     * @param sessionID
     */
    private void showMainView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("primary.fxml")
            );
            scene.setRoot(loader.load());

            PrimaryController primaryController =
                    loader.getController();
            primaryController.init(this, sessionID);

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
