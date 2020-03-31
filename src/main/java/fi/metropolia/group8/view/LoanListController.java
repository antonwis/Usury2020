package fi.metropolia.group8.view;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.Victim;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * controller for loanListView
 */
public class LoanListController {

    @FXML
    private AnchorPane eventlogView;
    @FXML
    private VBox LoanDetailsVbox;
    @FXML
    private TableView<Loan> LoanTableView;
    @FXML
    private TableColumn<Loan, Long> Id;
    @FXML
    private TableColumn<Loan, Float> Amount;
    @FXML
    private TableColumn<Loan, Victim> Debtor;

    @FXML
    private TableColumn<Loan, LocalDate> DueDate;
    @FXML
    private Button newLoanButton;


    private PrimaryController primaryController;
    private OverviewController overviewController;

    // FXML annotaatio nii ei tarvii edes luoda new (dependency injection memes)
    @FXML private EventLogController eventLogController;

    /**
     * opens newLoan window
     *
     * @throws IOException
     */
    @FXML
    void newLoan() throws IOException {
        if (DataModel.getInstance().getCurrentAlias() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Select an Alias first");
            alert.show();
        } else {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            FXMLLoader loan = new FXMLLoader(getClass().getResource("newLoan.fxml"));
            Parent root = loan.load();
            NewLoanController newLoanController = loan.getController();
            newLoanController.TransferMemes(this, stage, primaryController, overviewController);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Updates view with loans owned by current alias
     */

    public void refreshLoans() {

        DataModel.getInstance().loadLoanData();
        DataModel.getInstance().loadAliasData();
        FilteredList<Loan> filteredList = new FilteredList<>(DataModel.getInstance().getLoanList());

        if (DataModel.getInstance().getCurrentAlias() != null) {
            try {
                ObjectProperty<Predicate<Loan>> userFilter = new SimpleObjectProperty<>();
                ObjectProperty<Predicate<Loan>> aliasFilter = new SimpleObjectProperty<>();

                userFilter.bind(Bindings.createObjectBinding(() ->
                        i -> i.getOwner().getUser().getName().equals(DataModel.getInstance().getCurrentAlias().getUser().getName()) && i.isCompleted() == false));

                // filtteröi lainalistan tarkistamalla kuuluuko laina tämän hetkiselle aliakselle ja onko laina completed
                aliasFilter.bind(Bindings.createObjectBinding(() ->
                        i -> i.getOwner().getName().equals(DataModel.getInstance().getCurrentAlias().getName()) && i.isCompleted() == false));

                filteredList.predicateProperty().bind(Bindings.createObjectBinding(
                        () -> userFilter.get().and(aliasFilter.get()),
                        userFilter, aliasFilter));

                if (filteredList.size() < 1) {
                    LoanTableView.setItems(null);
                } else {
                    LoanTableView.setItems(filteredList);
                }
            } catch (Exception e) {
                System.out.println("Loan refresh failed");
            }
        } else {
            LoanTableView.setItems(null);
        }

    }

    /**
     * sets detail view based on selected loan
     *
     * @throws IOException
     */
    public void refreshDetails() throws IOException {
        if (DataModel.getInstance().getCurrentLoan() != null) {
            FXMLLoader loanDetails = new FXMLLoader(getClass().getResource("loanDetails.fxml"));
            LoanDetailsVbox.getChildren().setAll((Node) loanDetails.load());
            LoanDetailController loanDetailController = loanDetails.getController();
            loanDetailController.display(this, primaryController, overviewController);
        }
    }

    // print viestit eventlogiin (kutsutaan initModel)
    public void killme(){
        eventLogController.init();
    }

    /**
     * initializes controller
     *
     * @param primaryController
     * @param overviewController
     */
    public void initModel(PrimaryController primaryController, OverviewController overviewController) {
        if (this.primaryController == null) {
            this.primaryController = primaryController;
            this.overviewController = overviewController;
            ///////////////
            killme();
            ///////////////
            LoanTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> DataModel.getInstance().setCurrentLoan(newSelection));
            DataModel.getInstance().currentLoanProperty().addListener((obs, oldLoan, newLoan) -> {
                if (newLoan == null) {
                    LoanTableView.getSelectionModel().clearSelection();
                    try {
                        FXMLLoader placeholder = new FXMLLoader(getClass().getResource("placeholder.fxml"));
                        LoanDetailsVbox.getChildren().setAll((Node) placeholder.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
}


