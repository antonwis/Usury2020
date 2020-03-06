package fi.metropolia.group8.view;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class OverviewController {

    @FXML
    private Label user;

    @FXML
    private ComboBox<?> filter;

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

}
