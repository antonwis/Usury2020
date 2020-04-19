package fi.metropolia.group8.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ThemeController {

    @FXML
    private ChoiceBox<String> themeSelector;
    private ObservableList<String> themes = FXCollections.observableArrayList();

    public void init() {
        themes.addAll("Default", "Dark");
        themeSelector.setItems(themes);
        themeSelector.setValue(themes.get(0));
        themeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setCurrentTheme(newValue));
    }

    public void setCurrentTheme(String s) {
        switch (s){
            case "Default":
                System.out.println("Default");
                break;
            case "Dark":
                System.out.println("Dark");
                break;
            default:
                System.out.println("Kys");
                break;
        }
    }
}
