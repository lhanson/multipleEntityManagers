package example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import example.om.EntityA;
import javax.persistence.EntityManagerFactory;

public class EntityDaoAImpl implements EntityDaoA, EntityManagedDao {
    // Cannot autowire a disambiguated persistence context in Spring 2.x, so we
    // have to explicitly inject it.
    // @PersistenceContext
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Override
    public EntityA getEntityById(Long id) {
        return this.entityManager.find(EntityA.class, id);
    }

    @Override
    public List<EntityA> getAllEntities() {
        final Query query = this.entityManager.createQuery("from EntityA");
        @SuppressWarnings("unchecked") // the query specifies type
        final List<EntityA> entities = query.getResultList();
        return entities;
    }

    @Override
    @Transactional
    public void storeEntity(EntityA entity) {
        this.entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void throwException() throws Exception {
        throw new Exception("Testing exception propagation");
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    @Override
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
