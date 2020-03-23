package com.csye6225.spring2020.courseservice.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Professor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CoursesService {
    private static AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    public CoursesService() {
        client = DynamoDBConnector.getClient(true);
        mapper = new DynamoDBMapper(client);
    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = mapper.scan(Course.class, new DynamoDBScanExpression());
        return allCourses;
    }

    public List<Course> getCoursesByProgram(String programId) {
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":value1", new AttributeValue().withS(programId));
        DynamoDBQueryExpression<Course> queryExpression = new DynamoDBQueryExpression()
                .withIndexName("programId-index")
                .withKeyConditionExpression("programId = :value1")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);
        List<Course> list=new LinkedList<>();
        try{
            list= mapper.query(Course.class, queryExpression);
        }catch (ConditionalCheckFailedException e) {
            System.out.println(e);
        }
        return list;
    }

    public Course getCourse(String courseId) {
        Course cors = mapper.load(Course.class, courseId);
        if (cors == null) {
            System.out.println("This course is not exist: Invalid id:" + courseId);
        }
        return cors;
    }

    public Course addCourse(Course cour) {
        //check if cour is valid
        //1. courseId should be unique
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("courseId", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            mapper.save(cour, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if course table doesn't contain this course, throw exception
            System.out.println("This course is exist: Invalid course id:" + cour.getCourseId());
            return null;
        }
        return cour;
    }

    // Deleting a Course
    public Course deleteCourse(String Id) {
        Course oldObject = getCourse(Id);
        if (oldObject != null) {
            mapper.delete(oldObject);
        } else {
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Course Info
    public Course updateCourse(String Id, Course cour) {
        cour.setCourseId(Id);
        //check if course table contains this courseId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("courseId", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(cour, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if course table doesn't contain this course,
            System.out.println("This course is not exist: Invalid id:" + Id);
            return null;
        }
        return cour;
    }
}
