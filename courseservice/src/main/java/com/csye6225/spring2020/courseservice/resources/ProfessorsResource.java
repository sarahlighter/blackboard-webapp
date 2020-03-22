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
    public List<Professor> getProfessors(@QueryParam("professorId") String profId, @QueryParam("department") String depart, @QueryParam("year") String year) {
        if(profId !=null && depart==null && year==null){
            return profService.getProfessorByProfessorId(profId);
        }else if(depart!=null && year!=null){
            return profService.getProfessorByDepartmentAndYear(depart,year);
        }
        else if(depart !=null && year == null){
            return profService.getProfessorsByDepartment(depart);
        }
        else if(depart ==null && year !=null){
            return profService.getProfessorByYear(year);
        }
        else{
            return profService.getAllProfessors();
        }
    }

    // ... webapi/professor/1
    @GET
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Professor getProfessor(@PathParam("Id") String Id) {
        return profService.getProfessor(Id);
    }

    @DELETE
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Professor deleteProfessor(@PathParam("Id") String profId) {
        return profService.deleteProfessor(profId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Professor addProfessor(Professor prof) {
        if (prof == null) {
            System.out.println("INVALID INPUT");
            return null;
        }
        return profService.addProfessor(prof);
    }

    @PUT
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Professor updateProfessor(@PathParam("Id") String Id,
                                     Professor prof) {
        return profService.updateProfessorInformation(Id, prof);
    }


}