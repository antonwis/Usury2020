package fi.metropolia.group8.model;

import javax.persistence.*;
import java.time.LocalDate;

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
    @Column(name="loan_taken")
    private LocalDate loanTakenDate;
    @Column(name="due_date")
    private LocalDate dueDate;
    @Column(name = "intrest")
    private float intrest;

    public Loan(){

    }
    public Loan(User owner, float value, Victim victim, LocalDate loanTakenDate, LocalDate dueDate, float intrest){

        this.owner = owner;
        this.value = value;
        this.victim = victim;
        this.loanTakenDate = loanTakenDate;
        this.dueDate = dueDate;
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

    public LocalDate getLoanTakenDate() {
        return loanTakenDate;
    }

    public void setLoanTakenDate(LocalDate loanTakenDate) {
        this.loanTakenDate = loanTakenDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public float getIntrest() {
        return intrest;
    }

    public void setIntrest(float intrest) {
        this.intrest = intrest;
    }

    @Override
    public String toString(){
        return this.owner.getName() +", "+this.value+", "+this.victim.getName()+", "+this.loanTakenDate+", "+this.dueDate;
    }


}
