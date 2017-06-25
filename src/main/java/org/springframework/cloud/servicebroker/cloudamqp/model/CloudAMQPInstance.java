package org.springframework.cloud.servicebroker.cloudamqp.model;

import java.io.Serializable;

/**
 * An instance of a CloudAMQP Instance Definition
 *
 * @author ipolyzos
 */
public class CloudAMQPInstance implements Serializable {

    private static final long serialVersionUID = 612743187295887480L;

    private String id;
    private String name;
    private String plan;
    private String region;
    private String url;

    public CloudAMQPInstance() {
        super();
    }

    public CloudAMQPInstance(final String name,
                             final String plan, String region) {
        this.name = name;
        this.plan = plan;
        this.region = region;
    }

    public CloudAMQPInstance(final String id,
                             final String name,
                             final String plan,
                             final String region) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.region = region;
    }

    public CloudAMQPInstance(final String id,
                             final String name,
                             final String plan,
                             final String region,
                             final String url) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.region = region;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(final String plan) {
        this.plan = plan;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
