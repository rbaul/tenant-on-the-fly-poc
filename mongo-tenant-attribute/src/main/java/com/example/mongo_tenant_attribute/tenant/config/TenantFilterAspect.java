package com.example.mongo_tenant_attribute.tenant.config;

import com.example.mongo_tenant_attribute.tenant.web.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class TenantFilterAspect {

//    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
//    public void openSession() {
//    }

//    @AfterReturning(pointcut = "openSession()", returning = "session")
//	@AfterReturning(pointcut = "execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))", returning = "session")
//    public void afterOpenSession(Session session) {
////        if (Session.class.isInstance(session)) {
//            final String tenantId = TenantContext.getCurrentTenant();
//            if (tenantId != null) {
//                org.hibernate.Filter filter = ((Session) session).enableFilter(AbstractBaseEntity.FILTER_NAME);
//                filter.setParameter(AbstractBaseEntity.FILTER_ARGUMENT_NAME, tenantId);
//            }
////        }
//    }
	
//	@AfterReturning(pointcut = "execution (* com.example.tenantfilter.service.CustomerService.search(..))", returning = "customerDtos")
//	public void afterSearch(Page<CustomerDto> customerDtos) {
//		log.info("Return {}", customerDtos);
//
//	}
//	@AfterReturning(pointcut = "execution (* org.springframework.orm.jpa.EntityManagerFactoryAccessor.createEntityManager())", returning = "retVal")
//	public void afterSearch(JoinPoint jp, Object retVal) {
//		log.info("Return {}", retVal);
//
//	}

}