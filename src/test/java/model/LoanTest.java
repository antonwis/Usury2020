package model;
import fi.metropolia.group8.model.Alias;
import fi.metropolia.group8.model.Victim;
import fi.metropolia.group8.model.Loan;
import fi.metropolia.group8.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public class LoanTest {
    private LocalDate taken = LocalDate.now();
    private LocalDate due = LocalDate.of(2020,02,21);
    private User user = new User("näkki");
    private Alias alias = new Alias(user, "kappa", "kys", 9000);
    private Victim victim = new Victim("nisti","kappakatu","hintti");
    private Loan loan = new Loan(alias, (float) 20000.0,victim,this.taken,this.due, (float) 1.4);
    @BeforeEach
    public void deleteLoan(){

    }
    @Test
    @DisplayName("Maksupäivän hakeminen")
    public void getDue(){
        assertEquals(LocalDate.of(2020,02,21),loan.getDueDate());
    }
    @Test
    @DisplayName("Maksupäivän muuttaminen")
    public void setDue(){

    }


}
