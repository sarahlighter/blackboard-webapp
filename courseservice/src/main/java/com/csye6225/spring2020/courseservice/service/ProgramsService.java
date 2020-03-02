package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.Program;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;

public class ProgramsService {
    private static HashMap<String, Program> pgm_Map = InMemoryDatabase.getProgramsDB();
    private DynamoDBMapper mapper;
    private static AmazonDynamoDB client;

    public ProgramsService() {
		client = DynamoDBConnector.getClient(false);
		mapper = new DynamoDBMapper(client);
    }

    public List<Program> getAllPrograms() {
        List<Program> allPrograms = mapper.scan(Program.class,new DynamoDBScanExpression());
        return allPrograms;
    }

    public Program getProgram(String programId) {
        Program program = mapper.load(Program.class, programId);
        return program;
    }

    public Program addProgram(Program pgm) {
        mapper.save(pgm);
        return pgm;
    }

    public Program deleteProgram(String programId) {
        Program pgm = getProgram(programId);
        mapper.delete(pgm);
        return pgm;
    }

    public Program updateProgram(String programId, Program pgm) {
        pgm.setProgramId(programId);
        mapper.save(pgm);
        return pgm;
    }

    public boolean isExist(String pgmId) {
        return pgm_Map.containsKey(pgmId);
    }
}
