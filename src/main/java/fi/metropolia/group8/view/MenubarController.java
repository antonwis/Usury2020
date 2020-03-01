package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.AliasDataModel;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

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


    public void init(ObservableList<Alias> aliasList){
        System.out.println(aliasList);
        int i = 0;

        for(Alias alias : aliasList) {
            System.out.println(aliasList.get(0).getName());
            MenuItem menuItem = new MenuItem("Item");
            menuItem.setText(aliasList.get(i).getName());
            System.out.println(menuItem);
            System.out.println(aliasMenu);
            aliasMenu.getItems().add(menuItem);
            i++;
        }


    }

    public void exitApp(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }
    @FXML
    void createNewAlias(ActionEvent e) throws IOException {
        AliasController.display();
    }


}
