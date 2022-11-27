package com.example.hibernet_attribute.tenant_lib;

import com.example.hibernet_attribute.tenant.web.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@Slf4j
@Aspect
public class TenantFilterAspect {
	
	@Autowired
	private EntityManager entityManager;

//    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
//    public void openSession() {
//    }

//    @AfterReturning(pointcut = "openSession()", returning = "session")
	@AfterReturning(pointcut = "execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))", returning = "session")
    public void afterOpenSession(Session session) {
//        if (Session.class.isInstance(session)) {
            final String tenantId = TenantContext.getCurrentTenant();
            if (tenantId != null) {
                org.hibernate.Filter filter = ((Session) session).enableFilter(AbstractBaseEntity.FILTER_NAME);
                filter.setParameter(AbstractBaseEntity.FILTER_ARGUMENT_NAME, tenantId);
            }
//        }
    }
	
	@Around("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
	public Object aroundOpenSession(ProceedingJoinPoint pjp) throws Throwable {
		log.info("Return {}", pjp);
		return pjp.proceed();
	}
	
	@AfterReturning(pointcut = "execution (* com.example.tenant.service.CustomerService.search(..))", returning = "customerDtos")
	public void afterSearch(Page<CustomerDto> customerDtos) {
		log.info("Return {}", customerDtos);
	}
	
	@Before("within(@org.springframework.web.bind.annotation.RestController *) && execution(public * *(..))")
	public void before() {
		log.info("Before");
//		final String currentTenant = TenantContext.getCurrentTenant();
//		if (currentTenant != null) {
//			Session session = entityManager.unwrap(Session.class);
//			org.hibernate.Filter filter1 = session.enableFilter(AbstractBaseEntity.FILTER_NAME);
//
//			filter1.setParameter(AbstractBaseEntity.FILTER_ARGUMENT_NAME, currentTenant);
//			filter1.validate();
//		}
	}
	
	@AfterReturning(pointcut = "execution (* org.springframework.orm.jpa.EntityManagerFactoryAccessor.createEntityManager(..))", returning = "retVal")
	public void afterSearch(JoinPoint jp, Object retVal) {
		log.info("Return {}", retVal);
		
	}

}