package fi.metropolia.group8.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuryDAOTest {

    //private UsuryDAO dao = new UsuryDAO();
    private UsuryDAO dao = new UsuryDAO();
    private int loanCount = 0;
    private final LocalDate localDate = LocalDate.of(2003, 6,12);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        for (Loan l: dao.readLoans()) { dao.deleteLoan(l); }
        for (Victim v: dao.readVictims()){dao.deleteVictim(v);}
        for (Alias a: dao.readAliases()){dao.deleteAlias(a);}
        for (User u: dao.readUsers()){dao.deleteUser(u);}
    }

    @Test
    void readUsers() {
        User user1 = new User("nameRead1");
        User user2 = new User("nameRead2");
        dao.createUser(user1);
        dao.createUser(user2);
        List<User> users = dao.readUsers();
        assertTrue(users.size() > 1);
        assertTrue(users.stream().anyMatch(user -> (user.getId() == user1.getId() && user.getName().equals(user1.getName()))));
        assertTrue(users.stream().anyMatch(user -> (user.getId() == user2.getId() && user.getName().equals(user2.getName()))));
        dao.deleteUser(user1);
        dao.deleteUser(user2);
    }

    @Test
    void createUser() {
        User user1 = new User("nameCreate1");
        int sizeStart = dao.readUsers().size();
        dao.createUser(user1);
        int sizeEnd = dao.readUsers().size();
        assertTrue(sizeStart < sizeEnd);
        List<User> users = dao.readUsers();
        assertEquals(1, users.size());
        assertTrue(users.stream().anyMatch(user -> (user.getId() == user1.getId() && user.getName().equals(user1.getName()))));
        dao.deleteUser(user1);
    }

    @Test
    void updateUser() {
        User user1 = new User("nameOld1");
        dao.createUser(user1);
        long id = user1.getId();
        user1.setName("nameNew1");
        dao.updateUser(user1);
        User user2 = dao.getUserById(id);
        System.out.println(user2.getName() + "id" +user2.getId());
        assertEquals("nameNew1", user2.getName());
        dao.deleteUser(user1);
    }

    @Test
    void getUserById() {
        User user1 = new User("nameUserById1");
        dao.createUser(user1);
        long id = user1.getId();
        User user2 = dao.getUserById(id);
        assertEquals(id, user2.getId());
        assertEquals(user1.getName(), user2.getName());
        dao.deleteUser(user1);
    }

    @Test
    void deleteUserById() {
        User user1 = new User("nameDeleteUserById1");
        dao.createUser(user1);
        long id = user1.getId();
        assertNotNull(dao.getUserById(id));
        dao.deleteUserById(id);
        assertNull(dao.getUserById(id));

    }

    @Test
    void deleteUser() {
        User user1 = new User("nameDeleteUser1");
        dao.createUser(user1);
        long id = user1.getId();

        assertNotNull(dao.getUserById(id));
        dao.deleteUser(user1);
        assertNull(dao.getUserById(id));

    }

    @Test
    void readAliases() {
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "name1", "desc1", 100);
        Alias alias2 = new Alias(user1, "name2", "desc2", 200);
        dao.createAlias(alias1);
        dao.createAlias(alias2);
        List<Alias> aliases = dao.readAliases();
        assertTrue(aliases.size() > 1);
        assertTrue(aliases.stream().
                anyMatch(alias -> (
                        alias.getName().equals(alias1.getName()) &&
                                alias.getDescription().equals(alias1.getDescription()) &&
                                alias.getEquity() == alias1.getEquity() &&
                                alias.getId() == alias1.getId())));
        assertTrue(aliases.stream().
                anyMatch(alias -> (
                        alias.getName().equals(alias2.getName()) &&
                                alias.getDescription().equals(alias2.getDescription()) &&
                                alias.getEquity() == alias2.getEquity() &&
                                alias.getId() == alias2.getId())));
        dao.deleteUser(user1);
        dao.deleteAlias(alias1);
        dao.deleteAlias(alias2);
    }

    @Test
    void createAlias() {
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "nameCreateAlias1", "descCreateAlias1", 110);
        int sizeStart = dao.readAliases().size();
        dao.createAlias(alias1);
        int sizeEnd = dao.readAliases().size();
        assertTrue(sizeStart < sizeEnd);
        List<Alias> aliases = dao.readAliases();
        assertTrue(aliases.stream().
                anyMatch(alias -> (
                        alias.getName().equals(alias1.getName()) &&
                                alias.getDescription().equals(alias1.getDescription()) &&
                                alias.getEquity() == alias1.getEquity() &&
                                alias.getId() == alias1.getId())));
        dao.deleteUser(user1);
        dao.deleteAlias(alias1);
    }

    @Test
    void updateAlias() {
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "nameUpdateAlias1", "descUpdateAlias1", 120);
        dao.createAlias(alias1);
        long id = alias1.getId();
        alias1.setName("updatedName1");
        alias1.setEquity(310);
        dao.updateAlias(alias1);
        Alias alias2 = dao.getAliasById(id);
        assertEquals(id, alias2.getId());
        assertEquals("updatedName1", alias2.getName());
        dao.deleteUser(user1);
        dao.deleteAlias(alias1);
    }

    @Test
    void getAliasById() {
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "nameAliasById1", "descAliasById1", 130);
        dao.createAlias(alias1);
        long id = alias1.getId();
        Alias alias2 = dao.getAliasById(id);
        assertEquals(id, alias2.getId());
        assertEquals(alias1.getName(), alias2.getName());
        assertEquals(alias1.getDescription(), alias2.getDescription());
        assertEquals(alias1.getEquity(), alias2.getEquity());
        dao.deleteUser(user1);
        dao.deleteAlias(alias1);
    }

    @Test
    void deleteAliasById() {
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "nameDeleteAliasById1", "descDeleteAliasById1", 140);
        dao.createAlias(alias1);
        long id = alias1.getId();
        int sizeStart = dao.readAliases().size();
        dao.deleteAliasById(id);
        int sizeEnd = dao.readAliases().size();
        assertTrue(sizeStart > sizeEnd);
        assertNull(dao.getUserById(id));
        dao.deleteUser(user1);

    }

    @Test
    void deleteAlias() {
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "nameDeleteAlias1", "descDeleteAlias1", 150);
        dao.createAlias(alias1);
        long id = alias1.getId();
        int sizeStart = dao.readAliases().size();
        dao.deleteAlias(alias1);
        int sizeEnd = dao.readAliases().size();
        assertTrue(sizeStart > sizeEnd);
        assertNull(dao.getUserById(id));
        dao.deleteUser(user1);
    }

    @Test
    void readVictims() {
        Victim victim1 = new Victim("nameVictim1", "addrVictim1", "descVictim1");
        Victim victim2 = new Victim("nameVictim2", "addrVictim2", "descVictim2");
        dao.createVictim(victim1);
        dao.createVictim(victim2);
        List<Victim> victims = dao.readVictims();
        assertTrue(victims.stream().
                anyMatch(victim -> (
                        victim.getName().equals(victim1.getName()) &&
                                victim.getAddress().equals(victim1.getAddress()) &&
                                victim.getDescription().equals(victim1.getDescription()) &&
                                victim.getId() == victim1.getId()
                )));
        assertTrue(victims.stream().
                anyMatch(victim -> (
                        victim.getName().equals(victim2.getName()) &&
                                victim.getAddress().equals(victim2.getAddress()) &&
                                victim.getDescription().equals(victim2.getDescription()) &&
                                victim.getId() == victim2.getId()
                )));
        dao.deleteVictim(victim1);
        dao.deleteVictim(victim2);
    }

    @Test
    void createVictim() {
        Victim victim1 = new Victim("nameCreateVictim1", "addrCreateVictim1", "descCreateVictim1");
        int sizeStart = dao.readVictims().size();
        dao.createVictim(victim1);
        int sizeEnd = dao.readVictims().size();
        assertTrue(sizeStart < sizeEnd);
        List<Victim> victims = dao.readVictims();
        assertTrue(victims.stream().
                anyMatch(victim -> (
                        victim.getName().equals(victim1.getName()) &&
                                victim.getAddress().equals(victim1.getAddress()) &&
                                victim.getDescription().equals(victim1.getDescription()) &&
                                victim.getId() == victim1.getId()
                )));
        dao.deleteVictim(victim1);
    }

    @Test
    void updateVictim() {
        Victim victim1 = new Victim("nameUpdateVictim1", "addrUpdateVictim1", "descUpdateVictim1");
        int sizeStart = dao.readVictims().size();
        dao.createVictim(victim1);
        long id = victim1.getId();
        victim1.setName("updatedName1");
        victim1.setDescription("updatedDesc1");
        dao.updateVictim(victim1);
        assertEquals(sizeStart + 1, dao.readVictims().size());
        Victim victim2 = dao.getVictimById(id);
        assertEquals(id, victim2.getId());
        assertEquals("updatedName1", victim2.getName());
        assertEquals("updatedDesc1", victim2.getDescription());
        dao.deleteVictim(victim1);

    }

    @Test
    void getVictimById() {
        Victim victim1 = new Victim("nameVictimById1", "addrVictimById1", "descVictimById1");
        dao.createVictim(victim1);
        Victim victim2 = dao.getVictimById(victim1.getId());
        assertEquals(victim1.getId(), victim2.getId());
        assertEquals(victim1.getName(), victim2.getName());
        assertEquals(victim1.getAddress(), victim2.getAddress());
        assertEquals(victim1.getDescription(), victim2.getDescription());
        dao.createVictim(victim1);
    }

    @Test
    void deleteVictimById() {
        Victim victim1 = new Victim("nameDeleteVictim1", "addrDeleteVictim1", "descDeleteVictim1");
        int sizeStart = dao.readVictims().size();
        dao.createVictim(victim1);
        long id = victim1.getId();
        dao.deleteVictim(victim1);
        int sizeEnd = dao.readVictims().size();
        assertEquals(sizeStart, sizeEnd);
        assertNull(dao.getVictimById(id));

    }

    @Test
    void deleteVictim() {
        Victim victim1 = new Victim("nameDeleteVictimById1", "addrDeleteVictimById1", "descDeleteVictimById1");
        int sizeStart = dao.readVictims().size();
        dao.createVictim(victim1);
        long id = victim1.getId();
        dao.deleteVictimById(id);
        int sizeEnd = dao.readVictims().size();
        assertEquals(sizeStart, sizeEnd);
        assertNull(dao.getVictimById(id));

    }

    @Test
    void readLoans() {
        Loan loan1 = this.getNextLoan();
        Loan loan2 = this.getNextLoan();
        dao.createUser(loan1.getOwner().getUser());
        dao.createUser(loan2.getOwner().getUser());
        dao.createVictim(loan1.getVictim());
        dao.createAlias(loan1.getOwner());
        dao.createLoan(loan1);
        dao.createVictim(loan2.getVictim());
        dao.createAlias(loan2.getOwner());
        dao.createLoan(loan2);
        dao.deleteLoan(loan1);
        dao.deleteLoan(loan2);
        dao.deleteUser(loan1.getOwner().getUser());
        dao.deleteUser(loan2.getOwner().getUser());
        dao.deleteVictim(loan1.getVictim());
        dao.deleteAlias(loan1.getOwner());
        dao.deleteVictim(loan2.getVictim());
        dao.deleteAlias(loan2.getOwner());
    }

    @Test
    void createLoan() {
        Loan loan1 = this.getNextLoan();
        int startSize = dao.readLoans().size();
        dao.createUser(loan1.getOwner().getUser());
        dao.createVictim(loan1.getVictim());
        dao.createAlias(loan1.getOwner());
        dao.createLoan(loan1);
        int endSize = dao.readLoans().size();
        assertEquals(startSize + 1, endSize);
        List<Loan> loans = dao.readLoans();
        assertTrue(loans.stream().
                anyMatch( loan -> (
                        Math.abs(loan.getValue() - loan1.getValue()) < 0.001 &&
                                loan.getOwner().getId() == loan1.getOwner().getId() &&
                                loan.getVictim().getName().equals(loan1.getVictim().getName())
                )));
        dao.deleteLoan(loan1);
        dao.deleteUser(loan1.getOwner().getUser());
        dao.deleteVictim(loan1.getVictim());
        dao.deleteAlias(loan1.getOwner());
    }

    @Test
    void updateLoan() {
        Loan loan1 = this.getNextLoan();
        dao.createUser(loan1.getOwner().getUser());
        dao.createVictim(loan1.getVictim());
        dao.createAlias(loan1.getOwner());
        dao.createLoan(loan1);
        int startSize = dao.readLoans().size();
        User user1 = new User("nameRead1");
        dao.createUser(user1);
        Alias alias1 = new Alias(user1, "updateLoanAliasName1", "updateLoanAliasDescription1", 122);
        dao.createAlias(alias1);
        loan1.setOwner(alias1);
        dao.updateLoan(loan1);
        int endSize = dao.readLoans().size();
        assertEquals(startSize, endSize);
        Loan updatedLoan = dao.getLoanById(loan1.getId());
        assertEquals("updateLoanAliasName1", updatedLoan.getOwner().getName());
        dao.deleteUser(user1);
        dao.deleteVictim(loan1.getVictim());
        dao.deleteAlias(loan1.getOwner());
        dao.deleteLoan(loan1);
    }

    @Test
    void getLoanById() {
        Loan loan1 = this.getNextLoan();
        dao.createUser(loan1.getOwner().getUser());
        dao.createVictim(loan1.getVictim());
        dao.createAlias(loan1.getOwner());
        dao.createLoan(loan1);
        Loan loan2 = dao.getLoanById(loan1.getId());
        assertEquals(loan1.getId(), loan2.getId());
        assertEquals(loan1.getVictim().getDescription(), loan2.getVictim().getDescription());
        assertEquals(loan1.getValue(), loan2.getValue(), (float) 0.001);
        dao.deleteLoan(loan1);
        dao.deleteUser(loan1.getOwner().getUser());
        dao.deleteVictim(loan1.getVictim());
        dao.deleteAlias(loan1.getOwner());

    }

    @Test
    void deleteLoanById() {
        Loan loan1 = this.getNextLoan();
        dao.createUser(loan1.getOwner().getUser());
        dao.createVictim(loan1.getVictim());
        dao.createAlias(loan1.getOwner());
        dao.createLoan(loan1);
        long id = loan1.getId();
        assertNotNull(dao.getLoanById(id));
        dao.deleteLoanById(id);
        assertNull(dao.getLoanById(id));
        dao.deleteUser(loan1.getOwner().getUser());
        dao.deleteVictim(loan1.getVictim());
        dao.deleteAlias(loan1.getOwner());
    }

    @Test
    void deleteLoan() {
        Loan loan1 = this.getNextLoan();
        dao.createUser(loan1.getOwner().getUser());
        dao.createVictim(loan1.getVictim());
        dao.createAlias(loan1.getOwner());
        dao.createLoan(loan1);
        long id = loan1.getId();
        assertNotNull(dao.getLoanById(id));
        dao.deleteLoan(loan1);
        assertNull(dao.getLoanById(id));
        dao.deleteUser(loan1.getOwner().getUser());
        dao.deleteVictim(loan1.getVictim());
        dao.deleteAlias(loan1.getOwner());
    }

    private Loan getNextLoan() {
        User user1 = new User("nameRead1");
        Alias alias = new Alias(user1, "LoanAliasName" + loanCount, "LoanAliasDescription" + loanCount, 110 * loanCount);
        Victim victim = new Victim("LoanVictimName" + loanCount, "LoanVictimAddress" + loanCount, "LoanVictimDescription" + loanCount);
        Loan loan = new Loan(alias, (float) 1.1 * loanCount, victim, localDate.minusDays(loanCount), localDate.plusDays(loanCount), (float) 0.1 * loanCount);
        loanCount++;
        return loan;
    }
}