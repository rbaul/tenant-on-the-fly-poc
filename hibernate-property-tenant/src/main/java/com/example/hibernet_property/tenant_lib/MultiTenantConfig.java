package com.example.hibernet_property.tenant_lib;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConfig extends AbstractMultiTenantConnectionProvider {
	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return null;
	}
	
	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		return null;
	}
}
