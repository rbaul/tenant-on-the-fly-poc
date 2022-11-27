package com.example.mongo_tenant_schema_reactive.tenant_lib;

import com.example.mongo_tenant_schema_reactive.tenant_lib.domain.model.Tenant;
import com.example.mongo_tenant_schema_reactive.tenant_lib.domain.repository.TenantRepository;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class TenantManagementService {
	private final TenantRepository tenantRepository;
	private final ReactiveMongoTemplate reactiveMongoTemplate;
	
	private static final String VALID_DATABASE_NAME_REGEXP = "[^/\\\\.$\"\\s]+";
	
	public Mono<Tenant> create(Tenant tenant) {
		return createTenant(tenant.getId(), tenant.getDatabaseName());
	}
	
	public void delete(String tenantId) {
//		if (!tenantRepository.existsById(tenantId)) {
//			throw new EmptyResultDataAccessException("No found tenant with id: " + tenantId, 1);
//		}
		tenantRepository.deleteById(tenantId)
				.doFirst(() -> dropTenantDatabase(tenantId));
	}
	
	public Flux<Tenant> getAll() {
		return tenantRepository.findAll();
	}
	
	public Flux<Tenant> search(String filter, Pageable pageable) {
		return tenantRepository.findAll();
	}
	
	private Mono<Tenant> getById(String tenantId) {
		return tenantRepository.findById(tenantId);
	}
	
	public Mono<Tenant> get(String tenantId) {
		return getById(tenantId);
	}
	
	public Mono<Tenant> createTenant(String tenantId, String databaseName) {
		boolean result = tenantRepository.existsById(tenantId).block();
		if (!result) {
			
			// Verify database name
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
		
		return Mono.empty();
		
//		tenantRepository.existsById(tenantId)
//				.map(alreadyExist -> {
//					if (!alreadyExist) {
//
//						// Verify database name
//						if (!databaseName.matches(VALID_DATABASE_NAME_REGEXP)) {
//							throw new TenantCreationException("Database name must not contain slashes, dots, spaces, quotes, or dollar signs! -> " + databaseName);
//						}
//
//						try {
//							initTenantDatabase(databaseName);
//						} catch (Exception e) {
//							throw new TenantCreationException("Error when populating database name: ", e);
//						}
//						Tenant tenant = Tenant.builder()
//								.id(tenantId)
//								.databaseName(databaseName)
//								.build();
//
//						tenantRepository.save(tenant).block();
//						return tenant;
//					}
//					return null;
//		}).or(Mono.empty()).subscribe();
//		return Mono.empty();
	}
	
	private void initTenantDatabase(String databaseName) {
		// Create all relevant document (Mongock)
//		mongoTemplate
	}
	
	private void dropTenantDatabase(String database) {
		TenantContext.setCurrentTenant(database);
		reactiveMongoTemplate.getMongoDatabase().doOnNext(MongoDatabase::drop).block();
		TenantContext.clear();
	}
	
}
