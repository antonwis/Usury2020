package fi.metropolia.group8.view.Login;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.view.Menu.Settings.LanguageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

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
    private LanguageController languageController;

    /**
     * gets user name and creates new user
     */
    @FXML
    void createNewUser() {
        if (name.getText().length() < 2) {
            userError.setVisible(true);
        }
        else {
            userError.setVisible(false);
            DataModel.getInstance().createUser(name.getText());
            System.out.printf("%s: %s\n",languageController.getTranslation("new_user"),name.getText());
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
    public void init(Stage stage, LoginController loginController) {
        this.stage = stage;
        this.loginController = loginController;
        languageController = new LanguageController();
        Locale.getDefault();
    }

}