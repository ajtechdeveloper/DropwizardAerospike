package com.aj.dropwizardaerospike.domain;

public class Student {

    private String name;
    private String universityId;
    private String subjectSpecialization;

    public Student(String name, String universityId, String subjectSpecialization) {
        this.name = name;
        this.universityId = universityId;
        this.subjectSpecialization = subjectSpecialization;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", universityId='" + universityId + '\'' +
                ", subjectSpecialization='" + subjectSpecialization + '\'' +
                '}';
    }
}
