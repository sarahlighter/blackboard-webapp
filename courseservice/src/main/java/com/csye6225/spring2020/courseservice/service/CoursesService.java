package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		Course cors=cour_Map .get(courseId);
		System.out.println("Item retrieved:");
		System.out.println(cors.toString());
		return cors;
	}
	public Course addCourse(Course cour) {
		cour_Map.put(cour.getCourseId(),cour);
		return cour;
	}

	public Course deleteCourse(String courseId) {
		Course cour=getCourse(courseId);
		cour_Map.remove(courseId);
		return cour;
	}

	public Course updateCourse(String courseId, Course cour) {
		Course oldCourse=cour_Map .get(courseId);
		cour.setCourseId(oldCourse.getCourseId());
		cour_Map .put(courseId,cour);
		return cour;
	}
}
