package com.example.mongo_tenant_schema_reactive.app.web.controller;

import com.example.mongo_tenant_schema_reactive.app.service.CustomerService;
import com.example.mongo_tenant_schema_reactive.app.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping("{customerId}")
	public Mono<CustomerDto> getCustomer(@PathVariable String customerId) {
		return customerService.get(customerId);
	}
	
	@PostMapping
	public Mono<CustomerDto> create(
			@Validated @RequestBody CustomerDto customerDto) {
		return customerService.create(customerDto);
	}
	
	@PutMapping("{customerId}")
	public Mono<CustomerDto> update(@PathVariable String customerId,
							  @Validated @RequestBody CustomerDto customerDto) {
		return customerService.update(customerId, customerDto);
	}
	
	@DeleteMapping("{customerId}")
	public void delete(@PathVariable String productId) {
		customerService.delete(productId);
	}
	
	@GetMapping("search")
	public Flux<CustomerDto> fetch(@RequestParam(required = false) String filter, @PageableDefault Pageable pageable) {
		return customerService.search(filter, pageable);
	}
}
