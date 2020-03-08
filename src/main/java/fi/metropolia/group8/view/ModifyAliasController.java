package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ModifyAliasController {
    private AliasController aliasController;
    private Stage stage;
    private MenubarController menubarController;
    private PrimaryController primaryController;
    private Alias alias;

    public void init(AliasController aliasController, Stage stage, MenubarController menubarController, PrimaryController primaryController, Alias alias){
        this.aliasController = aliasController;
        this.stage = stage;
        this.menubarController = menubarController;
        this.primaryController = primaryController;
        this.alias = alias;


    }

    public void closeAliasWindow(ActionEvent actionEvent) {
        System.out.println(stage);
        stage.close();
        System.out.println("fuck");
    }

    public void modifyAlias(ActionEvent actionEvent) {
    }

}
