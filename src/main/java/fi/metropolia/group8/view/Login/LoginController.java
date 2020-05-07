package fi.metropolia.group8.view.Login;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.User;
import fi.metropolia.group8.view.Menu.Settings.LanguageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Controls the login screen */
public class LoginController {
    @FXML public Button newUserButton;
    @FXML private ComboBox<User> userList;
    @FXML private Button loginButton;
    @FXML private Label loginError;

    private LoginManager loginManager;

    /**
     * initializes loginManager
     * @param loginManager Login manager
     */
    public void initManager(LoginManager loginManager) {
        if(this.loginManager == null) {
            this.loginManager = loginManager;
        }
        updateView();

        userList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> DataModel.getInstance().setCurrentUser(newSelection));
        DataModel.getInstance().currentUserProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser == null) {
                userList.getSelectionModel().clearSelection();
            } else {
                userList.getSelectionModel().select(newUser);
            }
        });

        loginButton.setOnAction(event -> {
            String sessionID = authorize();
            if (!userList.getSelectionModel().isEmpty()) {
                loginError.setVisible(false);
                DataModel.getInstance().setCurrentUser(userList.getSelectionModel().selectedItemProperty().getValue());
                loginManager.authenticated(sessionID);
                System.out.println("Logging in as: " + DataModel.getInstance().getCurrentUser().getName());
            } else {
                loginError.setVisible(true);
            }
        });

    }

    /**
     * updates userList
     */
    public void updateView() {
        DataModel.getInstance().loadUserData();
        userList.setItems(DataModel.getInstance().getUserList());
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */
    private String authorize() {
        //return "password".equals(password.getText()) ? generateSessionID() : null;
        return generateSessionID();
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "session id: " + sessionID;
    }

    /**
     * opens create new user view
     * @throws IOException Exception
     */
    @FXML
    void createNewUser() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);

        FXMLLoader newUser = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Login/NewUser.fxml"));
        newUser.setResources(resourceBundle);
        Parent root = newUser.load();
        NewUserController newUserController = newUser.getController();
        newUserController.init(stage, this);
        stage.setScene(new Scene(root));
        stage.show();
    }

/*    public void updateTextField(String newUserName) {
        user.setText(newUserName);
        password.setText("");
    }*/

}