package com.example.mongo_tenant_schema.tenant_lib;

import com.example.mongo_tenant_schema.tenant_lib.domain.model.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/tenants")
public class TenantController {
	
	private final TenantManagementService tenantManagementService;
	
	@GetMapping("{tenantId}")
	public Tenant getCustomer(@PathVariable String tenantId) {
		return tenantManagementService.get(tenantId);
	}
	
	@PostMapping
	public Tenant create(
			@Validated @RequestBody Tenant customerDto) {
		return tenantManagementService.create(customerDto);
	}
	
	@DeleteMapping("{tenantId}")
	public void delete(@PathVariable String tenantId) {
		tenantManagementService.delete(tenantId);
	}
	
	@GetMapping("search")
	public Page<Tenant> fetch(@RequestParam(required = false) String filter, @PageableDefault Pageable pageable) {
		return tenantManagementService.search(filter, pageable);
	}
}
