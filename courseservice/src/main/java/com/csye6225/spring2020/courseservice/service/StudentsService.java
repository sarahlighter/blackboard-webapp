package com.csye6225.spring2020.courseservice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.s3.internal.Constants;
import com.csye6225.spring2020.courseservice.datamodel.Student;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Student;

public class StudentsService {
    private DynamoDBMapper mapper;
    private static AmazonDynamoDB client;

    public StudentsService() {
        client = DynamoDBConnector.getClient(true);
        mapper = new DynamoDBMapper(client);
    }

    public List<Student> getAllStudent() {
        return mapper.scan(Student.class, new DynamoDBScanExpression());
    }

    public List<Student> getStudentsByProgram(String programId) {
        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1", new AttributeValue().withS(programId));
        DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression()
                .withIndexName("programId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("programId = :v1")
                .withExpressionAttributeValues(eav);
        List<Student> students = mapper.query(Student.class, queryExpression);
        return students;
    }

    public Student addStudent(Student std) {
        //check if std is valid
        //1. studentId should be unique
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("studentId", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            //Set joining date
            std.setJoiningDate(getDate());
            mapper.save(std, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if student table doesn't contain this student, throw exception
            System.out.println("This student is exist: Invalid student id:" + std.getStudentId());
            return null;
        }
        return std;
    }

    //Get student by studentId
    public Student getStudent(String studentId) {
        Student std = mapper.load(Student.class, studentId);
        if (std == null) {
            System.out.println("This student is not exist: Invalid id:" + studentId);
        }
        return std;
    }

    // Deleting a Student
    public Student deleteStudent(String Id) {
        Student oldObject = getStudent(Id);
        if (oldObject != null) {
            mapper.delete(oldObject);
        } else {
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Student Info
    public Student updateStudent(String Id, Student std) {
        std.setStudentId(Id);
        //check if student table contains this studentId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("studentId", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(std, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if student table doesn't contain this student,
            System.out.println("This student is not exist: Invalid id:" + Id);
            return null;
        }
        return std;
    }

    private String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
