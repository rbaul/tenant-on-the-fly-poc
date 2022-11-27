package com.example.hibernate_tenant_schema.tenant.domain.repository;

import com.example.hibernate_tenant_schema.tenant.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<com.example.hibernate_tenant_schema.tenant.domain.model.Customer, Long> {
	
	Optional<Customer> findByName(String name);
	
}
