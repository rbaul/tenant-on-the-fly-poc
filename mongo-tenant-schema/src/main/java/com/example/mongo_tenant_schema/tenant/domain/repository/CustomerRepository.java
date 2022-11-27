package com.example.mongo_tenant_schema.tenant.domain.repository;

import com.example.mongo_tenant_schema.tenant.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	
	Optional<Customer> findByName(String name);
	
}
