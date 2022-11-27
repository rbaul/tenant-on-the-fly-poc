package com.example.mongo_tenant_attribute.tenant.service;

import com.example.mongo_tenant_attribute.tenant.domain.model.Customer;
import com.example.mongo_tenant_attribute.tenant.domain.repository.CustomerRepository;
import com.example.mongo_tenant_attribute.tenant.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	
	private final ModelMapper modelMapper;
	
	public CustomerDto get(String customerId) {
		return modelMapper.map(getById(customerId), CustomerDto.class);
	}
	
	public CustomerDto create(CustomerDto productDto) {
		Customer customer = customerRepository.save(modelMapper.map(productDto, Customer.class));
		return modelMapper.map(customer, CustomerDto.class);
	}
	
	public CustomerDto update(String customerId, CustomerDto customerDto) {
		Customer customer = getById(customerId);
		customer.setName(customerDto.getName());
		customer.setFamilyName(customerDto.getFamilyName());
		customer.setAddress(customerDto.getAddress());
		return modelMapper.map(customer, CustomerDto.class);
	}
	
	public void delete(String customerId) {
		if (!customerRepository.existsById(customerId)) {
			throw new EmptyResultDataAccessException("No found customer with id: " + customerId, 1);
		}
		customerRepository.deleteById(customerId);
	}
	
	public List<CustomerDto> getAll() {
		return customerRepository.findAll().stream()
				.map(customer -> modelMapper.map(customer, CustomerDto.class))
				.collect(Collectors.toList());
	}
	
	public Page<CustomerDto> search(String filter, Pageable pageable) {
		return customerRepository.findAll(pageable)
				.map(customer -> modelMapper.map(customer, CustomerDto.class));
	}
	
	private Customer getById(String customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new EmptyResultDataAccessException("No found customer with id: " + customerId, 1));
	}
}
