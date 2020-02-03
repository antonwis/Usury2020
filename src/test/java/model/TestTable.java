package model;

import javax.persistence.*;

@Entity
@Table(name = "test_table")
public class TestTable {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private int id;

    public TestTable() {
    }

    public int getId() {
        return id;
    }
}
