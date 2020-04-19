package fi.metropolia.group8.view.Login;

import fi.metropolia.group8.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls the new user creation screen
 */
public class NewUserController {
    @FXML
    public Button createUser;
    @FXML
    public Button cancelButton;
    @FXML
    private TextField name;

    @FXML
    private Label userError;

    private LoginController loginController;
    private Stage stage;

    public void initialize() {
    }

    /**
     * gets user name and creates new user
     */
    @FXML
    void createNewUser() {
        if (name.getText().isEmpty()) {
            userError.setText("pepega clap");
        }
        else {
            DataModel.getInstance().createUser(name.getText());
            System.out.println("New User: " + name.getText());
            //loginController.updateTextField(name.getText());
            stage.close();
        }
    }

    /**
     * closes new user view
     */
    @FXML
    void returnToLogin() {
        stage.close();
    }

    /**
     * gets login controller and instance of stage
     * @param stage Stage
     * @param loginController LoginController
     */
    public void TransferMemes(Stage stage, LoginController loginController) {
        this.stage = stage;
        this.loginController = loginController;
    }

}