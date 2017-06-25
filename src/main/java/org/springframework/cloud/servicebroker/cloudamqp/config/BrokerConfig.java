package org.springframework.cloud.servicebroker.cloudamqp.config;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
@EnableMongoRepositories(basePackages = "org.springframework.cloud.servicebroker.cloudamqp.repository")
public class BrokerConfig {

    @Value("${CLOUDAMQP_API_URL:https://customer.cloudamqp.com/api}")
    private String cloudAMQPAPIURL;

    @Value("${CLOUDAMQP_API_KEY}")
    private String cloudAMQPAPIKEY;

    @Value("${CLOUDAMQP_REGION:amazon-web-services::eu-west-1}")
    private String cloudAMQPRegion;

    /**
     * Build a Jersey http client instance
     *
     * @return Client
     */
    @Bean
    public Client restClient() {
        final ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        final HttpAuthenticationFeature httpAuthenticationFeature = HttpAuthenticationFeature.basic(cloudAMQPAPIKEY, "");

        final Client client = ClientBuilder.newClient(cc);
        client.register(httpAuthenticationFeature);

        return client;
    }


    public String getCloudAMQPAPIURL() {
        return cloudAMQPAPIURL;
    }

    public void setCloudAMQPAPIURL(String cloudAMQPAPIURL) {
        this.cloudAMQPAPIURL = cloudAMQPAPIURL;
    }

    public String getCloudAMQPAPIKEY() {
        return cloudAMQPAPIKEY;
    }

    public void setCloudAMQPAPIKEY(String cloudAMQPAPIKEY) {
        this.cloudAMQPAPIKEY = cloudAMQPAPIKEY;
    }

    public String getCloudAMQPRegion() {
        return cloudAMQPRegion;
    }

    public void setCloudAMQPRegion(String cloudAMQPRegion) {
        this.cloudAMQPRegion = cloudAMQPRegion;
    }
}
