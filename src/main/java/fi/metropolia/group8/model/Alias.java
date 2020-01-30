package fi.metropolia.group8.model;

public class Alias {

    private String name;
    private String description;
    private int equity = 0;


    public Alias(String name, int equity) {
        this.name = name;
        this.equity = equity;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEquity() {
        return equity;
    }

    public void setEquity(int equity) {
        this.equity = equity;
    }

    public void addMoney(int money) {
        this.equity += money;
    }
}
