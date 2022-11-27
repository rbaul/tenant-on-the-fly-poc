package com.example.hibernet_attribute.tenant_lib;

import org.hibernate.BaseSessionEventListener;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomSessionEventListener extends BaseSessionEventListener implements HibernatePropertiesCustomizer {
	@Override
	public void jdbcPrepareStatementStart() {
		super.jdbcPrepareStatementStart();
	}
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.AUTO_SESSION_EVENTS_LISTENER, CustomSessionEventListener.class.getName());
	}
}
