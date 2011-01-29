package example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.dao.EntityDao;
import example.om.EntityA;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml"})
public class HibernateDaoTest {
    @Autowired
    private EntityDao dao;

    @Test
    public void testStoreAndRetrieveEntity() {
        EntityA entity = new EntityA();
        assertNotNull(this.dao);
        assertNotNull(entity);
    }

}
