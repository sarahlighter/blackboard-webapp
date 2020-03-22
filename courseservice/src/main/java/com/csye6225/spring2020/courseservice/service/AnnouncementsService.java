package com.csye6225.spring2020.courseservice.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Announcement;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;

import java.util.*;

public class AnnouncementsService {
    private static AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    private final static String tableName = "Announcement";
    public AnnouncementsService() {
        client = DynamoDBConnector.getClient(false);
        mapper = new DynamoDBMapper(client);
    }

    public List<Announcement> getAllAnnouncements() {
        //Query or Scan operation is limited to 1 MB.
        List<Announcement> list = new ArrayList<Announcement>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        ScanResultPage<Announcement> scanPage = null;
        do{
            scanPage = mapper.scanPage(Announcement.class,scanExpression);
            scanPage.getResults().forEach((ann)-> {list.add(ann);});
            scanExpression.setExclusiveStartKey(scanPage.getLastEvaluatedKey());
        } while(scanPage.getLastEvaluatedKey() != null);
        return list;
    }

    public List<Announcement> getAnnouncementsByBoard(String brdId) {
        Map<String, AttributeValue> attributeValue = new HashMap<>();
        attributeValue.put(":value", new AttributeValue().withS(brdId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("boardId = :value")
                .withExpressionAttributeValues(attributeValue);
        List<Announcement> list = mapper.scan(Announcement.class, scanExpression);
        return list;
    }

    public Announcement addAnnouncement(Announcement ann) {
        //check if ann is valid
        //1. announcementId should be unique
        //2. boardId should be exist
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String, ExpectedAttributeValue> expected = new HashMap();
            expected.put("Id", new ExpectedAttributeValue().withExists(false));
            saveExpression.setExpected(expected);
            mapper.save(ann, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if announcement table doesn't contain this announcement, throw exception
            System.out.println("This announcement is exist: Invalid id:"+ann.getId());
            return null;
        }
        return ann;
    }

    // Getting One Announcement
    public Announcement getAnnouncementbyId(String Id) {
        Announcement ann = mapper.load(Announcement.class, Id);
        if(ann==null){
            System.out.println("This announcement is not exist: Invalid id:"+Id);
        }
        return ann;
    }

    // Deleting a Announcement
    public Announcement deleteAnnouncement(String Id) {
        Announcement oldObject = getAnnouncementbyId(Id);
        if(oldObject!=null){
            mapper.delete(oldObject);
        } else{
            System.out.println("Delete Fail");
        }
        return oldObject;
    }

    // Updating Announcement Info
    public Announcement updateAnnouncement(String Id, Announcement ann) {
        ann.setId(Id);
        //check if announcement table contains this announcementId before update
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            HashMap<String,ExpectedAttributeValue> expected = new HashMap();
            expected.put("Id", new ExpectedAttributeValue(new AttributeValue(Id)));
            saveExpression.setExpected(expected);
            mapper.save(ann, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            //if announcement table doesn't contain this announcement,
            System.out.println("This announcement is not exist: Invalid id:"+Id);
            return null;
        }
        return ann;
    }


}
