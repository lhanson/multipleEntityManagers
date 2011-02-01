package example.dao;

import java.util.List;

import example.om.EntityB;

public interface EntityDaoB {

    EntityB getEntityById(Long id);
    List<EntityB> getAllEntities();
    void storeEntity(EntityB entity);

}