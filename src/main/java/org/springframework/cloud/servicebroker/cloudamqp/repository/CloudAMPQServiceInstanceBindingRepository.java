package org.springframework.cloud.servicebroker.cloudamqp.repository;

import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstanceBinding;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Service Instance Binding objects
 *
 * @author ipolyzos
 */
public interface CloudAMPQServiceInstanceBindingRepository extends MongoRepository<ServiceInstanceBinding, String> {
}