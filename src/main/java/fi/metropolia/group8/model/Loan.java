package fi.metropolia.group8.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Loan")
@Access(value= AccessType.PROPERTY)
public class Loan {


    private long id;
    private User owner;
    private FloatProperty value;
    private Victim victim;
    private LocalDate startDate;
    private LocalDate dueDate;
    private FloatProperty interest;

    public Loan(){

    }
    public Loan(User owner, float value, Victim victim, LocalDate startDate, LocalDate dueDate, float interest){

        this.owner = owner;
        this.value = new SimpleFloatProperty(value);
        this.victim = victim;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.interest = new SimpleFloatProperty(interest);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="owner")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    //Value property stuff
    public FloatProperty valueProperty() {
        return value;
    }

    @Column(name="value")
    public final float getValue() {
        return valueProperty().get();
    }

    public final void setValue(float value) {
        valueProperty().set(value);
    }

    //Interest property stuff
    public FloatProperty interestProperty() {
        return interest;
    }

    @Column(name = "interest_percentage")
    public final float getInterest() {
        return interestProperty().get();
    }

    public final void setInterest(float value) {
        interestProperty().set(value);
    }


    @Column(name="victim")
    public Victim getVictim() {
        return victim;
    }

    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    @Column(name="startDate")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name="dueDate")
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



    @Override
    public String toString(){
        return this.owner.getName() +", "+this.value+", "+this.victim.getName()+", "+this.startDate +", "+this.dueDate;
    }


}
