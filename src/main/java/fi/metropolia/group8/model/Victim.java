package fi.metropolia.group8.model;

public class Victim {

    private String name;
    private String address;
    private String description;

    public Victim(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }
    public Victim(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
