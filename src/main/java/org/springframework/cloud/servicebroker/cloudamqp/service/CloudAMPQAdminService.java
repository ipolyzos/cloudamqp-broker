package org.springframework.cloud.servicebroker.cloudamqp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.cloudamqp.config.BrokerConfig;
import org.springframework.cloud.servicebroker.cloudamqp.exception.CloudAMQPServiceException;
import org.springframework.cloud.servicebroker.cloudamqp.model.CloudAMQPCreateInstanceResponse;
import org.springframework.cloud.servicebroker.cloudamqp.model.CloudAMQPInstance;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Utility class for manipulating CloudAMQP account
 *
 * @author ipolyzos
 */
@Service
public class CloudAMPQAdminService {

    private Logger logger = LoggerFactory.getLogger(CloudAMPQAdminService.class);

    /**
     * Service Broker Config
     */
    private BrokerConfig brokerConfig;

    /**
     * Jersey Rest Client
     */
    private Client client;

    @Autowired
    public CloudAMPQAdminService(final Client restClient,
                                 final BrokerConfig brokerConfig) {
        this.client = restClient;
        this.brokerConfig = brokerConfig;
    }

    /**
     * Create a broker Instance
     *
     * @param brokerName
     * @return
     * @throws CloudAMQPServiceException
     */
    public CloudAMQPCreateInstanceResponse createInstance(final String brokerName,
                                                          final String plan) throws CloudAMQPServiceException {
        // build input form
        final MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("name", brokerName);
        form.add("plan", plan);
        form.add("region", brokerConfig.getCloudAMQPRegion());

        //build post request
        final String target = String.format("%s/%s", brokerConfig.getCloudAMQPAPIURL(), "instances");
        final WebTarget webTarget = client.target(target);

        // call create broker instances API
        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), CloudAMQPCreateInstanceResponse.class);
    }

    /**
     * Change a broker Instance
     *
     * @param brokerName
     * @throws CloudAMQPServiceException
     */
    public void changeInstance(final String instanseId,
                               final String brokerName,
                               final String plan) throws CloudAMQPServiceException {
        // build input form
        final MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("name", brokerName);
        form.add("plan", plan);

        //build post request
        final String target = String.format("%s/%s/%s", brokerConfig.getCloudAMQPAPIURL(), "instances", instanseId);
        final WebTarget webTarget = client.target(target);

        // call create broker instances API
        webTarget.request(MediaType.APPLICATION_JSON).put(Entity.form(form));
    }

    /**
     * Check if Broker Instance exists
     *
     * @param brokerName
     * @return
     * @throws CloudAMQPServiceException
     */
    public boolean brokerInstanceExists(final String brokerName) throws CloudAMQPServiceException {
        final String target = String.format("%s/%s/%s", brokerConfig.getCloudAMQPAPIURL(), "instances", brokerName);
        final WebTarget webTarget = client.target(target);

        // call create broker instances API
        try {
            final CloudAMQPInstance response = webTarget.request(MediaType.APPLICATION_JSON)
                    .get(CloudAMQPInstance.class);

            // check brokers exist
            if (response != null) {
                return true;
            }

        } catch (NotFoundException e) {
            return false;
        }

        return false;
    }

    /**
     * Delete a broker instance
     *
     * @param brokerInstanceId
     * @throws CloudAMQPServiceException
     */
    public String deleteBrokerInstance(final String brokerInstanceId) throws CloudAMQPServiceException {
        final String target = String.format("%s/%s/%s", brokerConfig.getCloudAMQPAPIURL(), "instances", brokerInstanceId);
        final WebTarget webTarget = client.target(target);

        // call delete broker instance API
        return webTarget.request().delete(String.class);
    }

}
