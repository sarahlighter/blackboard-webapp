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
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice.datamodel.Lecture;
import com.csye6225.spring2020.courseservice.service.LecturesService;

@Path("lectures")
public class LecturesResource {
	LecturesService lectService = new LecturesService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Lecture> getLectures(){
		return lectService.getAllLectures();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture getLecture(@PathParam("id") String lectId) {
		System.out.println("Lecture Resource: Looking for: "+ lectId);
		return lectService.getLecture(lectId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture addLecture(Lecture lect) {
		if(lect == null) {
			return null;
		}
		return lectService.addLecture(lect);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture deleteLecture(@PathParam("id") String lectId) {
		return lectService.deleteLecture(lectId);
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture updateLecture(@PathParam("id") String lectId, Lecture lect) {
		return lectService.updateLecture(lectId,lect);

	}
}
