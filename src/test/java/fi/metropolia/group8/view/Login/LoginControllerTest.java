package fi.metropolia.group8.view.Login;

import fi.metropolia.group8.model.DataModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.ComboBoxMatchers;

import java.util.Locale;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    /**
     * Generates new window for testing purposes
     *
     * @param stage stage
     * @throws Exception exception
     */
    @Start
    private void start(Stage stage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/fi/metropolia/group8/view/Primary.fxml"),resourceBundle);
        Scene scene = new Scene(root,1400,800);
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Resets the robot before each test (not sure if needed)
     * @param robot robot
     */
    @BeforeEach
    void reset(FxRobot robot) {
        robot.release(new KeyCode[]{});
        robot.release(new MouseButton[]{});
    }

    /**
     * Tests if the create user button opens a new stage. also checks if the new stage can be closed
     * @param robot robot
     */
    @Test
    void windowOpensAndClose(FxRobot robot) {
        robot.clickOn("#newUserButton");
        FxAssert.verifyThat(robot.targetWindow(), WindowMatchers.isShowing());
        Window w = robot.targetWindow(1).targetWindow();
        robot.clickOn("#cancelButton");
        FxAssert.verifyThat(w, WindowMatchers.isNotShowing());
    }

    /**
     * Test the creation of a new user
     * @param robot robot
     */
    @Test
    void newUsername(FxRobot robot){
        robot.clickOn("#newUserButton");
        robot.clickOn("#name");
        robot.write("Pepega");
        robot.clickOn("#createUser");
        robot.clickOn("#userList");
        robot.clickOn("Pepega");
        //FxAssert.verifyThat("#userList", ComboBoxMatchers.hasSelectedItem("Pepega1"));
        FxAssert.verifyThat("#userList",ComboBoxMatchers.hasItems(1));
    }

    /**
     * Login test without selecting any user from the combobox
     */
    @Test
    void loginNoSelection(FxRobot robot){
        robot.clickOn("#loginButton");
        FxAssert.verifyThat("#loginError", NodeMatchers.isVisible());
    }
    /**
     * Login and Logout test
     */
    @Test
    void loginAndLogout(FxRobot robot){
        robot.clickOn("#userList");
        robot.clickOn("Pepega");
        robot.clickOn("#loginButton");
        //FxAssert.verifyThat("#user",LabeledMatchers.hasText("Pepega")); // null
        Assertions.assertEquals("Pepega",DataModel.getInstance().getCurrentUser().toString());
        robot.clickOn("#systemMenu");
        robot.clickOn("#logoutButton");
        Assertions.assertNull(DataModel.getInstance().getCurrentUser());
    }
    @AfterAll
    static void purge(){
        DataModel.getInstance().deleteUser(DataModel.getInstance().getUserList().get(0));
    }
}