package fi.metropolia.group8.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenubarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem exitMenuItem;



    public void exitApp(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }
}
