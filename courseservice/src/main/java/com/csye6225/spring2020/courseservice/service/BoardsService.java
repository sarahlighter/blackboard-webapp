package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Board;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;

public class BoardsService {
    private static HashMap<String, Board> brd_Map = new HashMap<>();
    private static AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    public BoardsService() {
        client = DynamoDBConnector.getClient(false);
        mapper = new DynamoDBMapper(client);
    }

    // Getting a list of all Board
    // GET "..webapi/boards"
    public List<Board> getAllBoards() {
        //Getting the list
        List<Board> list = mapper.scan(Board.class, new DynamoDBScanExpression());
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
        //TODO check if brd is valid
//		if( !isValid(brd)) {
//			return null;
//		}
        mapper.save(brd);
        return brd;
    }

    // Getting One Board
    public Board getBoard(String brdId) {
//		if(!isExist(brdId)) {
//			return null;
//		}
        Board brd = mapper.load(Board.class, brdId);
        return brd;
    }

    // Deleting a Board
    public Board deleteBoard(String brdId) {
//		if(!isExist(brdId)) {
//			return null;
//		}
        Board oldbrdObject = getBoard(brdId);
        mapper.delete(oldbrdObject);
        return oldbrdObject;
    }

    // Updating Board Info
    public Board updateBoard(String brdId, Board brd) {
//		if(!isExist(brdId)) {
//			return null;
//		}
        Board oldbrdObject = getBoard(brdId);
        brd.setBoardId(brdId);
        brd.setPostDate(oldbrdObject.getPostDate());
        mapper.save(brd);
        return brd;
    }

    public boolean isExist(String brdId) {
        return brd_Map.containsKey(brdId);
    }

    public boolean isValid(Board brd) {
        if (!new CoursesService().isExist(brd.getCourseId())) {
            return false;
        }
        return true;
    }
}
