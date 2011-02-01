package example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.dao.EntityDaoA;
import example.dao.EntityDaoB;
import example.om.EntityA;
import example.om.EntityB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml"})
public class HibernateDaoTest {
    @Autowired
    private EntityDaoA daoA;
    @Autowired
    private EntityDaoB daoB;

    @Test
    public void testStoreAndRetrieveEntity() {
        assertEquals(0, this.daoA.getAllEntities().size());
        assertEquals(0, this.daoB.getAllEntities().size());

        // Create and store EntityA
        this.daoA.storeEntity(new EntityA());
        this.daoA.storeEntity(new EntityA());
        assertEquals(2, this.daoA.getAllEntities().size());

        // Create and store EntityB
        this.daoB.storeEntity(new EntityB());
        this.daoB.storeEntity(new EntityB());
        assertEquals(2, this.daoB.getAllEntities().size());
    }

}
