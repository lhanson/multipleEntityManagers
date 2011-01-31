package example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import example.om.EntityB;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class EntityDaoB {
// Cannot inject disambiguated persistence context in Spring 2.5
//    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
    }

    public EntityB getEntityById(Long id) {
        return this.entityManager.find(EntityB.class, id);
    }

    public List<EntityB> getAllEntities() {
        final Query query = this.entityManager.createQuery("from EntityB");
        @SuppressWarnings("unchecked") // the query specifies type
        final List<EntityB> entities = query.getResultList();
        return entities;
    }

//    @Transactional
    public void storeEntity(EntityB entity) {
//        EntityTransaction transaction = this.entityManager.getTransaction();
//        transaction.begin();
        this.entityManager.persist(entity);
//        transaction.commit();
    }
}
