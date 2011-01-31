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
        assertNotNull(this.daoA);
        assertNull(this.daoA.getEntityById(1L));
        assertNotNull(this.daoB);
        assertNull(this.daoB.getEntityById(1L));

        // Create and store EntityA
        EntityA entityA = new EntityA();
        entityA.setAuthorId(1);
        entityA.setText("Text from author 1");
        this.daoA.storeEntity(entityA);

        assertEquals(1, this.daoA.getAllEntities().size());

        // Create and store EntityB
        EntityB entityB = new EntityB();
        entityB.setAuthorId(1);
        entityB.setText("Text from author 1");
        this.daoB.storeEntity(entityB);

        assertEquals(1, this.daoB.getAllEntities().size());
    }

}
