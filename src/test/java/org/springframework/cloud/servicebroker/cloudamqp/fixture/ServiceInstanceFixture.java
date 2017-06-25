package org.springframework.cloud.servicebroker.cloudamqp.fixture;

import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstance;

public class ServiceInstanceFixture {
    public static ServiceInstance getServiceInstance() {
        return new ServiceInstance("service-instance-id", "service-definition-id", "plan-id",
                "org-guid", "space-guid", "http://dashboard.example.com");
    }
}
