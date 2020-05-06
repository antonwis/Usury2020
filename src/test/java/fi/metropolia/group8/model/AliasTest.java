package fi.metropolia.group8.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AliasTest {

    private Alias alias;
    private final double DELTA = 0.001;

    @BeforeEach
    void init() {
        User user1 = new User("nameRead1");
        alias = new Alias(user1, "Ben Shapiro", "Professional Jew", 9001);
    }

    @Test
    void getId() {
        assertEquals(0, alias.getId(), "ID should be 0");
    }

    @Test
    void setName() {
        alias.setName("George Soros");
        assertEquals("George Soros", alias.getName(), "Wrong name");
    }

    @Test
    void getName() {
        assertEquals("Ben Shapiro", alias.getName(), "Wrong name");
    }

    @Test
    void setDescription() {
        alias.setDescription("Tiny Hat Man");
        assertEquals("Tiny Hat Man", alias.getDescription(), "Wrong description");
    }

    @Test
    void getDescription() {
        assertEquals("Professional Jew", alias.getDescription(), "Wrong description");
    }


    @Test
    void setEquity() {
        alias.setEquity(5000);
        assertEquals(5000, alias.getEquity(), "Wrong equity amount");
    }

    @Test
    void getEquity() {
        assertEquals(9001, alias.getEquity(), "Wrong amount.");
    }

}