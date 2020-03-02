package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.IDynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Lecture;

public class LecturesService {
	private static HashMap<String, Lecture> lect_Map = new HashMap<>();
	private static AmazonDynamoDB client;
	private DynamoDBMapper mapper;
	public LecturesService() {
		client= DynamoDBConnector.getClient(false);
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
				.withIndexName("courseId")
				.withKeyConditionExpression("courseId = :val1")
				.withExpressionAttributeValues(eav);
		List<Lecture> list = mapper.query(Lecture.class, queryExpression);
		return list ;
	}

	
	public Lecture addLecture(Lecture lect) {
		//TODO
//		if(!isValid(lect)) {
//			System.out.println("Invalid Lecture object input");
//			return null;
//		}
		mapper.save(lect);
		return lect;
	}
	
	// Getting One Lecture
	public Lecture getLecture(String lectId) {
		 //TODO
//		if(!isExist(lectId)) {
//			System.out.println("THis lecture is not exist");
//			return null;
//		}
		 Lecture lect2 = mapper.load(Lecture.class,lectId);
		return lect2;
	}
	
	// Deleting a Lecture
	public Lecture deleteLecture(String lectId) {
//		if(!isExist(lectId)) {
//			System.out.println("THis lecture is not exist");
//			return null;
//		}
		Lecture oldLect = getLecture(lectId);
		mapper.delete(oldLect);
		return oldLect;
	}
	
	// Updating Lecture Info
	public Lecture updateLecture(String lectId, Lecture lect) {
//		if (oldlectObject == null || !isValid(lect)){
//			System.out.println("Not exist or Invalid lecture input");
//            return null;
//        }
		lect.setLectureId(lectId);
		mapper.save(lect);
		return lect;
	}
	
	public boolean isExist(String lectId) {
		return lect_Map.containsKey(lectId);
	}
	public boolean isValid(Lecture lect) {
		if(!new CoursesService().isExist(lect.getCourseId())) {
			return false;
		}
		return true;
	}
	
}
