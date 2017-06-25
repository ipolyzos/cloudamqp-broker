package org.springframework.cloud.servicebroker.cloudamqp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.cloudamqp.config.BrokerConfig;
import org.springframework.cloud.servicebroker.cloudamqp.exception.CloudAMQPServiceException;
import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstance;
import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstanceBinding;
import org.springframework.cloud.servicebroker.cloudamqp.repository.CloudAMPQServiceInstanceBindingRepository;
import org.springframework.cloud.servicebroker.cloudamqp.repository.CloudAMPQServiceInstanceRepository;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * Service instance binding service.
 * <p>
 * NOTE:
 * Binding a service does the following:
 * 1. Creates a RabbitMQ instance
 * 2. Retrieve instance credentials
 * 3. Saves the ServiceInstanceBinding info to the CloudAMPQ
 *    repository along with credential and URI
 *
 * @author ipolyzos
 */
@Service
public class CloudAMPQServiceInstanceBindingService implements ServiceInstanceBindingService {

    private CloudAMPQAdminService cloudAMPQAdminService;

    private BrokerConfig brokerConfig;

    private CloudAMPQServiceInstanceRepository instanceRepository;

    private CloudAMPQServiceInstanceBindingRepository bindingRepository;

    @Autowired
    public CloudAMPQServiceInstanceBindingService(final CloudAMPQAdminService cloudAMPQAdminService,
                                                  final BrokerConfig brokerConfig,
                                                  final CloudAMPQServiceInstanceRepository instanceRepository,
                                                  final CloudAMPQServiceInstanceBindingRepository bindingRepository) {
        this.cloudAMPQAdminService = cloudAMPQAdminService;
        this.brokerConfig = brokerConfig;
        this.instanceRepository = instanceRepository;
        this.bindingRepository = bindingRepository;
    }

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(final CreateServiceInstanceBindingRequest request) {
        final String bindingId = request.getBindingId();
        final String serviceInstanceId = request.getServiceInstanceId();

        ServiceInstanceBinding binding = bindingRepository.findOne(bindingId);
        if (binding != null) {
            throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
        }

        ServiceInstance instance = instanceRepository.findOne(serviceInstanceId);
        if (instance == null) {
            throw new CloudAMQPServiceException("Instance don't exist :" + serviceInstanceId);
        }

        final Map<String, Object> credentials = Collections.singletonMap("uri", instance.getAmqpUrl());
        binding = new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());
        bindingRepository.save(binding);

        return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
    }

    @Override
    public void deleteServiceInstanceBinding(final DeleteServiceInstanceBindingRequest request) {
        final String bindingId = request.getBindingId();
        final String serviceInstanceId = request.getServiceInstanceId();

        final ServiceInstanceBinding binding = getServiceInstanceBinding(bindingId);
        if (binding == null) {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }

        bindingRepository.delete(bindingId);
    }

    protected ServiceInstanceBinding getServiceInstanceBinding(final String bindingId) {
        return bindingRepository.findOne(bindingId);
    }
}