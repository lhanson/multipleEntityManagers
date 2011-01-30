package example.dao;

import example.om.EntityA;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityDao {
    @PersistenceContext
    private EntityManager entityManager;

    public EntityA getEntityById(Integer id) {
        EntityA entity = null;
        try {
            entity = this.entityManager.find(EntityA.class, id);
        }
        catch (Throwable t) {
            System.out.println("THREW: " + t);
        }
        return entity;
    }

    public void storeEntity(EntityA entity) {
        this.entityManager.persist(entity);
    }
}
