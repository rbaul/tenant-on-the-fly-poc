package com.example.hibernate_tenant_schema.tenant_lib;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@Slf4j
//@Component
class SchemaMultiTenantConnectionProvider
  extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements HibernatePropertiesCustomizer {
	
	@Autowired
	DataSource dataSource;
	@Override
	protected DataSource selectAnyDataSource() {
		return dataSource;
	}
	
	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		try {
			Connection connection = dataSource.getConnection();
			connection.setSchema(tenantIdentifier);
		} catch (Exception e) {
			log.error("Failed switch schema", e);
		}
		
		return null;
	}
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
		hibernateProperties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
	}
}