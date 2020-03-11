package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    @FXML private Button loginButton;

    private LoginManager loginManager;

    public void initialize() {}

    public void initManager(LoginManager loginManager) {
        if(this.loginManager == null) {
            this.loginManager = loginManager;
        }
        updateView();

        userList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> DataModel.getInstance().setCurrentUser(newSelection));
        DataModel.getInstance().currentUserProperty().addListener((obs, oldLoan, newUser) -> {
            if (newUser == null) {
                userList.getSelectionModel().clearSelection();
            } else {
                userList.getSelectionModel().select(newUser);
            }
        });

        loginButton.setOnAction(event -> {
            String sessionID = authorize();
            if (sessionID != null && !userList.getSelectionModel().isEmpty()) {
                DataModel.getInstance().setCurrentUser(userList.getSelectionModel().selectedItemProperty().getValue());
                loginManager.authenticated(sessionID);
                System.out.println("Logging in as: " + DataModel.getInstance().getCurrentUser().getName());
            }
        });

    }

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


    @FXML
    void createNewUser() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);


        FXMLLoader newUser = new FXMLLoader(getClass().getResource("newUser.fxml"));
        Parent root = newUser.load();
        NewUserController newUserController = newUser.getController();
        newUserController.TransferMemes(stage, this);
        stage.setScene(new Scene(root));
        stage.show();
    }

/*    public void updateTextField(String newUserName) {
        user.setText(newUserName);
        password.setText("");
    }*/

}