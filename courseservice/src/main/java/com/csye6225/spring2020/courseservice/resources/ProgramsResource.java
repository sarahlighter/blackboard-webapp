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
    public List<Program> getPrograms() {
        return pgmService.getAllPrograms();
    }

    @GET
    @Path("/{programId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Program getProgram(@PathParam("programId") String pgmId) {
        return pgmService.getProgram(pgmId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Program addProgram(Program prg) {
        if (prg == null) {
            System.out.println("INVALID INPUT");
            return null;
        }
        return pgmService.addProgram(prg);
    }

    @DELETE
    @Path("/{programId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Program deleteProgram(@PathParam("programId") String prgId) {
        return pgmService.deleteProgram(prgId);
    }

    @PUT
    @Path("/{programId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Program updateProgram(@PathParam("programId") String prgId, Program prg) {
        return pgmService.updateProgram(prgId, prg);
    }
}
