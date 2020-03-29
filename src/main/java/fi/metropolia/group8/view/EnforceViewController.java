package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.LoanCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EnforceViewController {

    @FXML
    private AnchorPane enforceAction;

    @FXML
    private HBox enforceHbox;

    @FXML
    private Button enforceThreaten;

    @FXML
    private Button enforceExtort;

    @FXML
    private Button enforceTorture;

    @FXML
    private Button enforceAssassinate;

    private LoanDetailController loanDetailController;
    private Stage stage;
    private LoanCalculator loanCalculator;
    private OverviewController overviewController;

    @FXML
    void assassinate() {
        loanCalculator.updateEnforcedActions(DataModel.getInstance().getCurrentAlias());
        overviewController.updateOverview();
        stage.close();
    }

    @FXML
    void extort() {
        loanCalculator.updateEnforcedActions(DataModel.getInstance().getCurrentAlias());
        overviewController.updateOverview();
        stage.close();
    }

    @FXML
    void threaten() {
        loanCalculator.updateEnforcedActions(DataModel.getInstance().getCurrentAlias());
        overviewController.updateOverview();
        stage.close();
    }

    @FXML
    void torture() {
        loanCalculator.updateEnforcedActions(DataModel.getInstance().getCurrentAlias());
        overviewController.updateOverview();
        stage.close();
    }

    public void TransferMemes(LoanDetailController loanDetailController, Stage stage, LoanCalculator loanCalculator, OverviewController overviewController) {
        this.loanDetailController = loanDetailController;
        this.stage = stage;
        this.loanCalculator = loanCalculator;
        this.overviewController = overviewController;

    }
}
