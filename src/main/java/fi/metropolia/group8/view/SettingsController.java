package fi.metropolia.group8.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SettingsController {

    @FXML
    private ChoiceBox languageSelector;
    private ObservableList<String> languages = FXCollections.observableArrayList();

    public void init(){
        String language1 = "Suomi";
        String language2 = "English";
        languages.addAll(language1, language2);
        languageSelector.setItems(languages);
    }
}
