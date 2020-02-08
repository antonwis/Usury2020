package fi.metropolia.group8.view;

import fi.metropolia.group8.model.AliasDataModel;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.w3c.dom.Text;

import java.io.IOException;

public class NewAlias {

    private static Stage aliasWindow;

    @FXML
    private Button AddAlias;
    @FXML
    private Button CancelAlias;

    @FXML
    private TextField nameField;

    @FXML
    private TextField equityField;

    @FXML
    private TextField descriptionField;

    private AliasDataModel aliasDataModel;

    public void initModel(AliasDataModel aliasDataModel) {
        if (this.aliasDataModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.aliasDataModel = aliasDataModel;
        aliasDataModel.currentAliasProperty().addListener((obs, oldAlias, newAlias) -> {
            if(oldAlias != null) {
                nameField.textProperty().unbindBidirectional(oldAlias.nameProperty());
                equityField.textProperty().unbindBidirectional(oldAlias.equityProperty());
                descriptionField.textProperty().unbindBidirectional(oldAlias.descriptionProperty());
            }
            if(newAlias == null) {
                nameField.setText("");
                equityField.setText("");
                descriptionField.setText("");
            } else {
                nameField.textProperty().bindBidirectional(newAlias.nameProperty());
                equityField.textProperty().bindBidirectional(newAlias.equityProperty(), new NumberStringConverter());
                descriptionField.textProperty().bindBidirectional(newAlias.descriptionProperty());
            }
        });
    }


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