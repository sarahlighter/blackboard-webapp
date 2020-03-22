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
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.Professor;

public class ProfessorsService {

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
        prof.setJoiningDate(getDate());
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("Id", new ExpectedAttributeValue().withExists(false));
            expected.put("professorId", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            //Set joining date
            prof.setJoiningDate(getDate());
            mapper.save(prof, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if professor table doesn't contain this professor, throw exception
            System.out.println("The professor is exist: Invalid Id:"+prof.getId());
            return null;
        }
        return prof;
    }

    // Getting One Professor
    public Professor getProfessor(String Id) {
        Professor prof = mapper.load(Professor.class, Id);
        return prof;
    }

    // Deleting a professor
    public Professor deleteProfessor(String Id) {
        Professor oldObject = getProfessor(Id);
        mapper.delete(oldObject);
        return oldObject;
    }

    // Updating Professor Info
    public Professor updateProfessorInformation(String Id, Professor prof) {
        prof.setId(Id);
        //check if professor table contains this Id before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String,ExpectedAttributeValue> expected = new HashMap();
            expected.put("Id", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(prof, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if board table doesn't contain this board,
            System.out.println("This professor is not exist: Invalid id:"+Id);
            return null;
        }
        return prof;
    }

    // Getting Professor by professorId
    public List<Professor> getProfessorByProfessorId(String professorId){
        DynamoDBQueryExpression queryExpression = getQueryExpression(null,professorId,null);
        List<Professor> professorList = mapper.query(Professor.class, queryExpression);
        return professorList;
    }
    // Get professors in a department
    public List<Professor> getProfessorsByDepartment(String department) {
        DynamoDBQueryExpression queryExpression = getQueryExpression(department,null,null);
        List<Professor> list = mapper.query(Professor.class, queryExpression);
        return list;
    }

    //Get professors for a year with a size limit
    public List<Professor> getProfessorByYear(String year){
        Map<String, AttributeValue> attributeValue = new HashMap<>();
        attributeValue.put(":value", new AttributeValue().withS(year));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("begins_with(joiningDate, :value)")
                .withExpressionAttributeValues(attributeValue);
        List<Professor> result= mapper.scan(Professor.class, scanExpression);
        return result;
    }

    public List<Professor> getProfessorByDepartmentAndYear(String department, String year){
        DynamoDBQueryExpression queryExpression = getQueryExpression(department,null,year);
        List<Professor> result= mapper.query(Professor.class, queryExpression);
        return result;
    }

    private DynamoDBQueryExpression getQueryExpression(String department, String professorId, String year){
        HashMap<String, AttributeValue> eav = new HashMap<>();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withConsistentRead(false);
        if(department!=null){
            eav.put(":value1", new AttributeValue().withS(department));
            queryExpression.withIndexName("department-index").withKeyConditionExpression("department = :value1");
        }
        if(professorId!=null){
            eav.put(":value2", new AttributeValue().withS(professorId));
            queryExpression.withIndexName("professorId-index").withKeyConditionExpression("professorId = :value2");
        }
        if(year!=null){
            eav.put(":value3", new AttributeValue().withS(year));
            queryExpression.withFilterExpression("begins_with(joiningDate, :value3)");
        }
        queryExpression.withExpressionAttributeValues(eav);
        return queryExpression;
    }
    private String getDate(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

}