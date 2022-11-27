# Tenant implementation
Main idea of this project take any spring boot project and make tenant support with minimal changes with different approaches.

## 1. Hibernate
[spring.io](https://spring.io/blog/2022/07/31/how-to-integrate-hibernates-multitenant-feature-with-spring-data-jpa-in-a-spring-boot-application)  
[example ref](https://callistaenterprise.se/blogg/teknik/2020/10/10/multi-tenancy-with-spring-boot-part4/)


#### 1.1 DISCRIMINATOR
Not support with MongoDB with additional query filter

#### 1.2 Database
Database per tenant [project here](/mongo-tenant-schema)

Supported DISCRIMINATOR only from Hibernate 6 

## 2. MongoDB
[spring-doc](https://docs.spring.io/spring-integration/reference/html/mongodb.html)  
[example-reference](https://assist-software.net/blog/how-implement-dynamic-multi-tenancy-mongodb-and-spring-boot)

#### 2.1 Attribute (Tenant field) - DISCRIMINATOR
Not support with MongoDB with additional query filter

#### 2.2 Database
Database per tenant [project here](/mongo-tenant-schema)

#### 2.2 Database Reactive
Database per tenant [project here](/mongo-tenant-schema-reactive)

##### Gradle
``` groovy
implementation 'org.springframework.boot:spring-boot-starter-data-mongodb-reactive'
```

##### Implementation
> Create new bean `ReactiveMongoDatabaseFactory` example of `SimpleReactiveMongoDatabaseFactory` with change method `Mono<MongoDatabase> getMongoDatabase(String dbName)`

See also spring boot config of reactive: `MongoReactiveDataAutoConfiguration`

##### Drop all databases in MongoDB
``` javascript
var dbs = db.getMongo().getDBNames()
.filter(function(dbname) { return dbname.includes('tenant')})
.forEach( function(dbname) {db.getMongo().getDB(dbname).dropDatabase()});

dbs;
```

