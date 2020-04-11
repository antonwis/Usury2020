package fi.metropolia.group8.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class SettingsController {


    @FXML
    private ChoiceBox languageSelector;
    private ResourceBundle bundle;
    private ObservableList<String> languages = FXCollections.observableArrayList();

    public void init(){
        String language1 = "Suomi";
        String language2 = "English";
        languages.addAll(language1, language2);
        languageSelector.setItems(languages);

    }
    public void setCurrentLanguage(){
        String language1 = (String) languageSelector.getValue();
        Properties properties = new Properties();
        switch (language1){
            case "Suomi":
                System.out.println("Suomi");

                try {
                    properties.load(new FileInputStream("src\\main\\resources\\TextResources_fi_FI.properties"));
                    String language = properties.getProperty("language");
                    String country = properties.getProperty("country");

                    System.out.println(language);
                    Locale curLocale = new Locale(language,country);
                    Locale.setDefault(curLocale);
                    bundle = ResourceBundle.getBundle("TextResources", curLocale);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "English":
                System.out.println("English");

                try {
                    properties.load(new FileInputStream("src\\main\\resources\\TextResources.properties"));
                    String language = properties.getProperty("language");
                    String country = properties.getProperty("country");

                    System.out.println(language);
                    Locale curLocale = new Locale(language,country);
                    Locale.setDefault(curLocale);
                    bundle = ResourceBundle.getBundle("TextResources", curLocale);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}