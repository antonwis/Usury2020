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

    /**
     * Generates new Tree view and items for it.
     * Also adds an listener to the tree for checking what item is selected
     */
    public void init() {
        TreeItem<String> settingsRoot = new TreeItem<>("Settings");
        TreeItem<String> language = new TreeItem<>("Language");
        TreeItem<String> theme = new TreeItem<>("Theme");
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
     * @param value value of the selected item
     * @throws IOException Exception
     */
    private void changeView(String value) throws IOException {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
        switch (value){
            case "Language":
                System.out.println(value);

                FXMLLoader language = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Menu/Settings/Language.fxml"));
                language.setResources(resourceBundle);
                settingsVbox.getChildren().setAll((Node) language.load());
                LanguageController languageController = language.getController();
                languageController.init();
                break;
            case "Theme":
                System.out.println(value);

                FXMLLoader theme = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Menu/Settings/Theme.fxml"));
                theme.setResources(resourceBundle);
                settingsVbox.getChildren().setAll((Node) theme.load());
                ThemeController themeController = theme.getController();
                themeController.init();
                break;
            default:
                System.out.println("lol");
                break;
        }
    }

}
