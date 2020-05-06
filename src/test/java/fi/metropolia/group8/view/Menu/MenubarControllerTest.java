package fi.metropolia.group8.view.Menu;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.User;
import fi.metropolia.group8.view.Login.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;

import java.util.Locale;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class MenubarControllerTest {

    private static User user;

    /**
     * Generates new window for testing purposes
     *
     * @param stage stage
     * @throws Exception exception
     */
    @Start
    private void start(Stage stage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        Parent root = FXMLLoader.load(getClass().getResource("/fi/metropolia/group8/view/Primary.fxml"), resourceBundle);
        Scene scene = new Scene(root, 1400, 800);
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();
    }

    void login(FxRobot robot) {
        robot.clickOn("#newUserButton");
        robot.clickOn("#name");
        robot.write("Pepega");
        robot.clickOn("#createUser");
        user = DataModel.getInstance().getUserList().get(0);
        robot.clickOn("#userList");
        robot.clickOn("Pepega");
        robot.clickOn("#loginButton");
    }

    @Test
    void openSettings(FxRobot robot) {
        login(robot);
        robot.clickOn("#systemMenu");
        robot.clickOn("#settings");
        Window w = robot.window(1);
        FxAssert.verifyThat(w, WindowMatchers.isShowing());
        robot.closeCurrentWindow();
    }

    @AfterAll
    static void purge() {
        DataModel.getInstance().deleteUser(user);
    }
}