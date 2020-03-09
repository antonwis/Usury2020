package fi.metropolia.group8.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Data access object for usury2020's data.
 */
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

    /**
     * List all users in database user table.
     * @return List of all users
     */
    public List<User> readUsers() {
        return readObjects(User.class);
    }

    /**
     * Creates new record to database user table.
     * @param user User to be added to database
     */
    public void createUser(User user) {
        createObject(user);
    }

    /**
     * Updates user record in database user table.
     * @param user User to be updated
     */
    public void updateUser(User user) {
        createObject(user);
    }

    /**
     * Query users by id from database user table.
     * @param id id of user to be returned
     * @return user with the specified id
     */
    public User getUserById(long id) {
        return getObjectById(id, User.class);
    }

    /**
     * Delete user by id from database user table.
     * @param id id of the user to be deleted
     */
    public void deleteUserById(long id) {
        deleteObjectById(id, User.class);
    }

    /**
     * Delete user from database user table.
     * @param user user to be deleted
     */
    public void deleteUser(User user) {
        deleteObject(user);
    }

    /**
     * List all Aliases in database user table.
     * @return List of all Aliases
     */
    public List<Alias> readAliases() {
        return readObjects(Alias.class);
    }

    /**
     * Creates new record to database alias table.
     * @param alias Alias to be added to database
     */
    public void createAlias(Alias alias) {
        createObject(alias);
    }

    /**
     * Updates alias record in database alias table.
     * @param alias Alias to be updated
     */
    public void updateAlias(Alias alias) {
        createObject(alias);
    }

    /**
     * Query alias by id from database alias table.
     * @param id id of alias to be returned
     * @return alias with the specified id
     */
    public Alias getAliasById(long id) {
        return getObjectById(id, Alias.class);
    }

    /**
     * Delete alias by id from database alias table.
     * @param id id of the alias to be deleted
     */
    public void deleteAliasById(long id) {
        deleteObjectById(id, Alias.class);
    }

    /**
     * Delete alias from database alias table.
     * @param alias alias to be deleted
     */
    public void deleteAlias(Alias alias) {
        deleteObject(alias);
    }

    /**
     * List all Victims in database victim table.
     * @return List of all Victims
     */
    public List<Victim> readVictims() {
        return readObjects(Victim.class);
    }

    /**
     * Creates new record to database victim table.
     * @param victim Victim to be added to database
     */
    public void createVictim(Victim victim) {
        createObject(victim);
    }

    /**
     * Updates victim record in database victim table.
     * @param victim Victim to be updated
     */
    public void updateVictim(Victim victim) {
        createObject(victim);
    }

    /**
     * Query victim by id from database victim table.
     * @param id id of victim wish to be returned
     * @return user with the specified id
     */
    public Victim getVictimById(long id) {
        return getObjectById(id, Victim.class);
    }

    /**
     * Delete victim by id from database victim table.
     * @param id id of the victim to be deleted
     */
    public void deleteVictimById(long id) {
        deleteObjectById(id, Victim.class);
    }

    /**
     * Delete victim from database victim table.
     * @param victim victim to be deleted
     */
    public void deleteVictim(Victim victim) {
        deleteObject(victim);
    }

    /**
     * List all loans in database victim table.
     * @return List of all Loans
     */
    public List<Loan> readLoans() {
        return readObjects(Loan.class);
    }

    /**
     * Creates new record to database loan table.
     * Loans <em>{@link fi.metropolia.group8.model.Alias} owner</em> and <em>{@link fi.metropolia.group8.model.Victim} victim</em>
     * need to exit in database.
     * @param loan Loan to be added to database
     * @see #createAlias(Alias)
     * @see #createVictim(Victim)
     */
    public void createLoan(Loan loan) {
        createObject(loan);
    }

    /**
     * Updates loan record in database loan table.
     * Loans <em>{@link fi.metropolia.group8.model.Alias} owner</em> and <em>{@link fi.metropolia.group8.model.Victim} victim</em>
     * need to exit in database.
     * @param loan Loan to be updated
     * @see #createAlias(Alias)
     * @see #createVictim(Victim)
     */
    public void updateLoan(Loan loan) {
        createObject(loan);
    }

    /**
     * Query Loan by id from database loan table.
     * @param id id of loan wish to be returned
     * @return loan with the specified id
     */
    public Loan getLoanById(long id) {
        return getObjectById(id, Loan.class);
    }

    /**
     * Delete loan by id from database loan table.
     * @param id id of the loan to be deleted
     */
    public void deleteLoanById(long id) {
        deleteObjectById(id, Loan.class);
    }

    /**
     * Delete loan from database loan table.
     * @param loan loan to be deleted
     */
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
            }
            throw e;
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
                transaction.rollback();
            }
            throw e;
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
            }
            throw e;
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
            }
            throw e;
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
            }
            throw e;
        }
    }

}
