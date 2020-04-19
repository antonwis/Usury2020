package fi.metropolia.group8.view.Main.EventLog;

import fi.metropolia.group8.model.EventManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class EventHistoryController {


    @FXML
    private TextArea eventHistoryArea;

    private Stage stage;
    ObservableList<String> eventHistory;

    public void closeWindow() {
        stage.close();
    }

    public void init(Stage stage) {
        this.stage = stage;
        eventHistory = EventManager.getInstance().getEventList();
        eventHistory.forEach(e -> eventHistoryArea.appendText(e + "\n"));
    }
}
