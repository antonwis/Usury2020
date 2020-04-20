package fi.metropolia.group8.view;

import fi.metropolia.group8.view.Login.LoginManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        UIManager.getDefaults().addResourceBundle("TextResources");
        primaryStage.setTitle("Khazar Simulator (Early Access)");

        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Primary.fxml"));
        loader.setResources(resourceBundle);


        Scene scene = new Scene(loader.load(),1400,800);

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}