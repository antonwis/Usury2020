import fi.metropolia.group8.model.*;

import java.time.LocalDate;

public class ReadUsersTest {
/*
    private static UsuryDAO dao;

    public static void main(String[] args) {
        User newUser = new User("free");
        System.out.println("newUser:" + newUser.getName());
        dao = new UsuryDAO();
        dao.createUser(newUser);
        User[] users = dao.readUsers();
        System.out.println(users.length);
        System.out.println("newUser:" + newUser.getName());
        for (User user: users) {
            System.out.println(user.getName() + user.getId());
        }

        Alias newAlias = new Alias("re3o", "plos", 1);
        System.out.println("newAlias: " + newAlias.getName() + newAlias.getDescription());
        dao.createAlias(newAlias);
        Alias[] aliases = dao.readAliases();
        for (Alias alias : aliases) {
            System.out.println(alias.getName() + " " + alias.getDescription() + " " + alias.getEquity() + " " + alias.getId());
        }

        Victim newVictim = new Victim("eepa", "katoja 2", "velkaa!!");
        System.out.println("newVictim: " + newVictim);
        dao.createVictim(newVictim);
        Victim[] victims = dao.readVictims();
        for (Victim victim : victims) {
            System.out.println(victim.getName() + " " + victim.getAddress() + " " + victim.getDescription() + " " + victim.getId());
        }

        Loan newLoan = new Loan(newAlias, (float) 100.1, newVictim, LocalDate.now() , LocalDate.now(), (float) 1.2);
        System.out.println("newLoan: " + newLoan);
        dao.createLoan(newLoan);
        Loan[] loans = dao.readLoans();
        for (Loan loan : loans){
            System.out.println(loan);
            System.out.println(loan.getOwner().getDescription());
        }

    }*/
}
