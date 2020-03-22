package com.csye6225.spring2020.courseservice.service;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;

public class CoursesService {

    private static AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    public CoursesService() {
        client = DynamoDBConnector.getClient(false);
        mapper = new DynamoDBMapper(client);
    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = mapper.scan(Course.class, new DynamoDBScanExpression());
        return allCourses;
    }

    public List<Course> getCoursesByProgram(String programId) {
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":value", new AttributeValue().withS(programId));
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                                                    .withIndexName("programId-index")
                                                    .withConsistentRead(false)
                                                    .withKeyConditionExpression("programId = :value")
                                                    .withExpressionAttributeValues(eav);
        List<Course> courses = mapper.query(Course.class, queryExpression);
        return courses;
    }

    public Course getCourse(String courseId) {
        //TODO
//        if (!isExist(courseId)) {
//            return null;
//        }
        Course cors = mapper.load(Course.class, courseId);
        return cors;
    }

    public Course addCourse(Course cor) {
        //TODO
//        if (isExist(cor.getCourseId()) || !isValid(cor)) {
//            return null;
//        }
        mapper.save(cor);
        return cor;
    }

    public Course deleteCourse(String courseId) {
        //TODO
//        if (!isExist(courseId)) {
//            return null;
//        }
        Course oldCourse = getCourse(courseId);
        mapper.delete(oldCourse);
        return oldCourse;
    }

    public Course updateCourse(String courseId, Course cor) {
        //TODO
//        if (!isValid(cor)) {
//            return null;
//        }
        Course oldCourse = getCourse(courseId);
        cor.setCourseId(oldCourse.getCourseId());
        mapper.save(cor);
        return cor;
    }

}
