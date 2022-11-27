package com.example.hibernet_property.tenant_lib;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
	
	private String defaultTenant = "unknown";
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantContext.getCurrentTenant();
		return StringUtils.hasText(tenant) ? tenant : defaultTenant;
	}
	
	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
		hibernateProperties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.DISCRIMINATOR);
	}
	
}