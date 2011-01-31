package example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import example.om.EntityA;
import javax.persistence.EntityManagerFactory;

public class EntityDaoA {
// Cannot inject disambiguated persistence context in Spring 2.5
//    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
        System.out.println("!!!!!!!! SET EM: " + this.entityManager);
    }

    public EntityA getEntityById(Long id) {
        return this.entityManager.find(EntityA.class, id);
    }

    public List<EntityA> getAllEntities() {
        final Query query = this.entityManager.createQuery("from EntityA");
        @SuppressWarnings("unchecked") // the query specifies type
        final List<EntityA> entities = query.getResultList();
        return entities;
    }

//    @Transactional
//    https://jira.springframework.org/browse/SPR-3955
    public void storeEntity(EntityA entity) {
//        EntityTransaction transaction = this.entityManager.getTransaction();
//        transaction.begin();
        this.entityManager.persist(entity);
//        transaction.commit();
    }
}
