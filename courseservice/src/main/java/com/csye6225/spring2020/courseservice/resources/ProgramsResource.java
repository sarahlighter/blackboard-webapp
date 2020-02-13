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
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice.datamodel.Program;
import com.csye6225.spring2020.courseservice.service.ProgramsService;

@Path("programs")
public class ProgramsResource {
	ProgramsService pgmService = new ProgramsService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Program> getPrograms(){
		return pgmService.getAllPrograms();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Program getProgram(@PathParam("id") String pgmName) {
		System.out.println("Program Resource: Looking for: "+ pgmName);
		return pgmService.getProgram(pgmName);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Program addProgram(Program lect) {
		if(lect == null) {
			return null;
		}
		return pgmService.addProgram(lect);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Program deleteProgram(@PathParam("id") String lectId) {
		return pgmService.deleteProgram(lectId);
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Program updateProgram(@PathParam("id") String lectId, Program lect) {
		return pgmService.updateProgram(lectId,lect);

	}
}
