package com.example.hibernet_attribute.tenant_lib;

import org.hibernate.EmptyInterceptor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernetSessionInterceptor extends EmptyInterceptor implements HibernatePropertiesCustomizer {
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
//		hibernateProperties.put(AvailableSettings.INTERCEPTOR, this);
		hibernateProperties.put(AvailableSettings.SESSION_SCOPED_INTERCEPTOR, HibernetSessionInterceptor.class.getName());
	}
	
	@Override
	public String onPrepareStatement(String sql) {
		return super.onPrepareStatement(sql);
	}
	
}
