package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.csye6225.spring2020.courseservice.datamodel.DynamoDBConnector;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Student;

public class StudentsService {
	private static HashMap<String, Student> stud_Map = InMemoryDatabase.getStudentsDB();

	private DynamoDBMapper mapper;
	private static AmazonDynamoDB client;
	public StudentsService() {
		client=DynamoDBConnector.getClient(true);
		mapper= new DynamoDBMapper(client);
	}
	public Student addStudent(Student student) {
//		String id= String.valueOf(InMemoryDatabase.getNextStudentId());
//		student.setStudentId(id);
		
		student.setJoiningDate(new Date().toString());
//		stud_Map.put(id,student);
		mapper.save(student);
		System.out.println("I am here");
		return student;
	}
	public List<Student> getAllStudent() {
		ArrayList<Student> allStudents=new ArrayList<>();
		for(Student std:stud_Map.values()) {
			allStudents.add(std);
		}
		return allStudents;
	}
	public List<Student> getStudentsByProgram(String programId){
		ArrayList<Student> students = new ArrayList<>();
		for(Student std:stud_Map.values()) {
			if(std.getProgramId().equals(programId)) {
				students.add(std);
			}
		}
		return students;
	}
	public Student getStudent(String id) {
		Student std=stud_Map.get(id);
		if(std == null) 
			return null;
		return std;
	}
	public Student updateStudent(String id, Student student) {
		//TODO map to database
		Student oldStudent = stud_Map.get(id);
		if(oldStudent == null ) {
			return null;
		}
		String studentId = oldStudent.getStudentId();
		student.setStudentId(studentId);
		//TODO map to database
		stud_Map.put(id, student);
		return getStudent(id);
	}
	
	public Student deleteStudent(String id) {
		final Student oldStudent=getStudent(id);
		if(oldStudent == null)
			return null;
		stud_Map.remove(id);
		return oldStudent;
	}
	
	public boolean isValid(Student std) {
		if(!new ProgramsService().isExist(std.getProgramId())) {
			return false;
		}
		return true;
	}
}
