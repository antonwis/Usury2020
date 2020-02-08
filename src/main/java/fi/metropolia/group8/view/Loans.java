package fi.metropolia.group8.view;

import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.Victim;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Loans implements Initializable {

    @FXML
    private TableView<Loan> LoanTableView;

    @FXML
    private TableColumn<Loan, Alias> Lender;

    @FXML
    private TableColumn<Loan, Victim> Debtor;

    @FXML
    private TableColumn<Loan, FloatProperty> Amount;

    @FXML
    private TableColumn<Loan, FloatProperty> Interest;

    @FXML
    private TableColumn<Loan, LocalDate> Date;

    @FXML
    private TableColumn<Loan, LocalDate> Due_Date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Lender.setCellValueFactory(n -> new SimpleObjectProperty(n.getValue().getOwner().getName())); // Ghettofix
        Debtor.setCellValueFactory(v -> new SimpleObjectProperty(v.getValue().getVictim().getName()));
        Amount.setCellValueFactory(new PropertyValueFactory<>("value"));
        Interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        Date.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        Due_Date.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        LoanTableView.setItems(getLoans());
    }

    ObservableList<Loan> getLoans() {
        ObservableList<Loan> loans = FXCollections.observableArrayList();
        loans.add(new Loan(new Alias("Shekel Shekelstein", "Khazar king", 9001), 2000, new Victim("Good Goy", "Ebola street 9, Kongo", "Pepega"), LocalDate.now(), LocalDate.now(), 50));
        loans.add(new Loan(new Alias("Ben Shapiro", "now thats epic", 6000000), 90000, new Victim("Super Juden", "Random street 5 , Israel", "Crypto pro"), LocalDate.now(), LocalDate.now(), -50));
        return loans;
    }

}

