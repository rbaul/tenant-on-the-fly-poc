package com.example.hibernet_attribute.tenant_lib;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Map;

//@Component
class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
	
//	private String currentTenant;
//
//	public void setCurrentTenant(String tenant) {
//		currentTenant = tenant;
//	}
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		return TenantContext.getCurrentTenant();
	}
	
	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
	}
	
	// empty overrides skipped for brevity
}