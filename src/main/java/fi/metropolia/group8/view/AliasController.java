package fi.metropolia.group8.view;

import antlr.PreservingFileWriter;
import fi.metropolia.group8.model.*;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.function.Predicate;

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

    private MenubarController menubarController;
    private Stage stage;
    private PrimaryController primaryController;
    private OverviewController overviewController;

    @FXML
    void addNewAlias(ActionEvent e) {

        try {
            String name = nameField.getText();
            Integer equity = Integer.parseInt(equityField.getText());
            String description = descriptionArea.getText();
            Boolean alreadyExists = false;
            FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());

            Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
            aliasList.setPredicate(aliasFilter);
            for(Alias alias: aliasList){
                System.out.println(alias.getName()+""+name);
                if(alias.getName().equals(name)){
                    alreadyExists = true;
                }
            }

            if(nameField.getText().isBlank()) {
                nameField.setPromptText("You must choose a name");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));

            }
            if(alreadyExists == true){
                nameField.setText("");
                nameField.setPromptText("Alias already exists");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
            }
            else{
                DataModel.getInstance().addNewAlias(DataModel.getInstance().getCurrentUser(), name, description, equity);
                DataModel.getInstance().loadAliasData();
                ObservableList<Alias> list = DataModel.getInstance().getAliasList();
                DataModel.getInstance().setCurrentAlias(list.get(list.size()-1));
                primaryController.setCurrentAliasText();
                menubarController.updateView();
                overviewController.updateOverview();
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

    public void display(MenubarController menubarController, Stage stage,PrimaryController primaryController, OverviewController overviewController) throws IOException {
        if (this.menubarController == null) {
            this.menubarController = menubarController;
            this.stage = stage;
            this.primaryController = primaryController;
            this.overviewController = overviewController;
        }
    }


}