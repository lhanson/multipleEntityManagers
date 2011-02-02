package example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import example.dao.EntityDaoA;
import example.dao.EntityDaoB;
import example.om.EntityA;
import example.om.EntityB;

/**
 * A sample app demonstrating custom support for @Transactional annotations
 * in Spring 2.x with multiple data sources.
 *
 * Note that unit tests are handled by using separate contexts for each
 * DAO/data source and therefore use the standard annotation-drive Spring
 * support.
 *
 * @author Lyle Hanson
 */
public class App {
    private static final String APP_CONTEXT = "classpath:applicationContext.xml";
    
    public static void main(String[] args)  {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT);

        EntityDaoA daoA = (EntityDaoA) ctx.getBean("entityDaoA", EntityDaoA.class);
        EntityDaoB daoB = (EntityDaoB) ctx.getBean("entityDaoB", EntityDaoB.class);

        if ((daoA.getAllEntities().size() != 0) ||
            (daoB.getAllEntities().size() != 0)) {
            throw new IllegalStateException("Expected no entities yet");
        }

        // Create and store EntityA
        daoA.storeEntity(new EntityA());
        daoA.storeEntity(new EntityA());
        if (daoA.getAllEntities().size() != 2){
            throw new IllegalStateException("Expected to see two entities");
        }

        // Create and store EntityB
        daoB.storeEntity(new EntityB());
        daoB.storeEntity(new EntityB());
        if (daoB.getAllEntities().size() != 2){
            throw new IllegalStateException("Expected to see two entities");
        }

        System.out.println("Looks like everything works!");
    }

}
