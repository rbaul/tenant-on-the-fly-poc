package com.example.mongo_tenant_schema.tenant_lib;

import com.example.mongo_tenant_schema.tenant_lib.domain.model.Tenant;
import com.example.mongo_tenant_schema.tenant_lib.domain.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TenantManagementService {
	private final TenantRepository tenantRepository;
	private final MongoTemplate mongoTemplate;
	
	private static final String VALID_DATABASE_NAME_REGEXP = "[^/\\\\.$\"\\s]+";
	
	public Tenant create(Tenant tenant) {
		return createTenant(tenant.getId(), tenant.getDatabaseName());
	}
	
	public void delete(String tenantId) {
		if (!tenantRepository.existsById(tenantId)) {
			throw new EmptyResultDataAccessException("No found tenant with id: " + tenantId, 1);
		}
		dropTenantDatabase(tenantId);
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
	
	public Tenant createTenant(String tenantId, String databaseName) {
		boolean alreadyExist = tenantRepository.existsById(tenantId);
		
		if (alreadyExist) {
			return null;
		}
		
		// Verify database name string
		if (!databaseName.matches(VALID_DATABASE_NAME_REGEXP)) {
			throw new TenantCreationException("Database name must not contain slashes, dots, spaces, quotes, or dollar signs! -> " + databaseName);
		}
		
		try {
			initTenantDatabase(databaseName);
		} catch (Exception e) {
			throw new TenantCreationException("Error when populating database name: ", e);
		}
		Tenant tenant = Tenant.builder()
				.id(tenantId)
				.databaseName(databaseName)
				.build();
		return tenantRepository.save(tenant);
	}
	
	private void initTenantDatabase(String databaseName) {
		// Create all relevant document (Mongock)
//		mongoTemplate
	}
	
	private void dropTenantDatabase(String database) {
		TenantContext.setCurrentTenant(database);
		mongoTemplate.getDb().drop();
		TenantContext.clear();
	}
	
}
