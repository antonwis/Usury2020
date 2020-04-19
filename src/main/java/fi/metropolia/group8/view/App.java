package fi.metropolia.group8.view;

import fi.metropolia.group8.view.Login.LoginManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Khazar Simulator (Early Access)");

        AnchorPane root = FXMLLoader.load(getClass().getResource("/fi/metropolia/group8/view/Primary.fxml"));

        Scene scene = new Scene(root, 1400, 800);

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}