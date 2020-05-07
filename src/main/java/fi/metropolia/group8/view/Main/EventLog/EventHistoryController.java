package fi.metropolia.group8.view.Main.EventLog;

import fi.metropolia.group8.model.EventManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Displays the entire event history.
 */
public class EventHistoryController {


    @FXML
    private TextArea eventHistoryArea;

    private Stage stage;
    ObservableList<String> eventHistory;

    /**
     * Closes the popup window.
     */
    public void closeWindow() {
        stage.close();
    }

    /**
     * Initializes the window and adds every history message to a list to be displayed.
     * @param stage Stage
     */
    public void init(Stage stage) {
        this.stage = stage;
        eventHistory = EventManager.getInstance().getEventList();
        eventHistory.forEach(e -> eventHistoryArea.appendText(e + "\n"));
    }
}
