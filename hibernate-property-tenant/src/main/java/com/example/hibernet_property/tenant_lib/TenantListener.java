package com.example.hibernet_property.tenant_lib;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Slf4j
public class TenantListener {
	
	@PrePersist
	@PreUpdate
	@PreRemove
	public void prePresistEntity(AbstractBaseEntity baseEntity) {
		final String currentTenant = TenantContext.getCurrentTenant();
		baseEntity.setTenantId(currentTenant);
	}
	
	@AfterReturning
	public void sss() {
		log.info("AfterReturning");
	}
}
