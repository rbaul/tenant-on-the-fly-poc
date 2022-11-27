package com.example.hibernate_tenant_schema.tenant.config;

import com.example.hibernate_tenant_schema.tenant.domain.model.Customer;
import com.example.hibernate_tenant_schema.tenant.domain.repository.CustomerRepository;
import com.example.hibernate_tenant_schema.tenant_lib.TenantConfig;
import com.example.hibernate_tenant_schema.tenant_lib.TenantContext;
import com.example.hibernate_tenant_schema.tenant_lib.TenantManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Import(TenantConfig.class)
@Configuration
public class InitLoader {
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> initialLoader(CustomerRepository customerRepository, TenantManagementService tenantManagementService) {
		return event -> {
			for (int i = 1; i < 10; i++) {
				String tenantName = "TENANT_" + i;
				tenantManagementService.createTenant(tenantName, tenantName);
				
				List<Customer> customers = new ArrayList<>();
				for (int j = 0; j < 10; j++) {
					customers.add(Customer.builder()
							.name(RandomStringUtils.randomAlphabetic(3))
							.address(RandomStringUtils.randomAlphabetic(3))
							.familyName(RandomStringUtils.randomAlphabetic(3)).build());
				}
				TenantContext.setCurrentTenant(tenantName);
				customerRepository.saveAll(customers);
				TenantContext.clear();
			}

		};
	}
}
