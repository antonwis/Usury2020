package fi.metropolia.group8.view.Main.Loans;

import fi.metropolia.group8.model.DataModel;
import fi.metropolia.group8.model.LoanCalculator;
import fi.metropolia.group8.view.Menu.Settings.LanguageController;
import fi.metropolia.group8.view.Overview.OverviewController;
import fi.metropolia.group8.view.PrimaryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for showing and modifying loan details.
 */
public class LoanDetailController {

    @FXML
    public VBox victimDetailVbox;
    @FXML
    private VBox loanDetailVbox;

    @FXML
    private Label LoanDetailHeader;

    @FXML
    private Label TotalDebt;

    @FXML
    private Label IssueDate;

    @FXML
    private Label DebtRemaining;

    @FXML
    private Label ProjectedEarnings;

    @FXML
    private Label Interest;

    @FXML
    private Label DueDate;

    @FXML
    private ImageView ProfileImage;

    @FXML
    private Label VictimName;

    @FXML
    private Label VictimAddress;

    @FXML
    private Label VictimDescription;

    @FXML
    private HBox modifyHbox1;
    @FXML
    private Spinner<Double> interestSpinner;
    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private Button enforceP;

    @FXML
    private Button modifyL;

    @FXML
    private Button completeL;

    @FXML
    private HBox modifyHbox2;

    @FXML
    private Button applyModify;

    @FXML
    private Button cancelModify;

    private LoanListController loanListController;
    private PrimaryController primaryController;
    private OverviewController overviewController;
    private LanguageController languageController;

    /**
     * Updates loan based on modification done in modify loan window
     */
    @FXML
    void applyModify() throws IOException {
        if (interestSpinner.getValue() != null && dueDatePicker.getValue() != null) {
            interestSpinner.commitValue();
            LoanCalculator.getInstance().modifyLoan(DataModel.getInstance().getCurrentLoan(), (float) (double) interestSpinner.getValue(), dueDatePicker.getValue());
            loanListController.refreshLoanDetails();
        }
        modifyHbox1.setVisible(true);
        modifyHbox2.setVisible(false);
        DueDate.setVisible(true);
        dueDatePicker.setVisible(false);
        interestSpinner.setVisible(false);
        Interest.setVisible(true);
        loanListController.refreshLoans();
        overviewController.updateOverview();
    }

    /**
     * Changes fields back to default
     */
    @FXML
    void cancelModify() {
        modifyHbox1.setVisible(true);
        modifyHbox2.setVisible(false);
        DueDate.setVisible(true);
        dueDatePicker.setVisible(false);
        interestSpinner.setVisible(false);
        Interest.setVisible(true);
    }

    /**
     * Marks loan as completed and adds profits to the alias
     */
    @FXML
    void completeLoan() {
        LoanCalculator.getInstance().completeLoan(DataModel.getInstance().getCurrentLoan());
        primaryController.setCurrentAliasText();
        loanListController.refreshLoans();
        overviewController.updateOverview();
    }

    /**
     * Opens enforce payment window with different options.
     * @throws IOException Exception
     */
    @FXML
    void enforcePayment() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("TextResources", locale);
        FXMLLoader enforce = new FXMLLoader(getClass().getResource("/fi/metropolia/group8/view/Main/Loans/EnforceView.fxml"));
        enforce.setResources(resourceBundle);
        Parent root = enforce.load();
        EnforceViewController enforceViewController = enforce.getController();
        enforceViewController.TransferMemes(stage, overviewController, primaryController);
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * changes the fields on loan detail view for modifying
     */
    @FXML
    void modifyLoan() {
        modifyHbox1.setVisible(false);
        modifyHbox2.setVisible(true);
        DueDate.setVisible(false);
        dueDatePicker.setVisible(true);
        interestSpinner.setVisible(true);
        Interest.setVisible(false);
        dueDatePicker.setValue(DataModel.getInstance().getCurrentLoan().getDueDate());
        dueDatePicker.setEditable(false);
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 1000.0, DataModel.getInstance().getCurrentLoan().getInterest());
        interestSpinner.setValueFactory(spinnerValueFactory);
        interestSpinner.setEditable(false);
        dueDatePicker.setValue(DataModel.getInstance().getCurrentLoan().getDueDate());
    }

    /**
     * initializes loanDetailController and gets all the controllers and set the fields based on currently selected loan
     *
     * @param loanListController Loanlist Controller
     * @param primaryController Primary Controller
     * @param overviewController Overview Controller
     */
    public void display(LoanListController loanListController, PrimaryController primaryController, OverviewController overviewController) {

        this.loanListController = loanListController;
        this.primaryController = primaryController;
        this.overviewController = overviewController;
        languageController = new LanguageController();

        // Detail header
        LoanDetailHeader.setText(String.format("%s %s",languageController.getTranslation("loan"), DataModel.getInstance().getCurrentLoan().getId()));

        // Dates
        IssueDate.setText(DataModel.getInstance().getCurrentLoan().getStartDate().toString());
        DueDate.setText(DataModel.getInstance().getCurrentLoan().getDueDate().toString());

        // Original loaned sum
        TotalDebt.setText(String.valueOf(DataModel.getInstance().getCurrentLoan().getValue()));

        // Total Debt remaining
        DebtRemaining.setText(String.valueOf(LoanCalculator.getInstance().getLoanTotalSum(DataModel.getInstance().getCurrentLoan())));

        // Interest percentage
        Interest.setText(String.valueOf(DataModel.getInstance().getCurrentLoan().getInterest()));

        // Interest profit
        ProjectedEarnings.setText(String.valueOf(LoanCalculator.getInstance().getInterestProfit(DataModel.getInstance().getCurrentLoan())));

        // Victim details
        VictimName.setText(DataModel.getInstance().getCurrentLoan().getVictim().getName());
        VictimAddress.setText(DataModel.getInstance().getCurrentLoan().getVictim().getAddress());
        VictimDescription.setText(DataModel.getInstance().getCurrentLoan().getVictim().getDescription());
    }
}
