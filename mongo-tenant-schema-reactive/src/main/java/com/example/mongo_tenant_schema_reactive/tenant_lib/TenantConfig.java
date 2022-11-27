package com.example.mongo_tenant_schema_reactive.tenant_lib;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

//@ConditionalOnClass({MongoClient.class, ReactiveMongoTemplate.class})
//@ConditionalOnBean(MongoClient.class)
@AutoConfigurationPackage
@Configuration
@ComponentScan
public class TenantConfig {
	
	@Bean
	public SimpleReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory(MongoProperties properties,
																		   MongoClient mongo) {
		String database = properties.getMongoClientDatabase();
		return new MultiTenantReactiveMongoClientDatabaseFactory(mongo, database);
	}
}
