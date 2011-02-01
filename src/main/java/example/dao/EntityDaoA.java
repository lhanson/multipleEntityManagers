package example.dao;

import java.util.List;

import example.om.EntityA;

public interface EntityDaoA {

    EntityA getEntityById(Long id);
    List<EntityA> getAllEntities();
    void storeEntity(EntityA entity);

}