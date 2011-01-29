package example;

import example.dao.EntityDao;
import static org.junit.Assert.*;
import org.junit.Test;

import example.om.EntityA;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//TODO: this doesn't ever seem to work!
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml"})
public class HibernateDaoTest extends AbstractDependencyInjectionSpringContextTests {
    @Autowired
    private EntityDao dao;

//    @Override
//    protected String[] getConfigLocations() {
//        return new String[] { "testApplicationContext.xml" };
//    }

    @Test
    public void testStoreAndRetrieveEntity() {
        EntityA entity = new EntityA();
        assertNotNull(this.dao);
        System.out.println("********DAO: " + this.dao);
        assertNotNull(entity);
    }

}
