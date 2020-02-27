package fi.metropolia.group8.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Loan")
@Access(value = AccessType.PROPERTY)
public class Loan {


    private long id;
    private Alias owner;
    private FloatProperty value;
    private Victim victim;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> dueDate;
    private FloatProperty interest;


    public Loan() {
        // Empty Constructor for hibernate
    }

    public Loan(Alias owner, float value, Victim victim, LocalDate startDate, LocalDate dueDate, float interest) {

        this.owner = owner;
        this.value = new SimpleFloatProperty(value);
        this.victim = victim;
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.interest = new SimpleFloatProperty(interest);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "owner")
    public Alias getOwner() {
        return owner;
    }

    public void setOwner(Alias owner) {
        this.owner = owner;
    }

    @Column(name = "value")
    public float getValue() {
        return value.get();
    }

    public FloatProperty valueProperty() {
        if (value == null) value = new SimpleFloatProperty(this, "value");
        return value;
    }

    public void setValue(float value) {
        this.value.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "victim")
    public Victim getVictim() {
        return victim;
    }

    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    @Column(name = "startDate")
    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    @Column(name = "dueDate")
    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    @Column(name = "interest_percentage")
    public float getInterest() {
        return interest.get();
    }

    public FloatProperty interestProperty() {
        if (interest == null) interest = new SimpleFloatProperty(this, "interest");
        return interest;
    }

    public void setInterest(float interest) {
        this.interest.set(interest);
    }

    @Override
    public String toString() {
        return this.owner.getName() + ", " + getValue() + ", " + this.victim.getName() + ", " + getStartDate() + ", " + getDueDate();
    }


}
