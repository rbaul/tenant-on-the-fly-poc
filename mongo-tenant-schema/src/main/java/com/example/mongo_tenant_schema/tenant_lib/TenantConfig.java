package com.example.mongo_tenant_schema.tenant_lib;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.MongoPropertiesClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.stream.Collectors;

@ConditionalOnClass(MongoClient.class)
@AutoConfigurationPackage
@Configuration
@ComponentScan
public class TenantConfig {
	
	@Bean
	@ConditionalOnMissingBean(MongoClient.class)
	public MongoClient mongo(ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustomizers,
							 MongoClientSettings settings) {
		return new MongoClientFactory(builderCustomizers.orderedStream().collect(Collectors.toList()))
				.createMongoClient(settings);
	}
	
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(MongoClientSettings.class)
	static class MongoClientSettingsConfiguration {
		
		@Bean
		MongoClientSettings mongoClientSettings() {
			return MongoClientSettings.builder().build();
		}
		
		@Bean
		MongoPropertiesClientSettingsBuilderCustomizer mongoPropertiesCustomizer(MongoProperties properties,
																				 Environment environment) {
			return new MongoPropertiesClientSettingsBuilderCustomizer(properties, environment);
		}
		
	}
	
	@Bean
	@ConditionalOnSingleCandidate(MongoClient.class)
	MongoDatabaseFactorySupport<?> mongoDatabaseFactory(MongoClient mongoClient, MongoProperties properties) {
		return new MultiTenantMongoClientDatabaseFactory(mongoClient, properties.getMongoClientDatabase());
	}
}
