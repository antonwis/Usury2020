package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.EnforceManager;
import fi.metropolia.group8.model.Loan;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls everything related to enforcement actions on the view level.
 */

public class EnforceViewController implements Initializable {

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

    private Stage stage;
    private OverviewController overviewController;
    private Loan loan;

    /**
     * Calls EnforceManagers assassinate method and updates the overview.
     */
    @FXML
    void assassinate() {
        EnforceManager.getInstance().Assassinate(loan);
        EnforceManager.getInstance().updateEnforcedActions();
        overviewController.updateOverview();
        stage.close();
    }

    /**
     * Calls EnforceManagers extort method and updates the overview.
     */
    @FXML
    void extort() {
        EnforceManager.getInstance().Extort(loan);
        EnforceManager.getInstance().updateEnforcedActions();
        overviewController.updateOverview();
        stage.close();
    }
    /**
     * Calls EnforceManagers threaten method and updates the overview.
     */
    @FXML
    void threaten() {
        EnforceManager.getInstance().Threaten(loan);
        EnforceManager.getInstance().updateEnforcedActions();
        overviewController.updateOverview();
        stage.close();
    }

    /**
     * Calls EnforceManagers torture method and updates the overview.
     */
    @FXML
    void torture() {
        EnforceManager.getInstance().Torture(loan);
        EnforceManager.getInstance().updateEnforcedActions();
        overviewController.updateOverview();
        stage.close();
    }

    /**
     *  Receives controllers from other classes to be used in this one.
     * @param stage
     * @param overviewController
     */

    public void TransferMemes(Stage stage,OverviewController overviewController) {
        System.out.println(loan);
        this.stage = stage;
        this.overviewController = overviewController;
    }
    /**
     * Initializes the controller by setting currentloan to loan field
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loan = DataModel.getInstance().getCurrentLoan();
    }
}
