package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Victim;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class BookOfDebtorsController {

    @FXML
    private ListView<Victim> bookList;

    public void init(){
        DataModel.getInstance().loadVictimData();
        bookList.setItems(DataModel.getInstance().getVictimList());
    }
}
