package fi.metropolia.group8.view.Menu.Settings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class SettingsController {

    @FXML
    private TreeView<String> settingsTree;
    @FXML
    private VBox settingsVbox;
    private LanguageController languageController;

    /**
     * Generates new Tree view and items for it.
     * Also adds an listener to the tree for checking what item is selected
     */
    public void init() {
        languageController = new LanguageController();
        TreeItem<String> settingsRoot = new TreeItem<>(languageController.getTranslation("settings"));
        TreeItem<String> language = new TreeItem<>(languageController.getTranslation("languages"));
        TreeItem<String> theme = new TreeItem<>(languageController.getTranslation("theme"));
        settingsRoot.getChildren().addAll(language, theme);
        settingsTree.setRoot(settingsRoot);
        settingsRoot.setExpanded(true);

        settingsTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                changeView(newValue.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Changes the view on the left based on the user selection.
     *
     * @param value value of the selected item
     * @throws IOException Exception
     */
    private void changeView(String value) throws IOException {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
        //TODO kielen vaihtamisen jälkeen settings menu lakkaa toimimasta siihen asti kunnes sen avaa uudelleen.
        if (value.equals(languageController.getTranslation("languages"))) {
            FXMLLoader language = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Menu/Settings/Language.fxml"));
            language.setResources(resourceBundle);
            settingsVbox.getChildren().setAll((Node) language.load());
            LanguageController languageController = language.getController();
            languageController.init();
        } else if (value.equals(languageController.getTranslation("theme"))) {
            FXMLLoader theme = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Menu/Settings/Theme.fxml"));
            theme.setResources(resourceBundle);
            settingsVbox.getChildren().setAll((Node) theme.load());
            ThemeController themeController = theme.getController();
            themeController.init();
        }
    }
}
