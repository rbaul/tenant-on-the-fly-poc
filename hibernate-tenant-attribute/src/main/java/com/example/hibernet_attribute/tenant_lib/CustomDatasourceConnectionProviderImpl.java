package com.example.hibernet_attribute.tenant_lib;

import org.hibernate.Session;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

//@Component
public class CustomDatasourceConnectionProviderImpl  implements ConnectionProvider, HibernatePropertiesCustomizer {
	
//	@Autowired
//	@Lazy
//	private EntityManager entityManager;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public Connection getConnection() throws SQLException {
//		final String currentTenant = TenantContext.getCurrentTenant();
//		if (currentTenant != null) {
//			Session session = entityManager.unwrap(Session.class);
//			org.hibernate.Filter filter1 = session.enableFilter(AbstractBaseEntity.FILTER_NAME);
//
//			filter1.setParameter(AbstractBaseEntity.FILTER_ARGUMENT_NAME, currentTenant);
//			filter1.validate();
//		}
//		Connection connection = super.getConnection();
		
		return dataSource.getConnection();
	}
	
	@Override
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
	
	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.JPA_PERSISTENCE_PROVIDER, this);
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
