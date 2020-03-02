package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
//import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
//import com.csye6225.spring2020.courseservice.datamodel.DynamoDbConnector;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Professor;

public class ProfessorsService {

    private static HashMap<String, Professor> prof_Map = InMemoryDatabase.getProfessorDB();
    private DynamoDBMapper mapper;
    private static AmazonDynamoDB client;

    public ProfessorsService() {
        client = DynamoDBConnector.getClient(false);
        mapper = new DynamoDBMapper(client);
    }

    public List<Professor> getAllProfessors() {
        List<Professor> list = mapper.scan(Professor.class, new DynamoDBScanExpression());
        return list;
    }

    public Professor addProfessor(Professor prof) {
        mapper.save(prof);
        return prof;
    }

    // Getting One Professor
    public Professor getProfessor(String profId) {
        Professor prof = mapper.load(Professor.class, profId);
        return prof;
    }

    // Deleting a professor
    public Professor deleteProfessor(String profId) {
        Professor oldObject = getProfessor(profId);
        mapper.delete(oldObject);
        return oldObject;
    }

    // Updating Professor Info
    public Professor updateProfessorInformation(String profId, Professor prof) {
//		if (oldProfObject == null)
//        {
//            return null;
//        }
        prof.setProfessorId(profId);
        mapper.save(prof);
        return prof;
    }

    // Get professors in a department
    public List<Professor> getProfessorsByDepartment(String department) {
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":value1", new AttributeValue().withS(department));
        DynamoDBQueryExpression<Professor> queryExpression = new DynamoDBQueryExpression()
                .withIndexName("department")
                .withKeyConditionExpression("department = :value1")
                .withExpressionAttributeValues(eav);
        List<Professor> list = mapper.query(Professor.class, queryExpression);
        return list;
    }

    public boolean isExist(String profId) {
        return prof_Map.containsKey(profId);
    }


    // Get professors for a year with a size limit

}
