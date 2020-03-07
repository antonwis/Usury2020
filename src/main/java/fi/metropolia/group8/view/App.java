package fi.metropolia.group8.view;

import fi.metropolia.group8.model.LoanDataModel;
import fi.metropolia.group8.model.UserDataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Khazar Simulator (Early Access)");

        AnchorPane root = FXMLLoader.load(getClass().getResource("primary.fxml"));

        Scene scene = new Scene(root, 1200, 800);

        UserDataModel userDataModel = new UserDataModel();

        LoginManager loginManager = new LoginManager(scene, userDataModel);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}