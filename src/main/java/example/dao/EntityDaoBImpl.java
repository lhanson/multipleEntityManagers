package example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import example.om.EntityB;
import javax.persistence.EntityManagerFactory;
import org.springframework.transaction.annotation.Transactional;

public class EntityDaoBImpl implements EntityDaoB, EntityManagedDao {
    // Cannot autowire a disambiguated persistence context in Spring 2.x, so we
    // have to explicitly inject it.
    // @PersistenceContext
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Override
    public EntityB getEntityById(Long id) {
        return this.entityManager.find(EntityB.class, id);
    }

    @Override
    public List<EntityB> getAllEntities() {
        final Query query = this.entityManager.createQuery("from EntityB");
        @SuppressWarnings("unchecked") // the query specifies type
        final List<EntityB> entities = query.getResultList();
        return entities;
    }

    @Override
    @Transactional
    public void storeEntity(EntityB entity) {
        this.entityManager.persist(entity);
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
