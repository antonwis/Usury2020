package fi.metropolia.group8.view;

import fi.metropolia.group8.model.User;
import fi.metropolia.group8.model.UsuryDAO;
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

    private static Stage newUserScreen;

    public void initialize() {}

    @FXML
    void createNewUser(ActionEvent e) throws IOException {
        UsuryDAO usuryDAO = new UsuryDAO();
        User newUser = new User(name.getText());
        System.out.println("New User: " + newUser.getName());
        usuryDAO.createUser(newUser);
    }

    @FXML
    void returnToLogin(ActionEvent e) throws IOException {
        newUserScreen.close();
    }

    public static void display() throws IOException {
        newUserScreen = new Stage();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane anchor = FXMLLoader.load(AliasController.class.getResource("newUser.fxml"));
        NewUserController newUserController = loader.getController();

        Scene scene = new Scene(anchor, 300, 500);
        newUserScreen.setScene(scene);
        newUserScreen.show();
    }
}