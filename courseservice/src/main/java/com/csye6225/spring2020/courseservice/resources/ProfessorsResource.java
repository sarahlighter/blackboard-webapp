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
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2020.courseservice.datamodel.Professor;
import com.csye6225.spring2020.courseservice.service.ProfessorsService;

// .. /webapi/professors
@Path("professors")
public class ProfessorsResource {

	ProfessorsService profService = new ProfessorsService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Professor> getProfessors(@QueryParam("department") String depart) {		

		if (depart == null) {
			return profService.getAllProfessors();
		}
		return profService.getProfessorsByDepartment(depart);

	}

	// ... webapi/professor/1 
	@GET
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor getProfessor(@PathParam("professorId") String profId) {
		return profService.getProfessor(profId);
	}

	@DELETE
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("professorId") String profId) {
		return profService.deleteProfessor(profId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor addProfessor(Professor prof) {
		if (prof == null){
			System.out.println("INVALID INPUT");
			return null;
		}
		return profService.addProfessor(prof);
	}

	@PUT
	@Path("/{professorId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("professorId") String profId, 
			Professor prof) {
		return profService.updateProfessorInformation(profId, prof);
	}

	public void addProfessor(String firstName, String lastName, String department, Date joiningDate) {
		profService.addProfessor(firstName, lastName, department, joiningDate);
	}
}