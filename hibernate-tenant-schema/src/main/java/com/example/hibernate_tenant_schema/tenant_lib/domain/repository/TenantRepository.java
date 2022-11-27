package com.example.hibernate_tenant_schema.tenant_lib.domain.repository;

import com.example.hibernate_tenant_schema.tenant_lib.domain.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, String> {
	
}
