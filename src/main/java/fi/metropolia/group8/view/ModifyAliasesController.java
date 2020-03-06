package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.*;

public class ModifyAliasesController {

    @FXML
    private VBox aliasBox;

    private AliasController aliasController;
    private AliasDataModel aliasDataModel;
    private Stage stage;

    public void init(AliasController aliasController, AliasDataModel aliasDataModel, Stage stage){
        this.aliasController = aliasController;
        this.aliasDataModel = aliasDataModel;
        this.stage = stage;

        for(Alias alias : aliasDataModel.getAliasList()){
            HBox hBox = new HBox();
            Label label = new Label();
            label.setText(alias.getName());
            hBox.getChildren().add();
            Button modify = new Button();
            modify.setLabel("Modify");
            Button delete = new Button();
            delete.setLabel("Delete");



            aliasBox.getChildren().add(hBox);
        }

    }
}
