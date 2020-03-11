package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * Controller class for alias management window
 */
public class ManageAliasesController {

    @FXML
    private VBox aliasBox;

    private AliasController aliasController;
    private Stage stage;
    private MenubarController menubarController;
    private PrimaryController primaryController;
    private OverviewController overviewController;

    /**
     * Initialization method for Manage aliases window where all needed instances of controllers are initialized
     * @param aliasController
     * @param stage
     * @param menubarController
     * @param primaryController
     * @param overviewController
     */
    public void init(AliasController aliasController, Stage stage, MenubarController menubarController, PrimaryController primaryController, OverviewController overviewController){
        if(this.aliasController == null) {
            this.aliasController = aliasController;
            this.stage = stage;
            this.menubarController = menubarController;
            this.primaryController = primaryController;
            this.overviewController = overviewController;
        }
        updateView();
    }

    /**
     * Method that filters aliases from alias list based on user and creates all the needed javafx elements for managing aliases also set listeners for buttons delete and modify
     */
    public void updateView() {

        FilteredList<Alias> filteredList = new FilteredList<>(DataModel.getInstance().getAliasList());

        Predicate<Alias> aliasFilter = fil -> fil.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
        filteredList.setPredicate(aliasFilter);
        aliasBox.getChildren().clear();
        for (Alias alias : filteredList) {

            HBox hBox1 = new HBox();
            hBox1.setSpacing(10);
            Label label = new Label(alias.getName());
            label.setPrefWidth(300);
            label.setMinWidth(50);
            Button modify = new Button("Modify");
            modify.setMinWidth(60);

            modify.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * Handler for modify button that opens new window where you can edit alias
                 * @param actionEvent
                 */
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        DataModel.getInstance().setCurrentAlias(alias);
                        stage.close();
                        Stage stage = new Stage();
                        FXMLLoader modifyAlias = new FXMLLoader(getClass().getResource("modifyAlias.fxml"));
                        Parent root = modifyAlias.load();
                        ModifyAliasController modifyAliasController = modifyAlias.getController();
                        modifyAliasController.init(aliasController, stage,menubarController, primaryController, overviewController, alias);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }catch (IOException e){

                    }

                }
            });
            Button delete = new Button("Delete");
            delete.setMinWidth(60);
            delete.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * Handler for delete button that gets the alias and deletes it from the list and the database also if the alias is current alias it set current alias null
                 * @param actionEvent
                 */
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(DataModel.getInstance().getCurrentAlias() != null) {
                        if (DataModel.getInstance().getCurrentAlias().getName().equals(alias.getName())) {
                            DataModel.getInstance().setCurrentAlias(null);
                        }
                    }

                    DataModel.getInstance().deleteAlias(alias);
                    DataModel.getInstance().loadAliasData();
                    menubarController.updateView();
                    primaryController.setCurrentAliasText();
                    overviewController.updateOverview();
                    updateView();
                }
            });
            hBox1.getChildren().addAll(label,modify,delete);
            aliasBox.getChildren().add(hBox1);
        }
    }

    /**
     * Closes alias manage window
     */
    public void closeWindow(){
        stage.close();
    }
}