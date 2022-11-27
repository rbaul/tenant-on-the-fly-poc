package com.example.mongo_tenant_schema_reactive.app.domain.repository;

import com.example.mongo_tenant_schema_reactive.app.domain.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
	
	Optional<Customer> findByName(String name);
	
//	Flux<Customer> findAll(Pageable pageable);
}
