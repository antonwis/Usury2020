package fi.metropolia.group8.view;

import antlr.PreservingFileWriter;
import fi.metropolia.group8.model.AliasDataModel;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AliasController {

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
    private TextArea descriptionArea;

    private AliasDataModel aliasDataModel;
    private MenubarController menubarController;
    private Stage stage;

    public void initModel(AliasDataModel aliasDataModel) {
        if (this.aliasDataModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.aliasDataModel = aliasDataModel;
        aliasDataModel.currentAliasProperty().addListener((obs, oldAlias, newAlias) -> {

            /*
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
            */
        });
    }


    @FXML
    void addNewAlias(ActionEvent e) {

        try {
            String name = nameField.getText();
            Integer equity = Integer.parseInt(equityField.getText());
            String description = descriptionArea.getText();
            if(nameField.getText().isBlank()) {
                nameField.setPromptText("You must choose a name");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));

            }else{
                this.aliasDataModel.addNewAlias(name, description, equity);
                this.menubarController.updateView(this.aliasDataModel);
                stage.close();
            }

        }catch (NumberFormatException numE){
            equityField.setText("");
            equityField.setPromptText("Equity must be a number");
            equityField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
        }


    }

    @FXML
    void closeAliasWindow(ActionEvent e) throws IOException {
        stage.close();
    }

    public void display(MenubarController menubarController, AliasDataModel aliasDataModel, Stage stage) throws IOException {
        this.menubarController = menubarController;
        this.aliasDataModel = aliasDataModel;
        this.stage = stage;


    }

}