package org.springframework.cloud.servicebroker.cloudamqp.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;

class CloudConfig extends AbstractCloudConfig {

    @Bean
    public MongoDbFactory documentMongoDbFactory() {
        return connectionFactory().mongoDbFactory("document-service");
    }

}