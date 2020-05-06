package fi.metropolia.group8.view.Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.util.Locale;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class NewUserControllerTest {

    /**
     * Generates new window for testing purposes
     *
     * @param stage stage
     * @throws Exception exception
     */
    @Start
    private void start(Stage stage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/fi/metropolia/group8/view/Login/NewUser.fxml"),resourceBundle);
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    /**
     * Resets the robot before each test (not sure if needed)
     * @param robot robot
     */
    @BeforeEach
    void reset(FxRobot robot){
        robot.release(new KeyCode[]{});
        robot.release(new MouseButton[]{});
    }

    /**
     * Test whether an error message is displayed when a user tries to add a new user whose username is empty
     * @param robot robot
     */
    @Test
    void emptyFieldNotAllowed(FxRobot robot){
        robot.clickOn("#createUser");
        FxAssert.verifyThat("#userError", NodeMatchers.isVisible());
    }

    /**
     * Test whether an error message is displayed when a user tries to add a new user whose username is too short
     * @param robot robot
     */
    @Test
    void lessThan2(FxRobot robot){
        robot.clickOn("#name");
        robot.write("P");
        robot.clickOn("#createUser");
        FxAssert.verifyThat("#userError", NodeMatchers.isVisible());
    }
}