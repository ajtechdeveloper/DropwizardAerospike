package com.aj.dropwizardaerospike.resource;

import com.aj.dropwizardaerospike.DropwizardAerospikeConfiguration;
import com.codahale.metrics.health.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropwizardAerospikeHealthCheckResource extends HealthCheck {

    private static final Logger logger = LoggerFactory.getLogger(DropwizardAerospikeHealthCheckResource.class);

    private static String appName;

    public DropwizardAerospikeHealthCheckResource(DropwizardAerospikeConfiguration dropwizardAerospikeConfiguration){
       this.appName = dropwizardAerospikeConfiguration.getAppName();
    }

    @Override
    protected Result check() throws Exception {
        logger.info("App Name is: {}", appName);
        if("DropwizardAerospike".equalsIgnoreCase(appName)) {
            return Result.healthy();
        }
        return Result.unhealthy("DropwizardAerospike Service is down");
    }
}