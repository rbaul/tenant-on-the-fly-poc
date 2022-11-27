package com.example.hibernate_tenant_schema.tenant_lib;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Component
public class SchemaConfigurableMultiTenantConnectionProvider
		implements MultiTenantConnectionProvider, HibernatePropertiesCustomizer {
	
	@Autowired
	private HibernateProperties hibernateProperties;
	
	@Autowired
	DataSource dataSource;

//	@Override
//	protected DataSource selectAnyDataSource() {
//		return dataSource;
//	}
//
//	@Override
//	protected DataSource selectDataSource(String tenantIdentifier) {
//		Connection connection = dataSource.getConnection();
//		connection.setSchema(tenantIdentifier);
//		return dataSource;
//	}
	
	@Override
	public Connection getAnyConnection() throws SQLException {
		return getConnection("PUBLIC");
	}
	
	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}
	
	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		Connection connection = dataSource.getConnection();
		if (!tenantIdentifier.equals(TenantIdentifierResolver.defaultTenant)) {
			connection.setSchema(tenantIdentifier);
		}
		return connection;
	}
	
	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		connection.setSchema("PUBLIC");
		connection.close();
	}
	
	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
		hibernateProperties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
//		hibernateProperties.put(AvailableSettings.HBM2DDL_CREATE_SCHEMAS, true);
	}
	
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}
	
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}
}