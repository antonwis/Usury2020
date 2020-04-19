package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.hibernate.service.spi.ServiceException;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages control flow for logins
 */
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

            // test
            Locale locale = new Locale("fi", "FI");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources_fi_FI", locale);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            loader.setResources(resourceBundle); ////////////////////// test
            scene.setRoot(loader.load());
            LoginController controller = loader.getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database login error. " + ex.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    /**
     * opens MainView
     *
     * @param sessionID
     */
    private void showMainView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            scene.setRoot(loader.load());
            PrimaryController primaryController = loader.getController();
            primaryController.init(this, sessionID);

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
