package com.example.mongo_tenant_schema_reactive.app.service;

import com.example.mongo_tenant_schema_reactive.app.domain.model.Customer;
import com.example.mongo_tenant_schema_reactive.app.domain.repository.CustomerRepository;
import com.example.mongo_tenant_schema_reactive.app.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	
	private final ModelMapper modelMapper;
	
	public Mono<CustomerDto> get(String customerId) {
		return getById(customerId)
				.map(customer -> modelMapper.map(customer, CustomerDto.class));
	}
	
	public Mono<CustomerDto> create(CustomerDto customerDto) {
		return customerRepository.save(modelMapper.map(customerDto, Customer.class))
				.map(customer -> modelMapper.map(customer, CustomerDto.class));
	}
	
	public Mono<CustomerDto> update(String customerId, CustomerDto customerDto) {
		return getById(customerId).map(customer -> {
			customer.setName(customerDto.getName());
			customer.setFamilyName(customerDto.getFamilyName());
			customer.setAddress(customerDto.getAddress());
			customerRepository.save(customer);
			return modelMapper.map(customer, CustomerDto.class);
		});
	}
	
	public void delete(String customerId) {
		customerRepository.deleteById(customerId);
	}
	
	public Flux<CustomerDto> getAll() {
		return customerRepository.findAll()
				.map(customer -> modelMapper.map(customer, CustomerDto.class));
	}
	
	public Flux<CustomerDto> search(String filter, Pageable pageable) {
		return customerRepository.findAll()
				.map(customer -> modelMapper.map(customer, CustomerDto.class));
	}
	
	private Mono<Customer> getById(String customerId) {
		return customerRepository.findById(customerId);
	}
}
