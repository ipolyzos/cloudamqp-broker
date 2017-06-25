package org.springframework.cloud.servicebroker.cloudamqp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.cloudamqp.config.BrokerConfig;
import org.springframework.cloud.servicebroker.cloudamqp.exception.CloudAMQPServiceException;
import org.springframework.cloud.servicebroker.cloudamqp.model.CloudAMQPCreateInstanceResponse;
import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstance;
import org.springframework.cloud.servicebroker.cloudamqp.repository.CloudAMPQServiceInstanceRepository;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceUpdateNotSupportedException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

/**
 * Service Instance Service implementation to manage service instances.
 * <p>
 * NOTE:
 * Creating a service does the following:
 * <p>
 * 1. Creates a new broker instance
 * 2. Saves the Service Instance info to the database.
 *
 * @author ipolyzos
 */
@Service
public class CloudAMPQServiceInstanceService implements ServiceInstanceService {

    private BrokerConfig config;

    private CloudAMPQAdminService cloudAMPQAdminService;

    private CloudAMPQServiceInstanceRepository repository;

    @Autowired
    public CloudAMPQServiceInstanceService(final BrokerConfig config,
                                           final CloudAMPQAdminService admin,
                                           final CloudAMPQServiceInstanceRepository repository) {
        this.config = config;
        this.cloudAMPQAdminService = admin;
        this.repository = repository;
    }

    @Override
    public CreateServiceInstanceResponse createServiceInstance(final CreateServiceInstanceRequest request) {
        final ServiceInstance instance = repository.findOne(request.getServiceInstanceId());
        if (instance != null) {
            throw new ServiceInstanceExistsException(request.getServiceInstanceId(), request.getServiceDefinitionId());
        }

        if (cloudAMPQAdminService.brokerInstanceExists(instance.getServiceInstanceId())) {
            // ensure the instance is empty
            cloudAMPQAdminService.deleteBrokerInstance(instance.getServiceInstanceId());
        }

        //collect params
        final String iid = instance.getServiceInstanceId();
        final String iplan = instance.getPlanId().split("_")[0];

        final CloudAMQPCreateInstanceResponse response = cloudAMPQAdminService.createInstance(iid, iplan);
        if (response == null) {
            throw new ServiceBrokerException("Failed to create new broker instance: " + instance.getServiceInstanceId());
        }

        //fill object with required information
        instance.setAmqpUrl(response.getUrl());
        instance.setCloudAmqpId(response.getId());

        repository.save(instance);

        return new CreateServiceInstanceResponse();
    }

    @Override
    public GetLastServiceOperationResponse getLastOperation(final GetLastServiceOperationRequest request) {
        return new GetLastServiceOperationResponse().withOperationState(OperationState.SUCCEEDED);
    }

    public ServiceInstance getServiceInstance(String id) {
        return repository.findOne(id);
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(final DeleteServiceInstanceRequest request) throws CloudAMQPServiceException {
        final String instanceId = request.getServiceInstanceId();
        final ServiceInstance instance = repository.findOne(instanceId);

        if (instance == null) {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }

        cloudAMPQAdminService.deleteBrokerInstance(instance.getCloudAmqpId());
        repository.delete(instanceId);

        return new DeleteServiceInstanceResponse();
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(final UpdateServiceInstanceRequest request) {
        final String instanceId = request.getServiceInstanceId();
        final ServiceInstance instance = repository.findOne(instanceId);

        if (instance == null) {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }

        if (request.equals(instance)){
            throw new ServiceInstanceUpdateNotSupportedException("No changes in the change request");
        }

        // extract required values
        final String reqplan = request.getPlanId().split("_")[0];
        final String brkId = instance.getCloudAmqpId();

        // change instance to a new plan
        cloudAMPQAdminService.changeInstance(brkId, instance.getServiceInstanceId(), reqplan );
        instance.setPlanId(request.getPlanId());

        //replace old instance
        repository.delete(instanceId);
        repository.save(instance);

        return new UpdateServiceInstanceResponse();
    }
}