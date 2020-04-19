package fi.metropolia.group8.view.Menu.Settings;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ThemeController {

    @FXML
    private ChoiceBox<String> themeSelector;
    private ObservableList<String> themes = FXCollections.observableArrayList();

    public void init() {
        themes.addAll("Default", "Better");
        themeSelector.setItems(themes);
        themeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setCurrentTheme(newValue));
    }

    public void setCurrentTheme(String s) {
        switch (s){
            case "Default":
                Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
                break;
            case "Better":
                Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
                break;
            default:
                System.out.println("Kys");
                break;
        }
    }
}
