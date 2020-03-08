package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import fi.metropolia.group8.model.DataModel;
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
    private Stage stage;
    private MenubarController menubarController;
    private PrimaryController primaryController;

    public void init(AliasController aliasController, Stage stage, MenubarController menubarController, PrimaryController primaryController){
        this.aliasController = aliasController;
        this.stage = stage;
        this.menubarController = menubarController;
        this.primaryController = primaryController;

        for(Alias alias : DataModel.getInstance().getAliasList()){

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
                    if(DataModel.getInstance().getCurrentAlias() == alias){
                        DataModel.getInstance().setCurrentAlias(null);

                    }
                    DataModel.getInstance().deleteAlias(alias);
                    DataModel.getInstance().loadAliasData();
                    menubarController.updateView();
                    primaryController.setCurrentAliasText();
                    updateView();
                }
            });
            hBox1.getChildren().addAll(label,modify,delete);
            aliasBox.getChildren().add(hBox1);
        }
    }
    public void updateView() {
        DataModel.getInstance().loadAliasData();
        aliasBox.getChildren().clear();
        for (Alias alias : DataModel.getInstance().getAliasList()) {

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
                    if (DataModel.getInstance().getCurrentAlias() == alias) {
                        DataModel.getInstance().setCurrentAlias(null);

                    }
                    DataModel.getInstance().deleteAlias(alias);
                    DataModel.getInstance().loadAliasData();
                    menubarController.updateView();
                    primaryController.setCurrentAliasText();
                    updateView();
                }
            });
            hBox1.getChildren().addAll(label,modify,delete);
            aliasBox.getChildren().add(hBox1);
        }
    }
    public void closeWindow(){
        stage.close();
    }
}
