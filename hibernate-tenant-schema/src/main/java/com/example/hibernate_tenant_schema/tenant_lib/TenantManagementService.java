package com.example.hibernate_tenant_schema.tenant_lib;

import com.example.hibernate_tenant_schema.tenant_lib.domain.model.Tenant;
import com.example.hibernate_tenant_schema.tenant_lib.domain.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TenantManagementService {
	private final DataSource dataSource;
	private final TenantRepository tenantRepository;
	
	private static final String VALID_SCHEMA_NAME_REGEXP = "[A-Za-z0-9_]*";
	
	public void init() {
		ClassicConfiguration classicConfiguration = new ClassicConfiguration();
		classicConfiguration.setDataSource(dataSource);
		classicConfiguration.setLocations(new Location("tenant_db"));
		Flyway flyway = new Flyway(classicConfiguration);
		flyway.migrate();
	}
	
	public Tenant create(Tenant tenant) {
		return createTenant(tenant.getId(), tenant.getSchema());
	}
	
	public void delete(String tenantId) {
		if (!tenantRepository.existsById(tenantId)) {
			throw new EmptyResultDataAccessException("No found tenant with id: " + tenantId, 1);
		}
		try {
			dropSchema(tenantId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		tenantRepository.deleteById(tenantId);
	}
	
	public List<Tenant> getAll() {
		return tenantRepository.findAll();
	}
	
	public Page<Tenant> search(String filter, Pageable pageable) {
		return tenantRepository.findAll(pageable);
	}
	
	private Tenant getById(String tenantId) {
		return tenantRepository.findById(tenantId)
				.orElseThrow(() -> new EmptyResultDataAccessException("No found tenant with id: " + tenantId, 1));
	}
	
	public Tenant get(String tenantId) {
		return getById(tenantId);
	}
	
	public Tenant createTenant(String tenantId, String schema) {
		boolean alreadyExist = tenantRepository.existsById(tenantId);
		
		if (alreadyExist) {
			return null;
		}
		
		// Verify schema string to prevent SQL injection
		if (!schema.matches(VALID_SCHEMA_NAME_REGEXP)) {
			throw new TenantCreationException("Schema name not valid -> " + schema);
		}
		
		try {
			runFlyway(schema);
		} catch (Exception e) {
			throw new TenantCreationException("Error when populating schema: ", e);
		}
		Tenant tenant = Tenant.builder()
				.id(tenantId)
				.schema(schema)
				.build();
		return tenantRepository.save(tenant);
	}
	
	private void runFlyway(String schema) throws Exception {
		ClassicConfiguration classicConfiguration = new ClassicConfiguration();
		classicConfiguration.setDataSource(dataSource);
		classicConfiguration.setSchemas(new String[] {schema});
		classicConfiguration.setCreateSchemas(true);
		classicConfiguration.setDefaultSchema(schema);
		Flyway flyway = new Flyway(classicConfiguration);
		
		flyway.migrate();
//		FlywayMigrationStrategy migrationStrategy = flyway1 -> {
//			log.info("FlywayAutoConfiguration:FlywayMigrationStrategy - Start to migrate ...");
////			flyway1.baseline();
////			flyway1.repair();
//			flyway1.migrate();
//		};
//		migrationStrategy.migrate(flyway);
	}
	
	private void dropSchema(String schema) throws SQLException {
		dataSource.getConnection().setSchema(schema);
		dataSource.getConnection().prepareStatement(String.format("DROP SCHEMA %s cascade", schema)).execute();
	}
}
