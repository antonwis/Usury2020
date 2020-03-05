package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;


public class MenubarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu aliasMenu;

    private ObservableList<Alias> aliasList;
    private LoginManager loginManager;
    private AliasController aliasController;
    private AliasDataModel aliasDataModel;


    public void init(ObservableList<Alias> aliasList, LoginManager loginManager, AliasController aliasController, AliasDataModel aliasDataModel){
        this.loginManager = loginManager;
        this.aliasDataModel = aliasDataModel;
        this.aliasController = aliasController;
        int i = 0;

        for(Alias alias : aliasList) {

            MenuItem menuItem = new MenuItem("Item");
            menuItem.setText(aliasList.get(i).getName());
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    aliasDataModel.setCurrentAlias(alias);
                    System.out.println(aliasDataModel.getCurrentAlias().getName());
                }
            });
            aliasMenu.getItems().add(menuItem);
            i++;
        }
    }
    public void updateView(AliasDataModel aliasDataModel){

        this.aliasList = aliasDataModel.getAliasList();

        Alias alias = aliasList.get(aliasList.size()-1);

        MenuItem menuItem = new MenuItem("Item");
        menuItem.setText(alias.getName());
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aliasDataModel.setCurrentAlias(alias);
                System.out.println(aliasDataModel.getCurrentAlias().getName());
            }
        });
        aliasMenu.getItems().add(menuItem);
    }

    public void exitApp(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }

    public void logout(javafx.event.ActionEvent actionEvent) {
        loginManager.logout();
    }

    public void addNewAlias(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader alias = new FXMLLoader(getClass().getResource("newAlias.fxml"));
        Parent root = alias.load();
        aliasController = alias.getController();
        aliasController.display(this,aliasDataModel,stage);
        stage.setScene(new Scene(root));
        stage.show();
    }


}
