package com.csye6225.spring2020.courseservice.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.csye6225.spring2020.courseservice.datamodel.Board;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;

public class BoardsService {
    private static AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    private final static String tableName = "Board";
    public BoardsService() {
        client = DynamoDBConnector.getClient(false);
        mapper = new DynamoDBMapper(client);
    }

    public List<Board> getAllBoards() {
        //Query or Scan operation is limited to 1 MB.
        List<Board> list = new ArrayList<Board>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        ScanResultPage<Board> scanPage = null;
        do{
            scanPage = mapper.scanPage(Board.class,scanExpression);
            scanPage.getResults().forEach((brd)-> {list.add(brd);});
            scanExpression.setExclusiveStartKey(scanPage.getLastEvaluatedKey());
        } while(scanPage.getLastEvaluatedKey() != null);
        return list;
    }

    public List<Board> getBoardsByCourse(String corsId) {
        Map<String, AttributeValue> attributeValue = new HashMap<>();
        attributeValue.put(":value", new AttributeValue().withS(corsId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                                                .withFilterExpression("courseId = :value")
                                                .withExpressionAttributeValues(attributeValue);
        List<Board> list = mapper.scan(Board.class, scanExpression);
        return list;
    }

    public Board addBoard(Board brd) {
        //check if brd is valid
        //1. boardId should be unique
        //TODO 2. courseId should be exist
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String,ExpectedAttributeValue> expected = new HashMap();
            expected.put("Id", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            //Set post date
            brd.setPostDate(getDate());
            mapper.save(brd, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if board table doesn't contain this board, throw exception
            System.out.println("This board is exist: Invalid board id:"+brd.getId());
            return null;
        }
        return brd;
    }

    // Getting One Board
    public Board getBoardbyId(String Id) {
        Board brd = mapper.load(Board.class, Id);
        if(brd==null){
            System.out.println("This board is not exist: Invalid id:"+Id);
        }
        return brd;
    }

    //Get board by boardId
    public List<Board> getBoardByBoardId(String boardId){
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val",new AttributeValue().withS(boardId));
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withConsistentRead(false)
                .withIndexName("boardId-index")
                .withKeyConditionExpression("boardId = :val")
                .withExpressionAttributeValues(eav);
        List<Board> list = mapper.query(Board.class,queryExpression);
        return list;
    }
    // Deleting a Board
    public Board deleteBoard(String Id) {
        Board oldObject = getBoardbyId(Id);
        if(oldObject!=null){
            mapper.delete(oldObject);
        } else{
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Board Info
    public Board updateBoard(String Id, Board brd) {
        brd.setId(Id);
        //check if board table contains this boardId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String,ExpectedAttributeValue> expected = new HashMap();
            expected.put("Id", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(brd, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if board table doesn't contain this board,
            System.out.println("This board is not exist: Invalid id:"+Id);
            return null;
        }
        return brd;
    }

    private String getDate(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

}
