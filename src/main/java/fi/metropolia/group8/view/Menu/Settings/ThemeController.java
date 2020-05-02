package fi.metropolia.group8.view.Menu.Settings;

import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


public class ThemeController {

    @FXML
    private ChoiceBox<String> themeSelector;
    private ObservableList<String> themes = FXCollections.observableArrayList();

    /**
     * Initializes the choisebox and adds an listener to check selection
     */
    public void init() {
        themes.addAll("Default", "Dark");
        themeSelector.setItems(themes);
        themeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setCurrentTheme(newValue));
    }

    /**
     * Changes the theme based on the value
     * @param s value of the selection
     */
    public void setCurrentTheme(String s) {
        switch (s){
            case "Default":
                // silly "hack" but it works.
                StyleManager.getInstance().removeUserAgentStylesheet("/fi/metropolia/group8/css/Dark.css");
                StyleManager.getInstance().addUserAgentStylesheet("/fi/metropolia/group8/css/Default.css");
                break;
            case "Dark":
                // overrides the default style.
                StyleManager.getInstance().removeUserAgentStylesheet("/fi/metropolia/group8/css/Default.css");
                StyleManager.getInstance().addUserAgentStylesheet("/fi/metropolia/group8/css/Dark.css");
                break;
            default:
                break;
        }
    }
}
