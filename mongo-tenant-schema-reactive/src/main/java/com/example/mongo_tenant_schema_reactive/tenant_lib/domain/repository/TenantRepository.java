package com.example.mongo_tenant_schema_reactive.tenant_lib.domain.repository;

import com.example.mongo_tenant_schema_reactive.tenant_lib.domain.model.Tenant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TenantRepository extends ReactiveMongoRepository<Tenant, String> {
//	Flux<Tenant> findAll(Pageable pageable);
}
