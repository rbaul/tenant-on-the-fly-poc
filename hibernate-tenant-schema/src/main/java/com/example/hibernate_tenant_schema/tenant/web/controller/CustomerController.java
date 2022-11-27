package com.example.hibernate_tenant_schema.tenant.web.controller;

import com.example.hibernate_tenant_schema.tenant.service.CustomerService;
import com.example.hibernate_tenant_schema.tenant.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@Autowired
	private EntityManager entityManager;
	
	@GetMapping("{customerId}")
	public com.example.hibernate_tenant_schema.tenant.web.dto.CustomerDto getCustomer(@PathVariable long customerId) {
		return customerService.get(customerId);
	}
	
	@PostMapping
	public com.example.hibernate_tenant_schema.tenant.web.dto.CustomerDto create(
			@Validated @RequestBody com.example.hibernate_tenant_schema.tenant.web.dto.CustomerDto customerDto) {
		return customerService.create(customerDto);
	}
	
	@PutMapping("{customerId}")
	public com.example.hibernate_tenant_schema.tenant.web.dto.CustomerDto update(@PathVariable long customerId,
																				 @Validated @RequestBody com.example.hibernate_tenant_schema.tenant.web.dto.CustomerDto customerDto) {
		return customerService.update(customerId, customerDto);
	}
	
	@DeleteMapping("{customerId}")
	public void delete(@PathVariable long productId) {
		customerService.delete(productId);
	}
	
	@GetMapping("search")
	public Page<CustomerDto> fetch(@RequestParam(required = false) String filter, @PageableDefault Pageable pageable) {
		return customerService.search(filter, pageable);
	}
}
