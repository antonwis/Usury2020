package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/** Controls the new user creation screen */
public class NewUserController {
    @FXML private TextField name;
    @FXML private Button createUser;
    @FXML private Button cancelButton;

    private LoginManager loginManager;
    private UserDataModel userDataModel;
    private Stage stage;

    public void initialize() {}

    @FXML
    void createNewUser(ActionEvent e) throws IOException {
        userDataModel.addNewUser(name.getText());
        System.out.println("New User: " + name.getText());
    }

    @FXML
    void returnToLogin(ActionEvent e) throws IOException {
        stage.close();
    }

    public void TransferMemes(UserDataModel userDataModel, Stage stage) {
        this.userDataModel = userDataModel;
        this.stage = stage;
    }

}