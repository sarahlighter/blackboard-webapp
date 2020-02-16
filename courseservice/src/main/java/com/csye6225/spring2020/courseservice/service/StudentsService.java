package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Student;

public class StudentsService {
	private static HashMap<String, Student> stud_Map = InMemoryDatabase.getStudentsDB();

	public StudentsService() {
		
	}
	public Student addStudent(Student student) {
		String id= String.valueOf(InMemoryDatabase.getNextStudentId());
		student.setStudentId(id);
//		student.setStudentId(student.getFirstName()+student.getLastName());
		student.setJoiningDate(new Date().toString());
		stud_Map.put(id,student);
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
