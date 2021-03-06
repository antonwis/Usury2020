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
import net.bytebuddy.asm.Advice;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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

    /**
     * Initializes observer to eventmanager.
     */
    public void init() {
        changeListener = change -> { if(change.next()) { update(); } };
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

    /**
     * Opens a view and fills it with the session event history
     * @throws IOException Exception
     */
    public void viewEventHistory() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
        FXMLLoader event = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Main/EventLog/EventHistory.fxml"));
        event.setResources(resourceBundle);
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

