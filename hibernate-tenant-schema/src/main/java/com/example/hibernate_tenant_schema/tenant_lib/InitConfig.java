package com.example.hibernate_tenant_schema.tenant_lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class InitConfig {
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> applicationListener(TenantManagementService tenantManagementService) {
		return event -> {
			tenantManagementService.init();
		};
	}
}
