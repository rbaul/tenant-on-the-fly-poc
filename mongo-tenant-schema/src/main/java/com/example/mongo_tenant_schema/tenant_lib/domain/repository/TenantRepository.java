package com.example.mongo_tenant_schema.tenant_lib.domain.repository;

import com.example.mongo_tenant_schema.tenant_lib.domain.model.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TenantRepository extends MongoRepository<Tenant, String> {
	
}
