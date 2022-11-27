package com.example.mongo_tenant_schema_reactive.tenant_lib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
@Order(1)
class TenantFilter implements WebFilter {
	
	public static final String TENANT_HEADER = "X-TenantID";

//	private final TenantRepository tenantRepository;
	
	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange,
							 WebFilterChain webFilterChain) {
		ServerHttpRequest request = serverWebExchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		List<String> tenantHeader = headers.get(TENANT_HEADER);
		log.info("****Received tenant IDs in header: " + tenantHeader);
		if (!CollectionUtils.isEmpty(tenantHeader)) {
			TenantContext.setCurrentTenant(tenantHeader.get(0));
//			Optional<Tenant> tenantOptional = tenantRepository.findById(tenantsHeader);
//			tenantOptional.ifPresent(tenant -> {
//				TenantContext.setCurrentTenant(tenant.getDatabaseName());
//			});
			
			// TODO: if schema not exist create
			
		}
		return webFilterChain.filter(serverWebExchange).doFinally(signalType -> TenantContext.clear());
	}
}