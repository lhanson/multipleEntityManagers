package example.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * An interface for objects requiring extra support to make @Transactional
 * annotated methods work. This is necessary in Spring 2.x with multiple data
 * sources. Since you cannot use @PersistenceContext unambiguously, nor can
 * you use use @Transactional(unitName="...") to specify which transaction
 * manager should be used, we add our own AOP interceptor to execute such
 * methods in a transaction, and the methods on this interface are required to
 * find the correct entity manager to use.
 */
public interface EntityManagedDao {

    /**
     * @return the {@link EntityManager} used by this object
     */
    EntityManager getEntityManager();

    /**
     * @return the {@link EntityManagerFactory} used by this object, used
     *         to generate a {@link TransactionTemplate}
     */
    EntityManagerFactory getEntityManagerFactory();

    /**
     * @param emf the {@link EntityManagerFactory} used by this object
     */
    void setEntityManagerFactory(EntityManagerFactory emf);

}
