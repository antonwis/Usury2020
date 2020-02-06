package model;

import fi.metropolia.group8.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user = new User("kappa");
    User user2 = new User("kappa2");
    User user3 = new User("kappa3");
    @BeforeEach
    void setUp() {

    }

    @Test
    void getId() {
        System.out.println(user.getId());
        System.out.println(user2.getId());
        System.out.println(user3.getId());
    }

    @Test
    void setId() {

    }

    @Test
    void getName() {
        System.out.println(user.getName());
        assertEquals("kappa",user.getName());
    }

    @Test
    @DisplayName("Test name change")
    void setName() {
        user.setName("notKappa");
        System.out.println(user.getName());
        assertEquals("notKappa",user.getName());
    }

    @Test
    void testToString() {
        System.out.println(user);
        assertEquals("kappa",user.toString());
    }
}