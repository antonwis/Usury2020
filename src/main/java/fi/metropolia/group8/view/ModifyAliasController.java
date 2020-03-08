package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

public class ModifyAliasController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField equityField;

    @FXML
    private TextArea descriptionArea;

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

        nameField.setText(alias.getName());
        equityField.setText(Float.toString(alias.getEquity()));
        descriptionArea.setText(alias.getDescription());
    }

    public void closeAliasWindow(ActionEvent actionEvent) throws IOException {
        stage.close();
        Stage stage = new Stage();
        FXMLLoader modifyAlias = new FXMLLoader(getClass().getResource("modifyAliases.fxml"));
        Parent root = modifyAlias.load();
        ModifyAliasesController modifyAliasesController = modifyAlias.getController();
        modifyAliasesController.init(aliasController, stage, menubarController, primaryController);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void modifyAlias(ActionEvent actionEvent) {
        try {
            String name = nameField.getText();
            Float equity = Float.parseFloat(equityField.getText());
            String description = descriptionArea.getText();

            FilteredList<Alias> aliasList = new FilteredList<>(DataModel.getInstance().getAliasList());
            // ID:tä ei voinu verrata suoraan jostain syystä. Pitäs tehä oma DB kutsu koko paskalle mut tämäkin toimii.
            Predicate<Alias> aliasFilter = i -> i.getUser().getName().equals(DataModel.getInstance().getCurrentUser().getName());
            aliasList.setPredicate(aliasFilter);


            if(nameField.getText().isBlank()) {
                nameField.setPromptText("You must choose a name");
                nameField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));

            }

            else{
                if(DataModel.getInstance().getCurrentAlias().getName().equals(alias.getName())){
                    DataModel.getInstance().setCurrentAlias(null);
                    alias.setName(name);
                    alias.setEquity(equity);
                    alias.setDescription(description);
                    DataModel.getInstance().saveAliasData(alias);
                    DataModel.getInstance().loadAliasData();
                    DataModel.getInstance().setCurrentAlias(alias);
                    this.primaryController.setCurrentAliasText();
                    this.menubarController.updateView();
                    stage.close();
                }else{
                    alias.setName(name);
                    alias.setEquity(equity);
                    alias.setDescription(description);
                    DataModel.getInstance().saveAliasData(alias);
                    DataModel.getInstance().loadAliasData();

                    this.primaryController.setCurrentAliasText();
                    this.menubarController.updateView();
                    stage.close();
                }

            }

        }catch (NumberFormatException numE){
            equityField.setText("");
            equityField.setPromptText("Equity must be a number");
            equityField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(3))));
        }

    }

}
