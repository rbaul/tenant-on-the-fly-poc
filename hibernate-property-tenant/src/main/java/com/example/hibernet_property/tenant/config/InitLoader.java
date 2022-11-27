package com.example.hibernet_property.tenant.config;

import com.example.hibernet_property.tenant.domain.model.Customer;
import com.example.hibernet_property.tenant.domain.repository.CustomerRepository;
import com.example.hibernet_property.tenant_lib.TenantConfig;
import com.example.hibernet_property.tenant_lib.TenantContext;
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
	public ApplicationListener<ApplicationReadyEvent> initialLoader(CustomerRepository customerRepository) {
		return event -> {
			for (int i = 1; i < 5; i++) {
				List<Customer> customers = new ArrayList<>();
				for (int j = 0; j < 10; j++) {
					customers.add(Customer.builder()
							.name(RandomStringUtils.randomAlphabetic(3)).build());
				}
				customerRepository.saveAll(customers);
			}
		};
	}
}
