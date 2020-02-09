package fi.metropolia.group8.view;

import fi.metropolia.group8.model.LoanDataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        FXMLLoader primary = new FXMLLoader();
        AnchorPane root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        PrimaryController primaryController = primary.getController();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}