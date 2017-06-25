package org.springframework.cloud.servicebroker.cloudamqp.exception;

import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;

/**
 * Exception thrown when issues with the underlying CloudAMQP service occur.
 *
 * @author ipolyzos
 */
public class CloudAMQPServiceException extends ServiceBrokerException {

    private static final long serialVersionUID = 1432950105016369392L;

    public CloudAMQPServiceException(String message) {
        super(message);
    }

}
