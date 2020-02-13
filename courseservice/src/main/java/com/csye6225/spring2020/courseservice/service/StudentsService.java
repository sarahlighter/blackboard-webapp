package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Professor;
import com.csye6225.spring2020.courseservice.datamodel.Program;
import com.csye6225.spring2020.courseservice.datamodel.Student;

public class StudentsService {
	private static HashMap<String, Student> stud_Map = InMemoryDatabase.getStudentsDB();
    private static Map<String, Course> courseMap = InMemoryDatabase.getCoursesDB();
    private static Map<String, Program> programMap = InMemoryDatabase.getProgramsDB();
	public StudentsService() {
		
	}
	public Student addStudent(Student student) {
		String id= String.valueOf(InMemoryDatabase.getNextStudentId());
		student.setId(id);
		student.setStudentId(student.getFirstName()+student.getLastName());
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
	public Student getStudent(String id) {
		Student std=stud_Map.get(id);
		System.out.println("Item retrieved:");
		if(std != null) 
			System.out.println(std.toString());
		return std;
	}
	public Student updateStudent(String id, Student student) {
		//TODO map to database
		Student oldStudent = stud_Map.get(id);
		if(oldStudent == null ) {
			return null;
		}
		String studentId = oldStudent.getId();
		student.setId(studentId);
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
	
	private boolean verifyStudent(Student student)
    {
        student.setId(null);
        if (student.getCoursesEnrolled() != null && student.getCoursesEnrolled().isEmpty())
        {
            student.setCoursesEnrolled(null);
        }
        else if (student.getCoursesEnrolled() != null && student.getCoursesEnrolled().size() > 3)
        {
            return false;
        }

        if (student.getStudentId() != null && student.getStudentId().isEmpty())
        {
            return false;
        }
        
        //TODO check existCourses
//        final Set<String> registeredCourses = student.getCoursesEnrolled();
//        if (registeredCourses != null && !registeredCourses.isEmpty() && existedCourses(registeredCourses).size() != registeredCourses.size()){
//            return false;
//        }
        return true;
    }
}
