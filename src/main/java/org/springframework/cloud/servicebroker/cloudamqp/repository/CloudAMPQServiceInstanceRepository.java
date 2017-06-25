package org.springframework.cloud.servicebroker.cloudamqp.repository;

import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Service Instance objects
 *
 * @author ipolyzos
 */
public interface CloudAMPQServiceInstanceRepository extends MongoRepository<ServiceInstance, String> {
}