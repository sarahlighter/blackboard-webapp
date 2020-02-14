package com.csye6225.spring2020.courseservice.resources;
import java.util.Date;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice.datamodel.Student;
import com.csye6225.spring2020.courseservice.service.StudentsService;

//.. /webapi/professors
@Path("students")
public class StudentsResource {
	StudentsService stdService = new StudentsService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudents(){
		return stdService.getAllStudent();
	}

	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("studentId") String stdId) {
		Student std=stdService.getStudent(stdId);
		if(std == null) {
			System.out.println("Cannot find student with studentId:"+stdId);
		}
		return std;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student addStudent(Student std) {
		if(std == null) {
			System.out.println("INVALID INPUT");
			return null;
		}
		return stdService.addStudent(std);
	}

	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") String stdId) {
		return stdService.deleteStudent(stdId);
	}

	@PUT
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student updateStudent(@PathParam("studentId") String stdId, Student std) {	
		return stdService.updateStudent(stdId, std);

	}
}
