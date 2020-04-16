package com.csye6225.spring2020.courseservice.service;

import java.text.SimpleDateFormat;
import java.util.*;

//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
//import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
//import com.csye6225.spring2020.courseservice.datamodel.DynamoDbConnector;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Professor;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Professor;

public class ProfessorsService {
    private DynamoDBMapper mapper;
    private static AmazonDynamoDB client;

    public ProfessorsService() {
        client = DynamoDBConnector.getClient(true);
        mapper = new DynamoDBMapper(client);
    }

    public List<Professor> getAllProfessors() {
        List<Professor> list = mapper.scan(Professor.class, new DynamoDBScanExpression());
        return list;
    }

    // Getting One Professor
    public Professor getProfessor(String profId) {
        Professor prof = mapper.load(Professor.class, profId);
        if(prof==null){
            System.out.println("This professor is not exist: Invalid id:" + profId);
        }
        return prof;
    }
    public Professor addProfessor(Professor prof) {
        //check if prof is valid
        //1. professorId should be unique
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("professorId", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            //Set post date
            prof.setJoiningDate(getDate());
            mapper.save(prof, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if professor table doesn't contain this professor, throw exception
            System.out.println("This professor is exist: Invalid professor id:" + prof.getProfessorId());
            return null;
        }
        return prof;
    }

    // Deleting a Professor
    public Professor deleteProfessor(String Id) {
        Professor oldObject = getProfessor(Id);
        if (oldObject != null) {
            mapper.delete(oldObject);
        } else {
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Professor Info
    public Professor updateProfessor(String Id, Professor prof) {
        prof.setProfessorId(Id);
        //check if professor table contains this professorId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("professorId", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(prof, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if professor table doesn't contain this professor,
            System.out.println("This professor is not exist: Invalid id:" + Id);
            return null;
        }
        return prof;
    }
    // Get professors in a department
    public List<Professor> getProfessorsByDepartment(String department) {
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":value1", new AttributeValue().withS(department));
        DynamoDBQueryExpression<Professor> queryExpression = new DynamoDBQueryExpression()
                .withIndexName("department-index")
                .withKeyConditionExpression("department = :value1")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);
        List<Professor> list=new LinkedList<>();
        try{
             list= mapper.query(Professor.class, queryExpression);
        }catch (ConditionalCheckFailedException e) {
            System.out.println(e);
        }
        return list;
    }
    private String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    // Get professors for a year with a size limit

}
