package fi.metropolia.group8.model;

import javax.persistence.*;

@Entity
@Table
public class Victim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "desc")
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

    @Override
    public String toString(){
        return this.name+", "+this.address+", "+this.description;
    }
}
