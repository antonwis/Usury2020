package fi.metropolia.group8.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventLogController implements Initializable {

    @FXML
    private AnchorPane eventLogPane;

    @FXML
    private TextArea eventLogWindow;

    private ArrayList<String> logs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logs = new ArrayList<>();
        for(int i = 0; i<10; i++){
            logs.add("AAAA");
        }
        for(String s: logs){
            eventLogWindow.appendText(s+"\n");
        }
    }
}