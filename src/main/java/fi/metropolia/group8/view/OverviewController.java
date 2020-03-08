package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.util.Map;

public class OverviewController {

    @FXML
    private Label user;

    @FXML
    private ComboBox<Alias> filter;

    @FXML
    private Label balance;

    @FXML
    private Label profits;

    @FXML
    private Label loans;

    @FXML
    private Label loansComplete;

    @FXML
    private Label loansActive;

    @FXML
    private Label enforcerActions;

    @FXML
    private Label loansDue;

    @FXML
    private Label forecast;

    @FXML
    private BarChart<?, ?> profitChart;

    public void initModel(PrimaryController primaryController) {
        filter.setItems(DataModel.getInstance().getAliasList());
        /// Current alias
        user.setText(DataModel.getInstance().getCurrentAlias().getName());
        // balance
        balance.setText(String.valueOf(DataModel.getInstance().getCurrentAlias().getEquity()));
        // loans active
        loansActive.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));

        //WIP
        loans.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        profits.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        loansComplete.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        enforcerActions.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        loansDue.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
        forecast.setText(String.valueOf(DataModel.getInstance().getLoanList().size()));
    }
}
