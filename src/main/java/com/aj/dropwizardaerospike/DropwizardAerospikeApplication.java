package com.aj.dropwizardaerospike;

import com.aerospike.client.AerospikeClient;
import com.aj.dropwizardaerospike.resource.DropwizardAerospikeHealthCheckResource;
import com.aj.dropwizardaerospike.resource.PingResource;
import com.aj.dropwizardaerospike.resource.StudentResource;
import com.aj.dropwizardaerospike.service.StudentService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DropwizardAerospikeApplication extends Application<DropwizardAerospikeConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(DropwizardAerospikeApplication.class);

	public static void main(String[] args) throws Exception {
		new DropwizardAerospikeApplication().run("server", args[0]);
	}

    @Override
    public void initialize(Bootstrap<DropwizardAerospikeConfiguration> b) {
    }

	@Override
	public void run(DropwizardAerospikeConfiguration config, Environment env)
			throws Exception {
		AerospikeClient aerospikeClient = new AerospikeClient("172.28.128.3", 3000);
	    AerospikeManaged aerospikeManaged = new AerospikeManaged(aerospikeClient);
        env.lifecycle().manage(aerospikeManaged);
	    StudentService studentService = new StudentService();
	    logger.info("Registering RESTful API resources");
		env.jersey().register(new PingResource());
        env.jersey().register(new StudentResource(studentService, aerospikeClient, config));
		env.healthChecks().register("DropwizardAerospikeHealthCheck",
				new DropwizardAerospikeHealthCheckResource(config));
	}
}
