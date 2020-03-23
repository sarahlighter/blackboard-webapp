package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.*;

public class LecturesService {
	private static AmazonDynamoDB client;
	private DynamoDBMapper mapper;
	public LecturesService() {
		client = DynamoDBConnector.getClient(true);
		mapper=new DynamoDBMapper(client);
	}

	public List<Lecture> getAllLectures() {	
		List<Lecture> list = mapper.scan(Lecture.class,new DynamoDBScanExpression());
		return list ;
	}
	public List<Lecture> getLecturesByCourse(String courseId){
		HashMap<String, AttributeValue> eav = new HashMap<>();
		eav.put(":val1",new AttributeValue().withS(courseId));
		DynamoDBQueryExpression<Lecture> queryExpression = new DynamoDBQueryExpression()
				.withIndexName("courseId-index")
				.withKeyConditionExpression("courseId = :val1")
				.withExpressionAttributeValues(eav)
				.withConsistentRead(false);
		List<Lecture> list = mapper.query(Lecture.class, queryExpression);
		return list ;
	}


	public Lecture addLecture(Lecture lect) {
		//check if lect is valid
		//1. lectureId should be unique
		try {
			DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
			HashMap<String, ExpectedAttributeValue> expected = new HashMap();
			expected.put("lectureId", new ExpectedAttributeValue().withExists(false));
			saveExpression.setExpected(expected);
			mapper.save(lect, saveExpression);
		} catch (ConditionalCheckFailedException e) {
			//if lecture table doesn't contain this lecture, throw exception
			System.out.println("This lecture is exist: Invalid lecture id:" + lect.getLectureId());
			return null;
		}
		return lect;
	}

	//Get lecture by lectureId
	public Lecture getLecture(String lectureId) {
		Lecture lect = mapper.load(Lecture.class, lectureId);
		if (lect == null) {
			System.out.println("This lecture is not exist: Invalid id:" + lectureId);
		}
		return lect;
	}

	// Deleting a Lecture
	public Lecture deleteLecture(String Id) {
		Lecture oldObject = getLecture(Id);
		if (oldObject != null) {
			mapper.delete(oldObject);
		} else {
			System.out.println("Delete Fail");
		}
		return oldObject;
	}

	// Updating Lecture Info
	public Lecture updateLecture(String Id, Lecture lect) {
		lect.setLectureId(Id);
		//check if lecture table contains this lectureId before update
		try {
			DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
			HashMap<String, ExpectedAttributeValue> expected = new HashMap();
			expected.put("lectureId", new ExpectedAttributeValue(new AttributeValue(Id)));
			saveExpression.setExpected(expected);
			mapper.save(lect, saveExpression);
		} catch (ConditionalCheckFailedException e) {
			//if lecture table doesn't contain this lecture,
			System.out.println("This lecture is not exist: Invalid id:" + Id);
			return null;
		}
		return lect;
	}
}
