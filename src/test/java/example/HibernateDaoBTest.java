package example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import example.dao.EntityDaoB;
import example.om.EntityB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContextB.xml"})
@Transactional
public class HibernateDaoBTest {
    @Autowired
    private EntityDaoB daoB;

    @Test
    public void testStoreAndRetrieveEntity() {
        assertEquals(0, this.daoB.getAllEntities().size());

        // Create and store EntityB
        this.daoB.storeEntity(new EntityB());
        this.daoB.storeEntity(new EntityB());
        assertEquals(2, this.daoB.getAllEntities().size());
    }

    @Test
    public void testRollback() {
        // Each test should be executed in isolation and rolled back when done
        // Yes, this relies on the order of execution of tests...
        assertEquals(0, this.daoB.getAllEntities().size());
    }

}
