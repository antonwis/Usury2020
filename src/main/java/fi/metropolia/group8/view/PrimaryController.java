package fi.metropolia.group8.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private Tab Loans;
    @FXML
    private Tab Summary;
    @FXML
    private Tab Calendar;
    @FXML
    private Button newAlias;

    @FXML
    void createNewAlias(ActionEvent e) throws IOException {
        NewAlias.display();
    }
}
