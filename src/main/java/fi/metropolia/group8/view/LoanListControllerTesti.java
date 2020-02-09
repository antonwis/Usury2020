package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.LoanDataModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class LoanListControllerTesti {

    @FXML
    private ListView<Loan> listView;

    private LoanDataModel model;

    public void initModel(LoanDataModel model) {
        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        listView.setItems(model.getLoanList());

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                model.setCurrentLoan(newSelection));

        model.currentLoanProperty().addListener((obs, oldLoan, newLoan) -> {
                if(newLoan == null) {
                    listView.getSelectionModel().clearSelection();
                } else {
                    listView.getSelectionModel().select(newLoan);
                }
        });

        listView.setCellFactory(lv -> new ListCell<Loan>() {

            @Override
            public void updateItem(Loan loan, boolean empty) {
                super.updateItem(loan, empty);
                if(empty) {
                    setText(null);
                } else {
                    setText(loan.getId() + " " + loan.getVictim().getName() + " " + loan.getValue());
                }
            }
        });
    }
}
