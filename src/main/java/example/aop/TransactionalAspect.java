package example.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.SpringVersion;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import example.dao.EntityManagedDao;

@Aspect
public class TransactionalAspect implements BeanPostProcessor, ApplicationListener {
    private final Log logger = LogFactory.getLog(getClass());
    private Set<EntityManagedDao> entityManagedDaos = new HashSet<EntityManagedDao>();
    private Map<EntityManagedDao, TransactionTemplate> transactionTemplatesByDao =
        new HashMap<EntityManagedDao, TransactionTemplate>();

    public TransactionalAspect() {
        if ('2' != SpringVersion.getVersion().charAt(0)) {
            throw new IllegalStateException("This aspect compensates for Spring 2.x's " +
                "inability to nicely deal with multiple data sources and annotation-based " +
                "transactions. Spring 3+ apps should use its support for this directly.");
        }
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object transactionalAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        if (!(pjp.getTarget() instanceof EntityManagedDao)) {
            throw new IllegalStateException("Cannot manage transactions on non-EntityManagedDao interfaces");
        }
        
        final Object retval;
        EntityManagedDao dao = (EntityManagedDao) pjp.getTarget();
        if (this.transactionTemplatesByDao.containsKey(dao)) {
            TransactionTemplate transactionTemplate = this.transactionTemplatesByDao.get(dao);
            final EntityManager em = dao.getEntityManager();
            logger.debug("Executing @Around joinpoint in a TransactionTemplate");
            retval = transactionTemplate.execute(new TransactionCallback() {
                @Override
                public Object doInTransaction(TransactionStatus ts) {
                    logger.debug("Joining EntityManager with outer transaction");
                    em.joinTransaction(); // Join the template-created transaction
                    Object retval = null;
                    try {
                        retval = pjp.proceed();
                    }
                    catch (Throwable ex) {
                        logger.error("Error invoking transactional method: " + ex.getMessage());
                    }
                    return retval;
                }
            });
            logger.debug("Finished TransactionTemplate");
        }
        else {
            throw new IllegalStateException("Cannot support @Transactional on " +
                "beans which don't implement " + EntityManagedDao.class);
        }

        return retval;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Store all of the EntityManagedDaos by name
        if (bean instanceof EntityManagedDao) {
            if (AopUtils.isAopProxy(bean)) {
                // We need an unproxied reference, otherwise we can't match it
                // against the target in the AOP advice.
                throw new FatalBeanException(
                    "Transactional advice depends on an unproxied reference to a \"" +
                    EntityManagedDao.class + "\", instead got \"" + bean.getClass() + "\"");
            }
            this.entityManagedDaos.add((EntityManagedDao) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if ( (event instanceof ContextStartedEvent) ||
             (event instanceof ContextRefreshedEvent)) {
            // We've stored references to all of our EntityManagedDaos, now
            // create transaction templates for each of them.
            for (EntityManagedDao dao : this.entityManagedDaos) {
                EntityManagerFactory emf = dao.getEntityManagerFactory();
                TransactionTemplate tt =
                    new TransactionTemplate(new JpaTransactionManager(emf));
                this.transactionTemplatesByDao.put(dao, tt);
            }
        }
    }

}
