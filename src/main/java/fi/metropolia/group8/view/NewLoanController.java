package fi.metropolia.group8.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class NewLoanController {

    @FXML
    private Button loanConfirmButton;

    @FXML
    private Button loanCancelButton;

    @FXML
    void loanCancel(ActionEvent event) {
        newLoan.close();
    }

    @FXML
    void loanConfirm(ActionEvent event) {
        System.out.println("LOOOL");
    }


    private static Stage newLoan;

    public static void display() throws IOException {
        newLoan = new Stage();
        FXMLLoader loan = new FXMLLoader();
        AnchorPane newLoanFxml = FXMLLoader.load(NewLoanController.class.getResource("newLoan.fxml"));
        NewLoanController newLoanController = loan.getController();
        Scene scene = new Scene(newLoanFxml, 430, 525);
        newLoan.setScene(scene);
        newLoan.show();
    }
}
