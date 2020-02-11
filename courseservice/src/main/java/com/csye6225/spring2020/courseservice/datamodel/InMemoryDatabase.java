package com.csye6225.spring2020.courseservice.datamodel;

import java.util.HashMap;

public class InMemoryDatabase {

	private static HashMap<Long, Professor> professorDB = new HashMap<> ();
	private static long nextProfessorId=1;
    private static HashMap<Long, Student> studentsDB = new HashMap<>();

    private static HashMap<String, Program> programsDB = new HashMap<>();

    private static HashMap<String, Course> coursesDB = new HashMap<>();

    private static HashMap<Long, Lecture> lecturesDB = new HashMap<>();
    
	public static HashMap<Long, Professor> getProfessorDB() {
		return professorDB;
	}
	public static long getNextProfessorId() {
		return nextProfessorId++;
	}
	
	public static HashMap<Long, Student> getStudentsDB() {
		return studentsDB;
	}
	
	public static HashMap<String, Program> getProgramsDB() {
		return programsDB;
	}
	
	public static HashMap<String, Course> getCoursesDB() {
		return coursesDB;
	}
	
	public static HashMap<Long, Lecture> getLecturesDB() {
		return lecturesDB;
	}
	
	
}

