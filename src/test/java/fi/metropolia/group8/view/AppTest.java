package fi.metropolia.group8.view;

import fi.metropolia.group8.view.Login.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class AppTest {

    /**
     * Generates new window for testing purposes
     *
     * @param stage stage
     * @throws Exception exception
     */
    @Start
    private void start(Stage stage) throws Exception {
        UIManager.getDefaults().addResourceBundle("TextResources");
        stage.setTitle("Khazar Simulator (Early Access)");
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Primary.fxml"));
        loader.setResources(resourceBundle);
        Scene scene = new Scene(loader.load(),1400,800);
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();
    }

/*    @Test
    void test(FxRobot robot){
        robot.sleep(1000);
    }*/
}