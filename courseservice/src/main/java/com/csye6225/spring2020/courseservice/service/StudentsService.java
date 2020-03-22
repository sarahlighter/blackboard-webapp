package com.csye6225.spring2020.courseservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.Student;

public class StudentsService {
    private DynamoDBMapper mapper;
    private static AmazonDynamoDB client;

    public StudentsService() {
        client = DynamoDBConnector.getClient(false);
        mapper = new DynamoDBMapper(client);
    }

    public Student addStudent(Student student) {
        student.setJoiningDate(new Date().toString());
        mapper.save(student);
        return student;
    }

    public List<Student> getAllStudent() {
        return mapper.scan(Student.class, new DynamoDBScanExpression());
    }

    public List<Student> getStudentsByProgram(String programId) {
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1", new AttributeValue().withS(programId));
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                                                    .withIndexName("programId-index")
                                                    .withConsistentRead(false)
                                                    .withKeyConditionExpression("programId = :v1")
                                                    .withExpressionAttributeValues(eav);
        List<Student> students = mapper.query(Student.class, queryExpression);
        return students;
    }

    public Student getStudent(String id) {
        Student std = mapper.load(Student.class, id);
        if (std == null)
            return null;
        return std;
    }

    public Student updateStudent(String id, Student student) {
        Student oldStudent = mapper.load(Student.class, id);
        if (oldStudent == null) {
            return null;
        }
        String studentId = oldStudent.getStudentId();
        student.setStudentId(studentId);
        mapper.save(student);
        return student;
    }

    public Student deleteStudent(String id) {
        final Student oldStudent = getStudent(id);
        if (oldStudent == null)
            return null;
        mapper.delete(oldStudent);
        return oldStudent;
    }


}
