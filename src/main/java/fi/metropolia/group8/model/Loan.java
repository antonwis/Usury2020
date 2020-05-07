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
     * @param owner owner alias object
     * @param value loan value
     * @param victim victim object
     * @param startDate loan issue date
     * @param dueDate loan due date
     * @param interest interest percentage
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
     * @return loan id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     * @param id loan id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return owner alias object
     */
    @ManyToOne
    @JoinColumn(name = "owner")
    public Alias getOwner() {
        return owner;
    }

    /**
     * @param owner new owner alias object
     */
    public void setOwner(Alias owner) {
        this.owner = owner;
    }

    /**
     * @return loan value
     */
    //Value property stuff
    public FloatProperty valueProperty() {
        if (value == null) value = new SimpleFloatProperty(this, "value");
        return value;
    }

    /**
     * @return loan value
     */
    @Column(name = "value")
    public float getValue() {
        return valueProperty().get();
    }

    /**
     * @param value loan value
     */
    public void setValue(float value) {
        valueProperty().set(value);
    }

    /**
     * @return interest value
     */
    //Interest property stuff
    public FloatProperty interestProperty() {
        if (interest == null) interest = new SimpleFloatProperty(this, "interest");
        return interest;
    }

    /**
     * @return interest value
     */
    @Column(name = "interest_percentage")
    public float getInterest() {
        return interestProperty().get();
    }

    /**
     * @param value interest value
     */
    public void setInterest(float value) {
        interestProperty().set(value);
    }

    /**
     * @return victim object
     */
    @ManyToOne
    @JoinColumn(name = "victim")
    public Victim getVictim() {
        return victim;
    }

    /**
     * @param victim new victim object
     */
    // Probably not needed
    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    /**
     * @return issue date
     */
    @Column(name = "startDate")
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate issue date
     */
    // Probably not needed
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return loan due date
     */
    @Column(name = "dueDate")
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate loan due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return completion status
     */
    @Column(name = "completed")
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed completion status
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return completion date
     */
    @Column(name = "completeDate")
    public LocalDate getCompleteDate() {
        return completeDate;
    }

    /**
     * @param completeDate completion date
     */
    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    /**
     * to string method for loan
     * the lender, amount of money borrowed, borrower, the date of the loan, loan due date.
     *
     * @return loan info
     */
    @Override
    public String toString() {
        return this.owner.getName() + ", " + getValue() + ", " + this.victim.getName() + ", " + getStartDate() + ", " + getDueDate();
    }


}
