package fi.metropolia.group8.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.Collections;
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
                //System.exit(-1);
            }
        }
    }

    public List<User> readUsers() {
        return readObjects(User.class);
    }

    public void createUser(User user) {
        createObject(user);
    }

    public List<Alias> readAliases() {
        return readObjects(Alias.class);
    }

    public void createAlias(Alias alias) {
        createObject(alias);
    }

    public List<Victim> readVictims() {
        return readObjects(Victim.class);
    }

    public void createVictim(Victim victim) {
        createObject(victim);
    }

    public List<Loan> readLoans() {
        return readObjects(Loan.class);
    }

    public void createLoan(Loan loan) {
        createObject(loan);
    }

    private <T> void createObject(T object) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()){
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> readObjects(Class aClass) {
        Transaction transaction = null;
        List result = Collections.emptyList();
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("FROM " + aClass.getName()).getResultList(); //hql
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
        return (List<T>) result;
    }

}
