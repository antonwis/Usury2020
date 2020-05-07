package fi.metropolia.group8.model;


import javafx.beans.property.*;

import javax.persistence.*;

/**
 * Alias model class
 */
@Entity
@Table(name = "Alias")
@Access(value = AccessType.PROPERTY)
public class Alias {

    private long id;
    private User user;
    private StringProperty name;
    private StringProperty description;
    private FloatProperty equity;
    private IntegerProperty completedLoans;
    private FloatProperty totalProfits;
    private IntegerProperty enforcerActions;


    public Alias() {
        // Empty constructor for hibernate
    }

    /**
     * Constructor for Alias
     *
     * @param user User object
     * @param name name of the Alias
     * @param description Description for the alias
     * @param equity Starting equity for the alias
     */
    public Alias(User user, String name, String description, int equity) {
        this.user = user;
        setName(name);
        setDescription(description);
        setEquity(equity);
        setCompletedLoans(0);
        setTotalProfits(0);
        setEnforcerActions(0);
    }

    /**
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     * @param id unique id for the alias
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return user
     */
    @ManyToOne
    @JoinColumn(name = "user")
    public User getUser() {
        return user;
    }

    /**
     * @param user user object
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String value) {
        nameProperty().set(value);
    }

    @Column(name = "name")
    public String getName() {
        return nameProperty().get();
    }

    /**
     * Name property for Alias
     *
     * @return name
     */
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    /**
     * @param value new description
     */
    public void setDescription(String value) {
        descriptionProperty().set(value);
    }

    /**
     * @return description
     */
    @Column(name = "description")
    public String getDescription() {
        return descriptionProperty().get();
    }

    /**
     * Description property for Alias
     *
     * @return description
     */
    public StringProperty descriptionProperty() {
        if (description == null) description = new SimpleStringProperty(this, "description");
        return description;
    }

    /**
     * @param value new equity value
     */
    public void setEquity(float value) {
        equityProperty().set(value);
    }

    /**
     * @return equity
     */
    @Column(name = "equity")
    public float getEquity() {
        return equityProperty().get();
    }

    /**
     * Equity property for Alias
     *
     * @return equity
     */
    public FloatProperty equityProperty() {
        if (equity == null) equity = new SimpleFloatProperty(this, "equity");
        return equity;
    }

    /**
     * @param value new completed loans value
     */
    public void setCompletedLoans(int value) {
        completedLoansProperty().set(value);
    }

    /**
     * @return completedLoans
     */
    @Column(name = "completed_loans")
    public int getCompletedLoans() {
        return completedLoansProperty().get();
    }

    /**
     * Completed loans property for Alias
     *
     * @return completedLoans
     */
    public IntegerProperty completedLoansProperty() {
        if (completedLoans == null) completedLoans = new SimpleIntegerProperty(this, "completedLoans");
        return completedLoans;
    }

    /**
     * @param value new total profits value
     */
    public void setTotalProfits(float value) {
        totalProfitsProperty().set(value);
    }

    @Column(name = "total_profits")
    public float getTotalProfits() {
        return totalProfitsProperty().get();
    }

    /**
     * Total profits property for Alias
     *
     * @return totalProfits
     */
    public FloatProperty totalProfitsProperty() {
        if (totalProfits == null) totalProfits = new SimpleFloatProperty(this, "totalProfits");
        return totalProfits;
    }

    /**
     * @param value new enforcer action total value
     */
    public void setEnforcerActions(int value) {
        enforcerActionsProperty().set(value);
    }

    /**
     * @return enforcedActions
     */
    @Column(name = "enforcer_actions")
    public int getEnforcerActions() {
        return enforcerActionsProperty().get();
    }

    /**
     * enforcedActions property for Alias
     *
     * @return enforcedActions
     */
    public IntegerProperty enforcerActionsProperty() {
        if (enforcerActions == null) enforcerActions = new SimpleIntegerProperty(this, "enforcerActions");
        return enforcerActions;
    }

    /**
     * @return alias info
     */
    @Override
    public String toString() {
        return getName();
    }
}
