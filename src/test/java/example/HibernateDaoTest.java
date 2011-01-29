package example;

import example.dao.EntityDao;
import static org.junit.Assert.*;
import org.junit.Test;

import example.om.EntityA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations={"testApplicationContext.xml"})
public class HibernateDaoTest /*extends AbstractDependencyInjectionSpringContextTests */{
    @Autowired
    private EntityDao dao;

    @Test
    public void testStoreAndRetrieveEntity() {
        EntityA entity = new EntityA();
        assertNotNull(this.dao);
        assertNotNull(entity);
    }

}
