package fi.metropolia.group8.view;

import fi.metropolia.group8.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Predicate;


public class LoanListController {


    @FXML
    private VBox LoanDetailsVbox;
    @FXML
    private TableView<Loan> LoanTableView;
    @FXML
    private TableColumn<Loan, Long> Id;
    @FXML
    private TableColumn<Loan, Alias> Lender;
    @FXML
    private TableColumn<Loan, Float> Amount;
    @FXML
    private TableColumn<Loan, Victim> Debtor;
    @FXML
    private TableColumn<Loan, Float> Interest;
    @FXML
    private TableColumn<Loan, LocalDate> Date;
    @FXML
    private TableColumn<Loan, LocalDate> DueDate;
    @FXML
    private Button newLoanButton;

    private NewLoanController newLoanController;
    private PrimaryController primaryController;

    @FXML
    void newLoan() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        FXMLLoader loan = new FXMLLoader(getClass().getResource("newLoan.fxml"));
        Parent root = loan.load();
        NewLoanController newLoanController = loan.getController();
        newLoanController.TransferMemes(this, stage, primaryController);
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void updateView() {
        DataModel.getInstance().loadLoanData();
        if (DataModel.getInstance().getCurrentAlias() != null) {
            LoanTableView.setItems(DataModel.getInstance().getLoanList());
        } else {
            LoanTableView.setItems(null);
        }
    }

    // Updates view with loans owned by current alias
    public void refreshLoans() {

        DataModel.getInstance().loadLoanData();
        FilteredList<Loan> filteredList = new FilteredList<>(DataModel.getInstance().getLoanList());

        // ID:tä ei voinu verrata suoraan jostain syystä. Pitäs tehä oma DB kutsu koko paskalle mut tämäkin toimii.
        Predicate<Loan> aliasFilter = i -> i.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName());
        filteredList.setPredicate(aliasFilter);


        if (filteredList.size() < 1) {
            LoanTableView.setItems(null);
        } else {
            LoanTableView.setItems(filteredList);
        }

    }

    public void refreshDetails() throws IOException {
        FXMLLoader loanDetails = new FXMLLoader(getClass().getResource("loanDetails.fxml"));
        LoanDetailsVbox.getChildren().setAll((Node) loanDetails.load());
        LoanDetailController loanDetailController = loanDetails.getController();
        loanDetailController.display(this, primaryController);
    }

    public void initModel(PrimaryController primaryController) throws IOException {

        this.primaryController = primaryController;
        updateView();

        LoanTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> DataModel.getInstance().setCurrentLoan(newSelection));
        DataModel.getInstance().currentLoanProperty().addListener((obs, oldLoan, newLoan) -> {
            if (newLoan == null) {
                LoanTableView.getSelectionModel().clearSelection();
            } else {
                LoanTableView.getSelectionModel().select(newLoan);
                try {
                    refreshDetails();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Debtor.setCellValueFactory(victim -> new SimpleObjectProperty(victim.getValue().getVictim().getName()));
        Amount.setCellValueFactory(amount -> amount.getValue().valueProperty().asObject());
        DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }
}


