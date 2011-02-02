package example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import example.dao.EntityDaoA;
import example.om.EntityA;
import org.springframework.test.annotation.ExpectedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContextA.xml"})
@Transactional
public class HibernateDaoATest {
    @Autowired
    private EntityDaoA daoA;

    @Test
    public void testStoreAndRetrieveEntity() {
        assertEquals(0, this.daoA.getAllEntities().size());

        // Create and store EntityA
        this.daoA.storeEntity(new EntityA());
        this.daoA.storeEntity(new EntityA());
        assertEquals(2, this.daoA.getAllEntities().size());
    }

    @Test
    public void testRollback() {
        // Each test should be executed in isolation and rolled back when done
        // Yes, this relies on the order of execution of tests...
        assertEquals(0, this.daoA.getAllEntities().size());
    }

    @Test
    @ExpectedException(Exception.class)
    public void testExceptionPropagation() throws Exception {
        // Still a blank slate?
        assertEquals(0, this.daoA.getAllEntities().size());

        // Make sure our @Transactional handler doesn't swallow exceptions
        this.daoA.throwException();
    }

}
