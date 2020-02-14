package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;

public class CoursesService {
	private static HashMap<String, Course> cour_Map = InMemoryDatabase.getCoursesDB();
	
	public CoursesService() {}

	public List<Course> getAllCourses(){
		ArrayList<Course> allCourses = new ArrayList<>();
		for(Course c:cour_Map .values()) {
			allCourses.add(c);
		}
		return allCourses;
	}
 	public Course getCourse(String courseId) {
 		if(!isExist(courseId)) {
 			return null;
 		}
		Course cors=cour_Map.get(courseId);
		return cors;
	}
	public Course addCourse(Course cor) {
		if(isExist(cor.getCourseId())||!isValid(cor)) {
			return null;
		}
		cour_Map.put(cor.getCourseId(),cor);
		return cor;
	}

	public Course deleteCourse(String courseId) {
		if(!isExist(courseId)) {
			return null;
		}
		Course oldCourse=cour_Map.get(courseId);
		cour_Map.remove(courseId);
		return oldCourse;
	}

	public Course updateCourse(String courseId, Course cor) {
		if(!isValid(cor)) {
			return null;
		}
		Course oldCourse=cour_Map.get(courseId);
		cor.setCourseId(oldCourse.getCourseId());
		cour_Map.put(courseId,cor);
		return cor;
	}
	
	public boolean isExist(String courseId) {
		return cour_Map.containsKey(courseId);
	}
	public boolean isValid(Course cors) {
		if(!new ProfessorsService().isExist(cors.getProfessorId())) return false;
		if(!new ProgramsService().isExist(cors.getProgramId())) return false;
		return true;
	}
}
