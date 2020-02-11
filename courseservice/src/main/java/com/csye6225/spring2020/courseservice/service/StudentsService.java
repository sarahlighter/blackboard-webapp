package com.csye6225.spring2020.courseservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.csye6225.spring2020.courseservice.datamodel.Student;

public class StudentsService {
	private Map<String,Student> studentMap;
	public StudentsService() {
		studentMap=new HashMap<>();
	}
	public Student addStudent(Student student) {
		studentMap.put(student.getStudentId(),student);
		return student;
	}
	public Student getStudent(String studentId) {
		return studentMap.get(studentId);
	}
	public Student updateStudent(String studentId, Student student) {
		//TODO map to database
		Student oldStudent = studentMap.get(studentId);
		if(oldStudent == null || !verifyStudent(student)) {
			return null;
		}
		student.setId(oldStudent.getId());
		student.setStudentId(oldStudent.getStudentId());
		//TODO map to database
		studentMap.put(studentId, student);
		
		return getStudent(studentId);
	}
	
	public Student deleteStudent(String studentId) {
		final Student oldStudent=getStudent(studentId);
		if(oldStudent == null)
			return null;
		return oldStudent;
	}
	
	private boolean verifyStudent(final Student student)
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
