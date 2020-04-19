package fi.metropolia.group8.view.Main.EventLog;

import fi.metropolia.group8.model.EventManager;
import fi.metropolia.group8.view.Main.Loans.LoanListController;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls everything related to Eventlog on the view level.
 */
public class EventLogController {

    @FXML
    public Button eventHistory;
    @FXML
    public AnchorPane eventLog;
    @FXML
    private AnchorPane eventlogView;

    @FXML
    private TextArea textAreaEventlog;
    @FXML
    private LoanListController loanListController;

    ListChangeListener<String> changeListener;

    public void init() {
        changeListener = change -> {
            if(change.next()) {
                update();
            }

        };
        EventManager.getInstance().addChangeListener(changeListener);

    }

    /**
     * Updates the Eventlog with new messages
     */
    public void update() {
        textAreaEventlog.appendText(
                EventManager.getInstance().getEventList().get(
                        EventManager.getInstance().getEventList().size()-1) + "\n"
        );
    }

    public void viewEventHistory() throws IOException {
        // TODO open or expand a view and fill it with the session event history
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader event = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Main/EventLog/EventHistory.fxml"));
        Parent root = event.load();
        EventHistoryController e = event.getController();
        e.init(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Work in progress
     * @param loanListController Loanlist Controller
     */
    // pohja
    public void TransferMemes(LoanListController loanListController) {
        this.loanListController = loanListController;
    }
}

