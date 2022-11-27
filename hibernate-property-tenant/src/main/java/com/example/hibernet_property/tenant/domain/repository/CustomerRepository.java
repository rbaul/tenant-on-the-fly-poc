package com.example.hibernet_property.tenant.domain.repository;

import com.example.hibernet_property.tenant.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findByName(String name);
	
	List<Customer> findByTenantId(String tenantId);
}
