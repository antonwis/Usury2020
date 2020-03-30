package fi.metropolia.group8.view;

import fi.metropolia.group8.model.EventManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class EventLogController {

    @FXML
    private AnchorPane eventlogView;

    @FXML
    private TextArea textAreaEventlog;
    @FXML
    private LoanListController loanListController;

    public void update() {
        ObservableList<String> meme = EventManager.getInstance().getEventList();
        for (String s : meme) {
            textAreaEventlog.appendText(s);
        }
    }

    // pohja
    public void TransferMemes(LoanListController loanListController) {
        this.loanListController = loanListController;
    }
}

