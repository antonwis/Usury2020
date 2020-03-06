package fi.metropolia.group8.view;

import fi.metropolia.group8.model.User;
import fi.metropolia.group8.model.UserDataModel;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/** Controls the login screen */
public class LoginController {

    /*
    * TODO replace with a drop down selection which pulls every user from database
    */

    @FXML private ComboBox<User> userList;
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;

    private LoginManager loginManager;
    private UserDataModel userDataModel;

    public void initialize() {}

    public void initManager(final LoginManager loginManager, UserDataModel userDataModel) {
        this.loginManager = loginManager;
        this.userDataModel = userDataModel;
        updateView();

        userList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> userDataModel.setCurrentUser(newSelection));
        userDataModel.currentUserProperty().addListener((obs, oldLoan, newUser) -> {
            if (newUser == null) {
                userList.getSelectionModel().clearSelection();
            } else {
                userList.getSelectionModel().select(newUser);
            }
        });

        loginButton.setOnAction(event -> {
            String sessionID = authorize();
            if (sessionID != null) {
                userDataModel.setCurrentUser(userList.getSelectionModel().selectedItemProperty().getValue());
                loginManager.authenticated(sessionID, userDataModel.getCurrentUser());
                System.out.println("Logging in as: " + userDataModel.getCurrentUser().getName());
            }
        });

    }


    public void updateView() {
        userDataModel.loadData();
        userList.setItems(userDataModel.getUserList());
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */
    private String authorize() {
        return
                "password".equals(password.getText())
                        ? generateSessionID()
                        : null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "session id: " + sessionID;
    }


    @FXML
    void createNewUser() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        FXMLLoader newUser = new FXMLLoader(getClass().getResource("newUser.fxml"));
        Parent root = newUser.load();
        NewUserController newUserController = newUser.getController();
        newUserController.TransferMemes(this.userDataModel, stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateTextField(String newUserName) {
        user.setText(newUserName);
        password.setText("");
    }

}