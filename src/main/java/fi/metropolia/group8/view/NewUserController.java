package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import javafx.event.ActionEvent;
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
    private TextField name;

    @FXML
    private Label userError;

    private LoginController loginController;
    private Stage stage;

    public void initialize() {
    }

    @FXML
    void createNewUser() {
        if (name.getText().isEmpty()) {
            userError.setText("pepega clap");
        }
        else {
            DataModel.getInstance().addNewUser(name.getText());
            System.out.println("New User: " + name.getText());
            //loginController.updateTextField(name.getText());
            stage.close();
        }
    }

    @FXML
    void returnToLogin(ActionEvent e) throws IOException {
        stage.close();
    }

    public void TransferMemes(Stage stage, LoginController loginController) {
        this.stage = stage;
        this.loginController = loginController;
    }

}