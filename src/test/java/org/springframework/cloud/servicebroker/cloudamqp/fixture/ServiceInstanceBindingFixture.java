package org.springframework.cloud.servicebroker.cloudamqp.fixture;

import org.springframework.cloud.servicebroker.cloudamqp.model.ServiceInstanceBinding;

import java.util.Collections;
import java.util.Map;

public class ServiceInstanceBindingFixture {
    public static ServiceInstanceBinding getServiceInstanceBinding() {
        final Map<String, Object> credentials = Collections.singletonMap("url", (Object) "amqp://example.com");
        return new ServiceInstanceBinding("binding-id", "service-instance-id", credentials, null, "app-guid");
    }
}
