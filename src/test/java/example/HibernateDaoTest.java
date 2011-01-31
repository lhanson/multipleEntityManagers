package example;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.dao.EntityDaoA;
import example.om.EntityA;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml"})
public class HibernateDaoTest {
    @Autowired
    private EntityDaoA dao;

    @Test
    public void testStoreAndRetrieveEntity() {
        assertNotNull(this.dao);
        assertNull(this.dao.getEntityById(1L));

        EntityA entity = new EntityA();
        entity.setAuthorId(1);
        entity.setText("Text from author 1");
        this.dao.storeEntity(entity);

        assertEquals(1, this.dao.getAllEntities().size());
    }

}
