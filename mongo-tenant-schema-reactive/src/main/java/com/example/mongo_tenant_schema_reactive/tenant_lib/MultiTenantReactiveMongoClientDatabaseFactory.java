package com.example.mongo_tenant_schema_reactive.tenant_lib;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class MultiTenantReactiveMongoClientDatabaseFactory extends SimpleReactiveMongoDatabaseFactory {
	
	public MultiTenantReactiveMongoClientDatabaseFactory(MongoClient mongoClient, String databaseName) {
		super(mongoClient, databaseName);
	}
	
	@Override
	public Mono<MongoDatabase> getMongoDatabase(String dbName) throws DataAccessException {
		String tenant = TenantContext.getCurrentTenant();
		String databaseName = StringUtils.hasText(tenant) ?
				String.format("%s-%s", dbName, tenant) : dbName;
		return super.getMongoDatabase(databaseName);
	}
	
	
}
