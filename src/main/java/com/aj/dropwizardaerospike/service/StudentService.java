package com.aj.dropwizardaerospike.service;

import com.aerospike.client.*;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.aj.dropwizardaerospike.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService() {
    }

    public static Student getFromDatabase(String universityId) {
        Student student1 = new Student("Jim", "S100", "Science");
        Student student2 = new Student("Steve", "M101", "Maths");
        Student student3 = new Student("Mark", "P102", "Physics");
        Map<String, Student> database = new HashMap<>();
        database.put("S100", student1);
        database.put("M101", student2);
        database.put("P102", student3);
        logger.info("Database called for: {}", universityId);
        return database.get(universityId);
    }

    public void createStudent(AerospikeClient client, String universityId,
                              String name, String subjectSpecialization,
                              String namespace, String setName) throws AerospikeException {
        // Write record
        WritePolicy wPolicy = new WritePolicy();
        wPolicy.recordExistsAction = RecordExistsAction.UPDATE;
        //Cache will expire after 300 Seconds(5 minutes)
        wPolicy.expiration = 300;

        Key key = new Key(namespace, setName, universityId);
        Bin bin1 = new Bin("name", name);
        Bin bin2 = new Bin("universityId", universityId);
        Bin bin3 = new Bin("specialization", subjectSpecialization);
        client.put(wPolicy, key, bin1, bin2, bin3);
        logger.info("Student record created in cache with universityId: {}", universityId);
    }

    public Student getFromCache(AerospikeClient client, String universityId,
                                String namespace, String setName) throws AerospikeException {
        Record studentRecord;
        Key studentKey;
        Student student = null;
        studentKey = new Key(namespace, setName, universityId);
        studentRecord = client.get(null, studentKey);
        if (studentRecord != null) {
            student = new Student();
            student.setName(studentRecord.getValue("name").toString());
            student.setUniversityId(studentRecord.getValue("universityId").toString());
            student.setSubjectSpecialization(studentRecord.getValue("specialization").toString());
            logger.info("Cache called for: {}", universityId);
        }
        return student;
    }
}
