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

    public EntityA getEntityById(Long id) {
        return this.entityManager.find(EntityA.class, id);
    }

    public List<EntityA> getAllEntities() {
        final Query query = this.entityManager.createQuery("from EntityA");
        @SuppressWarnings("unchecked") // the query specifies type
        final List<EntityA> entities = query.getResultList();
        return entities;
    }

    @Transactional
    public void storeEntity(EntityA entity) {
        this.entityManager.persist(entity);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
