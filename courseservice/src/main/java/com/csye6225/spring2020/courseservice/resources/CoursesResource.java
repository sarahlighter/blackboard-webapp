package com.csye6225.spring2020.courseservice.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice.datamodel.Course;
import com.csye6225.spring2020.courseservice.service.CoursesService;

@Path("courses")
public class CoursesResource {
	CoursesService courService = new CoursesService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getCourses(){
		return courService.getAllCourses();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("id") String courId) {
		System.out.println("Course Resource: Looking for: "+ courId);
		return courService.getCourse(courId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course addCourse(Course cour) {
		if(cour == null) {
			return null;
		}
		return courService.addCourse(cour);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Course updateCourse(@PathParam("id") String courId, Course cour) {
		return courService.updateCourse(courId, cour);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("id") String courId) {
		return courService.deleteCourse(courId);
	}
	
}
