package fi.metropolia.group8.view.Menu;

import fi.metropolia.group8.model.*;
import fi.metropolia.group8.view.Login.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private UsuryDAO dao = new UsuryDAO();

    /**
     * Generates new window for testing purposes
     *
     * @param stage stage
     * @throws Exception exception
     */
    @Start
    private void start(Stage stage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/fi/metropolia/group8/view/Primary.fxml"), resourceBundle);
        Scene scene = new Scene(root, 1400, 800);
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    /**
     * Resets the robot before each test (not sure if needed)
     *
     * @param robot robot
     */
    @BeforeEach
    void reset(FxRobot robot) {
        robot.release(new KeyCode[]{});
        robot.release(new MouseButton[]{});
    }

    /**
     * Clears the database of leftover items
     */
    @AfterEach
    void tearDown() {
        for (Loan l : dao.readLoans()) { dao.deleteLoan(l); }
        for (Victim v : dao.readVictims()) { dao.deleteVictim(v); }
        for (Alias a : dao.readAliases()) { dao.deleteAlias(a); }
        for (User u : dao.readUsers()) { dao.deleteUser(u); }
    }

    /**
     * Login Helper method
     * @param robot robot
     */
    void login(FxRobot robot) {
        robot.clickOn("#newUserButton");
        robot.clickOn("#nameF");
        robot.write("Pepega");
        robot.clickOn("#createUser");
        user = DataModel.getInstance().getUserList().get(0);
        robot.clickOn("#userList");
        robot.clickOn("Pepega");
        robot.clickOn("#loginButton");
    }

    /**
     * Test for opening the settings window from the menu
     * @param robot robot
     */
/*    @Test
    void openSettingsWindow(FxRobot robot) {
        login(robot);
        robot.clickOn("#systemMenu");
        robot.clickOn("#settings");
        Window w = robot.window(1);
        FxAssert.verifyThat(w, WindowMatchers.isShowing());
    }*/

    /**
     * Test for creating a new alias from the menu
     * @param robot robot
     */
    @Test
    void createAlias(FxRobot robot) {
        login(robot);
        robot.clickOn("#aliasMenu");
        robot.clickOn("#newAlias");
        robot.clickOn("#nameField");
        robot.write("Pepe");
        robot.clickOn("#equityField");
        robot.write("1000");
        robot.clickOn("#descriptionArea");
        robot.clickOn("#AddAlias");
        Assertions.assertEquals(1, dao.readAliases().size());
        DataModel.getInstance().deleteAlias(dao.getAliasById(dao.readAliases().get(0).getId()));
    }

    /**
     * Quick test for opening the alias creation window from the menu
     * @param robot robot
     */
    @Test
    void addAliasWindow(FxRobot robot) {
        login(robot);
        robot.clickOn("#aliasMenu");
        robot.clickOn("#newAlias");
        Window w = robot.window(1);
        FxAssert.verifyThat(w, WindowMatchers.isShowing());
        robot.clickOn("#CancelAlias");
        FxAssert.verifyThat(w, WindowMatchers.isNotShowing());
    }
    /**
     * Quick test for opening the modify aliases window from the menu
     * @param robot robot
     */
    @Test
    void modifyAliasWindow(FxRobot robot) {
        login(robot);
        robot.clickOn("#aliasMenu");
        robot.clickOn("#modifyAlias");
        Window w = robot.window(1);
        FxAssert.verifyThat(w, WindowMatchers.isShowing());
        robot.clickOn("#CancelAlias");
        FxAssert.verifyThat(w, WindowMatchers.isNotShowing());
    }

    /**
     * Quick test for opening the book of debtors window from the menu
     * @param robot robot
     */
    @Test
    void bookOfDebtors(FxRobot robot) {
        login(robot);
        robot.clickOn("#viewMenu");
        robot.clickOn("#bookDebt");
        Window w = robot.window(1);
        FxAssert.verifyThat(w, WindowMatchers.isShowing());
    }
}