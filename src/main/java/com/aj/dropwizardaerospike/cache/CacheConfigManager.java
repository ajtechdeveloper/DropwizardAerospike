package com.aj.dropwizardaerospike.cache;

import com.aerospike.client.AerospikeClient;
import com.aj.dropwizardaerospike.DropwizardAerospikeConfiguration;
import com.aj.dropwizardaerospike.domain.Student;
import com.aj.dropwizardaerospike.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfigManager.class);

    private static CacheConfigManager cacheConfigManager = new CacheConfigManager();

    public static CacheConfigManager getInstance() {
        return cacheConfigManager;
    }

    //Logic For Student Cache
    public Student getStudentData(String key, StudentService studentService,
                                  AerospikeClient aerospikeClient, DropwizardAerospikeConfiguration config) {
        try {
            Student student = studentService.getFromCache(aerospikeClient,key,
                    config.getNamespace(), config.getSetName());
            if(student == null){
                student = studentService.getFromDatabase(key);
                studentService.createStudent(aerospikeClient, student.getUniversityId(),
                        student.getName(), student.getSubjectSpecialization(),
                        config.getNamespace(), config.getSetName());
            }
            return student;
        } catch (Exception e) {
            logger.error("Error Retrieving Elements from the Student Cache"
                    + e.getMessage());
            return null;
        }
    }
}
