package com.aj.dropwizardaerospike.resource;

import com.aerospike.client.AerospikeClient;
import com.aj.dropwizardaerospike.DropwizardAerospikeConfiguration;
import com.aj.dropwizardaerospike.cache.CacheConfigManager;
import com.aj.dropwizardaerospike.service.StudentService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private static final Logger logger = LoggerFactory.getLogger(StudentResource.class);

    private static StudentService studentService;
    private static AerospikeClient aerospikeClient;
    private static DropwizardAerospikeConfiguration config;

    public StudentResource(StudentService studentService, AerospikeClient aerospikeClient,
                           DropwizardAerospikeConfiguration config) {
        this.studentService = studentService;
        this.aerospikeClient = aerospikeClient;
        this.config = config;
    }

    @Timed
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cache() {
        logger.info("In StudentResource.cache()...Get Student Data");
        //On the first call, data will be fetched from DB and
        //cache will be populated with the corresponding student record
        //On all subsequent calls, data will be returned from the cache
        for (int i = 1; i < 4; i++) {
            getStudentData(i);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student Data has been retrieved");
        return Response.ok(response).build();
    }

    private void getStudentData(int i) {
        logger.info("********** Call " + String.valueOf(i) + " Started **********");
        logger.info("Call " + String.valueOf(i) + ": {}",
                CacheConfigManager.getInstance().getStudentData("S100",studentService,
                        aerospikeClient, config));
        logger.info("Call " + String.valueOf(i) + ": {}",
                CacheConfigManager.getInstance().getStudentData("M101",studentService,
                        aerospikeClient, config));
        logger.info("Call " + String.valueOf(i) + ": {}",
                CacheConfigManager.getInstance().getStudentData("P102",studentService,
                        aerospikeClient, config ));
        logger.info("********** Call " + String.valueOf(i) + " Ended **********");
    }
}
