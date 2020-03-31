package fi.metropolia.group8.view;

import fi.metropolia.group8.model.EventManager;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * Controls everything related to Eventlog on the view level.
 */
public class EventLogController {

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
                        EventManager.getInstance().getEventList().size()-1)
        );
    }


    /**
     * Work in progress
     * @param loanListController
     */
    // pohja
    public void TransferMemes(LoanListController loanListController) {
        this.loanListController = loanListController;
    }
}

