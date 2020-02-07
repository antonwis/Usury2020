package fi.metropolia.group8.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NewAlias {

    private static Stage aliasWindow;

    @FXML
    private Button AddAlias;
    @FXML
    private Button CancelAlias;

    @FXML
    void addNewAlias(ActionEvent e) throws IOException {
        // tee jotai memes
        aliasWindow.close();
    }

    @FXML
    void closeAliasWindow(ActionEvent e) throws IOException {
        aliasWindow.close();
    }

    public static void display() throws IOException {
        Scene scene = new Scene(App.loadFXML("newAlias"));
        aliasWindow = new Stage();
        aliasWindow.initModality(Modality.APPLICATION_MODAL);
        aliasWindow.setTitle("Add new alias");
        aliasWindow.setScene(scene);
        aliasWindow.show();
    }

}