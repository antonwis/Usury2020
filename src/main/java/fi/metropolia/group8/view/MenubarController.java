package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.MouseEvent;
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


    public void init(ObservableList<Alias> aliasList){

        int i = 0;

        for(Alias alias : aliasList) {
            MenuItem menuItem = new MenuItem("Item");
            menuItem.setText(aliasList.get(i).getName());
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(alias.getName());
                }
            });
            aliasMenu.getItems().add(menuItem);
            i++;
        }
    }
    public void newAliasMenuItem(Alias alias){
        MenuItem menuItem = new MenuItem("Item");
        menuItem.setText(alias.getName());
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(alias.getName());
            }
        });
        aliasMenu.getItems().add(menuItem);
    }

    public void exitApp(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }
    @FXML
    void createNewAlias(ActionEvent e) throws IOException {
        AliasController.display();
    }


}
