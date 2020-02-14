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
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture getLecture(@PathParam("lectureId") String lectId) {
		return lectService.getLecture(lectId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture addLecture(Lecture lect) {
		if(lect == null) {
			System.out.println("INVALID INPUT");
			return null;
		}
		return lectService.addLecture(lect);
	}

	@DELETE
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture deleteLecture(@PathParam("lectureId") String lectId) {
		return lectService.deleteLecture(lectId);
	}

	@PUT
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Lecture updateLecture(@PathParam("lectureId") String lectId, Lecture lect) {
		return lectService.updateLecture(lectId,lect);

	}
}
