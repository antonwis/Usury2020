package fi.metropolia.group8.view.Menu;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.Victim;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.function.Predicate;

public class BookOfDebtorsController {

    @FXML
    private ListView<Victim> bookList;

    /**
     * Initializes victims
     */
    public void init() {
        DataModel.getInstance().loadVictimData();
        refreshVictims();
    }

    /**
     * Shows the debtors based on what user is active.
     */
    public void refreshVictims() {

        DataModel.getInstance().loadLoanData();
        DataModel.getInstance().loadAliasData();
        FilteredList<Loan> filteredList = new FilteredList<>(DataModel.getInstance().getLoanList());

        if (DataModel.getInstance().getCurrentAlias() != null) {
            try {
                ObjectProperty<Predicate<Loan>> userFilter = new SimpleObjectProperty<>();

                userFilter.bind(Bindings.createObjectBinding(() ->
                        i -> i.getOwner().getUser().getName().equals(DataModel.getInstance().getCurrentAlias().getUser().getName())));

                // filtteröi lainalistan tarkistamalla kuuluuko laina tämän hetkiselle aliakselle ja onko laina completed

                filteredList.predicateProperty().bind(Bindings.createObjectBinding(userFilter::get, userFilter));

                if (filteredList.size() < 1) {
                    bookList.setItems(null);
                } else {
                    ObservableList<Victim> victims = FXCollections.observableArrayList();
                    for (Loan loan : filteredList) {
                        System.out.println(loan.getVictim());
                        victims.add(loan.getVictim());
                    }
                    bookList.setItems(victims);
                }
            } catch (Exception e) {
                System.out.println("Loan refresh failed");
            }
        } else {
            bookList.setItems(null);
        }

    }
}
