package fi.metropolia.group8.model.loan;

import fi.metropolia.group8.model.Victim;
import fi.metropolia.group8.model.user.User;
import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    @Column(name="owner")
    private User owner;
    @Column(name="value")
    private float value;
    @Column(name="victim")
    private Victim victim;
    @Column(name="loanTaken")
    private Date loanTakenDate;
    @Column(name="dueDate")
    private Date dueDate;

    public Loan(){

    }
    public Loan(long id, User owner, float value, Victim victim, Date loanTakenDate, Date dueDate){
        id = this.id;
        owner = this.owner;
        value = this.value;
        victim = this.victim;
        loanTakenDate = this.loanTakenDate;
        dueDate = this.dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Victim getVictim() {
        return victim;
    }

    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    public Date getLoanTakenDate() {
        return loanTakenDate;
    }

    public void setLoanTakenDate(Date loanTakenDate) {
        this.loanTakenDate = loanTakenDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


}
