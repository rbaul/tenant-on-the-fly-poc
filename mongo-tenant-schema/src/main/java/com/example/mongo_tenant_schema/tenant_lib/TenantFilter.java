package com.example.mongo_tenant_schema.tenant_lib;

import com.example.mongo_tenant_schema.tenant_lib.domain.model.Tenant;
import com.example.mongo_tenant_schema.tenant_lib.domain.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
@Order(1)
class TenantFilter implements Filter {
	
	public static final String TENANT_HEADER = "X-TenantID";
	
	private final TenantRepository tenantRepository;
	

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
	
		String tenantsHeader = ((HttpServletRequest) request).getHeader(TENANT_HEADER);
		log.info("****Received tenant IDs in header: " + tenantsHeader);
		if (StringUtils.hasText(tenantsHeader)) {
			TenantContext.setCurrentTenant(tenantsHeader);
			Optional<Tenant> tenantOptional = tenantRepository.findById(tenantsHeader);
			tenantOptional.ifPresent(tenant -> {
				TenantContext.setCurrentTenant(tenant.getDatabaseName());
			});
			
			// TODO: if schema not exist create
			
		}
	
		try {
			chain.doFilter(request, response);
		} finally {
			TenantContext.clear();
		}

    }
}