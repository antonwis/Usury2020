package fi.metropolia.group8.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class UsuryDAO {

    private static SessionFactory istuntotehdas;

    public UsuryDAO() {
        if (istuntotehdas == null){
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            try {
                istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Istuntotehtaan luonti ei onnistu");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);
                System.exit(-1);
            }
        }
    }

    public User[] readUsers() {
        Transaction transaction = null;
        User[] users = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            List result = session.createQuery("FROM fi.metropolia.group8.model.User").getResultList();
            users = new User[result.size()];
            result.toArray(users);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
        return users;
    }

    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public Alias[] readAliases() {
        Transaction transaction = null;
        Alias[] aliases = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            List result = session.createQuery("FROM fi.metropolia.group8.model.Alias").getResultList();
            aliases = new Alias[result.size()];
            result.toArray(aliases);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
        return aliases;
    }

    public void createAlias(Alias alias) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()){
            transaction = session.beginTransaction();
            session.save(alias);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public Victim[] readVictims() {
        Transaction transaction = null;
        Victim[] victims = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            List result = session.createQuery("FROM fi.metropolia.group8.model.Victim").getResultList();
            victims = new Victim[result.size()];
            result.toArray(victims);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
        return victims;
    }

    public void createVictim(Victim victim) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()){
            transaction = session.beginTransaction();
            session.save(victim);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public Loan[] readLoans() {
        Transaction transaction = null;
        Loan[] loans = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            List result = session.createQuery("FROM fi.metropolia.group8.model.Loan").getResultList();
            loans = new Loan[result.size()];
            result.toArray(loans);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
        return loans;
    }

    public void createLoan(Loan loan) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()){
            transaction = session.beginTransaction();
            session.save(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }


}
