package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;






public class ModifyAliasesController {

    @FXML
    private VBox aliasBox;

    private AliasController aliasController;
    private AliasDataModel aliasDataModel;
    private Stage stage;
    private MenubarController menubarController;

    public void init(AliasController aliasController, AliasDataModel aliasDataModel, Stage stage, MenubarController menubarController){
        this.aliasController = aliasController;
        this.aliasDataModel = aliasDataModel;
        this.stage = stage;
        this.menubarController = menubarController;

        for(Alias alias : aliasDataModel.getAliasList()){

            HBox hBox1 = new HBox();
            hBox1.setSpacing(10);
            Label label = new Label(alias.getName());
            label.setPrefWidth(300);
            label.setMinWidth(50);
            Button modify = new Button("Modify");
            modify.setMinWidth(60);
            Button delete = new Button("Delete");
            delete.setMinWidth(60);
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    aliasDataModel.deleteAlias(alias);
                    aliasDataModel.loadData();
                    menubarController.updateView(aliasDataModel);
                }
            });
            hBox1.getChildren().addAll(label,modify,delete);



            aliasBox.getChildren().add(hBox1);
        }

    }
}
