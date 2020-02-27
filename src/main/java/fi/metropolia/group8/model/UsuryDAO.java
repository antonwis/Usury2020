package fi.metropolia.group8.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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

    public void updateUser(User user) {
        createObject(user);
    }

    public User getUserById(long id) {
        return getObjectById(id, User.class);
    }

    public void deleteUserById(long id) {
        deleteObjectById(id, User.class);
    }

    public void deleteUser(User user) {
        deleteObject(user);
    }

    public List<Alias> readAliases() {
        return readObjects(Alias.class);
    }

    public void createAlias(Alias alias) {
        createObject(alias);
    }

    public void updateAlias(Alias alias) {
        createObject(alias);
    }

    public Alias getAliasById(long id) {
        return getObjectById(id, Alias.class);
    }

    public void deleteAliasById(long id) {
        deleteObjectById(id, Alias.class);
    }

    public void deleteAlias(Alias alias) {
        deleteObject(alias);
    }

    public List<Victim> readVictims() {
        return readObjects(Victim.class);
    }

    public void createVictim(Victim victim) {
        createObject(victim);
    }

    public void updateVictim(Victim victim) {
        createObject(victim);
    }

    public Victim getVictimById(long id) {
        return getObjectById(id, Victim.class);
    }

    public void deleteVictimById(long id) {
        deleteObjectById(id, Victim.class);
    }

    public void deleteVictim(Victim victim) {
        deleteObject(victim);
    }

    public List<Loan> readLoans() {
        return readObjects(Loan.class);
    }

    public void createLoan(Loan loan) {
        createObject(loan);
    }

    public void updateLoan(Loan loan) {
        createObject(loan);
    }

    public Loan getLoanById(long id) {
        return getObjectById(id, Loan.class);
    }

    public void deleteLoanById(long id) {
        deleteObjectById(id, Loan.class);
    }

    public void deleteLoan(Loan loan) {
        deleteObject(loan);
    }

    private <T> void createObject(T object) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

    private <T> List<T> readObjects(Class<T> aClass) {
        Transaction transaction = null;
        List<T> result = Collections.emptyList();
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("FROM " + aClass.getName(), aClass).getResultList(); //hql
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
        return result;
    }

    private <T> T getObjectById(long id, Class<T> aClass) {
        Transaction transaction = null;
        T object = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            object = session.get(aClass, (Long) id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
        return object;
    }

    private <T> void deleteObjectById(long id, Class<T> aClass) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            T object = getObjectById(id, aClass);
            if (object != null) {
                session.delete(object);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

    private <T> void deleteObject(T object) {
        Transaction transaction = null;
        try (Session session = istuntotehdas.openSession()) {
            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }
    }

}
