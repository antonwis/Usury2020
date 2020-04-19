package fi.metropolia.group8.view.Menu.Settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LanguageController {
    @FXML
    private ChoiceBox<String> languageSelector;
    private ResourceBundle bundle;
    private ObservableList<String> languages = FXCollections.observableArrayList();
    private Locale curLocale;

    public void init() {
        String language1 = "Suomi";
        String language2 = "English";
        languages.addAll(language1, language2);
        languageSelector.setItems(languages);

    }

    public void setCurrentLanguage() {
        String language1 = languageSelector.getValue();
        Properties properties = new Properties();
        switch (language1) {
            case "Suomi":
                System.out.println("Suomi");

                try {
                    properties.load(new FileInputStream("src\\main\\resources\\TextResources_fi_FI.properties"));
                    String language = properties.getProperty("language");
                    String country = properties.getProperty("country");

                    System.out.println(language);
                    curLocale = new Locale(language, country);
                    Locale.setDefault(curLocale);
                    bundle = ResourceBundle.getBundle("TextResources", curLocale);

                    UIManager.getDefaults().setDefaultLocale(curLocale);
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
                    curLocale = new Locale(language, country);
                    Locale.setDefault(curLocale);
                    bundle = ResourceBundle.getBundle("TextResources", curLocale);
                    UIManager.getDefaults().setDefaultLocale(curLocale);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }

        }
    public String getTranslation(String k){
        return (String) UIManager.getDefaults().get(k, Locale.getDefault());
    }
}
