package com.example.mongo_tenant_attribute.tenant_lib;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Getter
@Setter
@ToString
@Component
@RequestScope
public class TenantResolver {
	public static final String TENANT_HEADER = "X-TenantID";
	
	@Autowired
	public TenantResolver(HttpServletRequest request) {
		String tenantsHeader = request.getHeader(TenantResolver.TENANT_HEADER);
		log.trace("****Received tenant IDs in header: " + tenantsHeader);
		if (StringUtils.hasText(tenantsHeader)) {
			TenantContext.setCurrentTenant(tenantsHeader);
		} else {
			TenantContext.clear();
		}
	}
	
}