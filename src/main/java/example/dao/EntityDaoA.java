package example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import example.om.EntityA;

public class EntityDaoA {
    @PersistenceContext
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
}
