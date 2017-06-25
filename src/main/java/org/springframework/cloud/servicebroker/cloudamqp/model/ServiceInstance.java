package org.springframework.cloud.servicebroker.cloudamqp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceRequest;

/**
 * An instance of a Service Definition.
 *
 * @author ipolyzos
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ServiceInstance {

    @JsonSerialize
    @JsonProperty("service_instance_id")
    private String id;

    @JsonSerialize
    @JsonProperty("service_id")
    private String serviceDefinitionId;

    @JsonSerialize
    @JsonProperty("plan_id")
    private String planId;

    @JsonSerialize
    @JsonProperty("organization_guid")
    private String organizationGuid;

    @JsonSerialize
    @JsonProperty("space_guid")
    private String spaceGuid;

    @JsonSerialize
    @JsonProperty("dashboard_url")
    private String dashboardUrl;

    @JsonSerialize
    @JsonProperty("amqp_url")
    private String amqpUrl;

    @JsonSerialize
    @JsonProperty("cloudamqp_id")
    private String cloudAmqpId;

    @SuppressWarnings("unused")
    private ServiceInstance() {
    }

    public ServiceInstance(String serviceInstanceId, String serviceDefinitionId, String planId,
                           String organizationGuid, String spaceGuid, String dashboardUrl) {
        this.id = serviceInstanceId;
        this.serviceDefinitionId = serviceDefinitionId;
        this.planId = planId;
        this.organizationGuid = organizationGuid;
        this.spaceGuid = spaceGuid;
        this.dashboardUrl = dashboardUrl;
    }

    /**
     * Create a ServiceInstance from a create request. If fields
     * are not present in the request they will remain null in the
     * ServiceInstance.
     *
     * @param request containing details of ServiceInstance
     */
    public ServiceInstance(CreateServiceInstanceRequest request) {
        this.serviceDefinitionId = request.getServiceDefinitionId();
        this.planId = request.getPlanId();
        this.organizationGuid = request.getOrganizationGuid();
        this.spaceGuid = request.getSpaceGuid();
        this.id = request.getServiceInstanceId();
    }

    /**
     * Create a ServiceInstance from a delete request. If fields
     * are not present in the request they will remain null in the
     * ServiceInstance.
     *
     * @param request containing details of ServiceInstance
     */
    public ServiceInstance(DeleteServiceInstanceRequest request) {
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
        this.serviceDefinitionId = request.getServiceDefinitionId();
    }

    /**
     * Create a service instance from an update request. If fields
     * are not present in the request they will remain null in the
     * ServiceInstance.
     *
     * @param request containing details of ServiceInstance
     */
    public ServiceInstance(UpdateServiceInstanceRequest request) {
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
    }

    public String getServiceInstanceId() {
        return id;
    }

    public String getServiceDefinitionId() {
        return serviceDefinitionId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public String getSpaceGuid() {
        return spaceGuid;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public String getAmqpUrl() {
        return amqpUrl;
    }

    public void setAmqpUrl(String amqpUrl) {
        this.amqpUrl = amqpUrl;
    }

    public String getCloudAmqpId() {
        return cloudAmqpId;
    }

    public void setCloudAmqpId(String cloudAmqpId) {
        this.cloudAmqpId = cloudAmqpId;
    }

    public ServiceInstance and() {
        return this;
    }

    public ServiceInstance withDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
        return this;
    }
}
