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

import com.csye6225.spring2020.courseservice.datamodel.Board;
import com.csye6225.spring2020.courseservice.service.BoardsService;

@Path("boards")
public class BoardsResource {
    BoardsService brdService = new BoardsService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Board> getBoards(@QueryParam("boardId") String brdId, @QueryParam("courseId") String corsId) {
        if(brdId!=null&& corsId==null){
            return brdService.getBoardByBoardId(brdId);
        }
        if (corsId != null && brdId==null) {
            return brdService.getBoardsByCourse(corsId);
        }
        return brdService.getAllBoards();
    }

    @GET
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board getBoard(@PathParam("Id") String Id) {
        return brdService.getBoardbyId(Id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Board addBoard(Board brd) {
        if (brd == null) {
            System.out.println("INVALID INPUT");
            return null;
        }
        return brdService.addBoard(brd);
    }

    @DELETE
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Board deleteBoard(@PathParam("Id") String brdId) {
        return brdService.deleteBoard(brdId);
    }

    @PUT
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Board updateBoard(@PathParam("Id") String brdId, Board brd) {
        return brdService.updateBoard(brdId, brd);

    }
}
