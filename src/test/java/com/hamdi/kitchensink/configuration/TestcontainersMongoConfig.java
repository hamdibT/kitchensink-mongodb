package com.hamdi.kitchensink.configuration;

import com.mongodb.client.MongoClients;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.testcontainers.containers.MongoDBContainer;
/*
@TestConfiguration
@EnableMongoRepositories(basePackages = "com.hamdi.kitchensink.data")
public class TestcontainersMongoConfig {

    @Bean
    public MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:4.4.3");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDBContainer mongoDBContainer) {
        String mongoUri = mongoDBContainer.getReplicaSetUrl();
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(mongoUri), "test");
        mongoTemplate.indexOps("members").ensureIndex(new Index().on("email", org.springframework.data.domain.Sort.Direction.ASC).unique());

        return mongoTemplate;
    }
}
*/