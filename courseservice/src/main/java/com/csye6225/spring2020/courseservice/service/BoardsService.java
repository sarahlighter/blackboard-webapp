package com.csye6225.spring2020.courseservice.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Board;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;

import java.text.SimpleDateFormat;
import java.util.*;

public class BoardsService {
    private DynamoDBMapper mapper;

    public BoardsService() {
        AmazonDynamoDB client = DynamoDBConnector.getClient(true);
        mapper = new DynamoDBMapper(client);
    }

    // Getting a list of all Board
    // GET "..webapi/boards"
    public List<Board> getAllBoards() {
        //Query or Scan operation is limited to 1 MB.
        List<Board> list = new ArrayList<Board>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        ScanResultPage<Board> scanPage = null;
        do {
            scanPage = mapper.scanPage(Board.class, scanExpression);
            scanPage.getResults().forEach((brd) -> {
                list.add(brd);
            });
            scanExpression.setExclusiveStartKey(scanPage.getLastEvaluatedKey());
        } while (scanPage.getLastEvaluatedKey() != null);
        return list;
    }

    public List<Board> getBoardsByCourse(String corsId) {
        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":value", new AttributeValue().withS(corsId));
        Map<String, AttributeValue> attributeValue = new HashMap<String, AttributeValue>();
        attributeValue.put(":value", new AttributeValue().withS(corsId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("courseId = :value").withExpressionAttributeValues(attributeValue);
        List<Board> list = mapper.scan(Board.class, scanExpression);
        return list;
    }

    public Board addBoard(Board brd) {
        //check if brd is valid
        //1. boardId should be unique
        //TODO 2. courseId should be exist
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("boardId", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            //Set post date
            brd.setPostDate(getDate());
            mapper.save(brd, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if board table doesn't contain this board, throw exception
            System.out.println("This board is exist: Invalid board id:" + brd.getBoardId());
            return null;
        }
        return brd;
    }

    //Get board by boardId
    public Board getBoard(String boardId) {
        Board brd = mapper.load(Board.class, boardId);
        if (brd == null) {
            System.out.println("This board is not exist: Invalid id:" + boardId);
        }
        return brd;
    }

    // Deleting a Board
    public Board deleteBoard(String Id) {
        Board oldObject = getBoard(Id);
        if (oldObject != null) {
            mapper.delete(oldObject);
        } else {
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Board Info
    public Board updateBoard(String Id, Board brd) {
        brd.setBoardId(Id);
        //check if board table contains this boardId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("boardId", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(brd, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if board table doesn't contain this board,
            System.out.println("This board is not exist: Invalid id:" + Id);
            return null;
        }
        return brd;
    }

    private String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
