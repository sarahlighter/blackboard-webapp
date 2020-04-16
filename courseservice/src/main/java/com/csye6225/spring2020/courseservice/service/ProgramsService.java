package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Program;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.Program;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;

public class ProgramsService {
    private DynamoDBMapper mapper;
    private static AmazonDynamoDB client;

    public ProgramsService() {
        client = DynamoDBConnector.getClient(true);
		mapper = new DynamoDBMapper(client);
    }

    public List<Program> getAllPrograms() {
        List<Program> allPrograms = mapper.scan(Program.class,new DynamoDBScanExpression());
        return allPrograms;
    }

    public Program addProgram(Program pgrm) {
        //check if pgrm is valid
        //1. programId should be unique
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("programId", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            mapper.save(pgrm, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if program table doesn't contain this program, throw exception
            System.out.println("This program is exist: Invalid program id:" + pgrm.getProgramId());
            return null;
        }
        return pgrm;
    }

    //Get program by programId
    public Program getProgram(String programId) {
        Program pgrm = mapper.load(Program.class, programId);
        if (pgrm == null) {
            System.out.println("This program is not exist: Invalid id:" + programId);
        }
        return pgrm;
    }

    // Deleting a Program
    public Program deleteProgram(String Id) {
        Program oldObject = getProgram(Id);
        if (oldObject != null) {
            mapper.delete(oldObject);
        } else {
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Program Info
    public Program updateProgram(String Id, Program pgrm) {
        pgrm.setProgramId(Id);
        //check if program table contains this programId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("programId", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(pgrm, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if program table doesn't contain this program,
            System.out.println("This program is not exist: Invalid id:" + Id);
            return null;
        }
        return pgrm;
    }

}
