package fi.metropolia.group8.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * model for loan object
 */
@Entity
@Table(name = "Loan")
@Access(value = AccessType.PROPERTY)
public class Loan {


    private long id;
    private Alias owner;
    private FloatProperty value;
    private Victim victim;
    private LocalDate startDate;
    private LocalDate dueDate;
    private FloatProperty interest;
    private boolean completed;
    private LocalDate completeDate;


    public Loan() {
        // Empty Constructor for hibernate
    }

    /**
     * constructor
     *
     * @param owner
     * @param value
     * @param victim
     * @param startDate
     * @param dueDate
     * @param interest
     */
    public Loan(Alias owner, float value, Victim victim, LocalDate startDate, LocalDate dueDate, float interest) {

        this.owner = owner;
        this.value = new SimpleFloatProperty(value);
        this.victim = victim;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.interest = new SimpleFloatProperty(interest);
    }

    /**
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return
     */
    @ManyToOne
    @JoinColumn(name = "owner")
    public Alias getOwner() {
        return owner;
    }

    /**
     * @param owner
     */
    public void setOwner(Alias owner) {
        this.owner = owner;
    }

    /**
     * @return
     */
    //Value property stuff
    public FloatProperty valueProperty() {
        if (value == null) value = new SimpleFloatProperty(this, "value");
        return value;
    }

    /**
     * @return
     */
    @Column(name = "value")
    public float getValue() {
        return valueProperty().get();
    }

    /**
     * @param value
     */
    public void setValue(float value) {
        valueProperty().set(value);
    }

    /**
     * @return
     */
    //Interest property stuff
    public FloatProperty interestProperty() {
        if (interest == null) interest = new SimpleFloatProperty(this, "interest");
        return interest;
    }

    /**
     * @return
     */
    @Column(name = "interest_percentage")
    public float getInterest() {
        return interestProperty().get();
    }

    /**
     * @param value
     */
    public void setInterest(float value) {
        interestProperty().set(value);
    }

    /**
     * @return
     */
    @ManyToOne
    @JoinColumn(name = "victim")
    public Victim getVictim() {
        return victim;
    }

    /**
     * @param victim
     */
    // Probably not needed
    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    /**
     * @return
     */
    @Column(name = "startDate")
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    // Probably not needed
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return
     */
    @Column(name = "dueDate")
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return
     */
    @Column(name = "completed")
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return
     */
    @Column(name = "completeDate")
    public LocalDate getCompleteDate() {
        return completeDate;
    }

    /**
     * @param completeDate
     */
    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    /**
     * to string method for loan
     *
     * @return
     */
    @Override
    public String toString() {
        return this.owner.getName() + ", " + getValue() + ", " + this.victim.getName() + ", " + getStartDate() + ", " + getDueDate();
    }


}
