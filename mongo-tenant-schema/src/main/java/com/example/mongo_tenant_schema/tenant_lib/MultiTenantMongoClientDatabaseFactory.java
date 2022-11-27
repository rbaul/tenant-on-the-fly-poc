package com.example.mongo_tenant_schema.tenant_lib;

import com.mongodb.client.MongoClient;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.util.StringUtils;

public class MultiTenantMongoClientDatabaseFactory extends SimpleMongoClientDatabaseFactory {
	
	public MultiTenantMongoClientDatabaseFactory(MongoClient mongoClient, String databaseName) {
		super(mongoClient, databaseName);
	}
	
	@Override
	protected String getDefaultDatabaseName() {
		String tenant = TenantContext.getCurrentTenant();
		return StringUtils.hasText(tenant) ?
				String.format("%s-%s", super.getDefaultDatabaseName(), tenant) : super.getDefaultDatabaseName();
	}
	
}
