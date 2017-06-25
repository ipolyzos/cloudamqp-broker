package org.springframework.cloud.servicebroker.cloudamqp.model;

/**
 * An instance of a CloudAMQP Create Instance Response Definition
 *
 * @author ipolyzos
 */
public class CloudAMQPCreateInstanceResponse {

    private String id;
    private String url;

    public CloudAMQPCreateInstanceResponse() {
        super();
    }

    public CloudAMQPCreateInstanceResponse(final String url) {
        this.url = url;
    }

    public CloudAMQPCreateInstanceResponse(final String id,
                                           final String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
