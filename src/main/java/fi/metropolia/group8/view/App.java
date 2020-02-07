package fi.metropolia.group8.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(loadFXML("primary"));
        primaryStage.setTitle("Khazar Simulator (Early Access)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // public ettei tarvii copypaste. Väliaikanen solution
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}