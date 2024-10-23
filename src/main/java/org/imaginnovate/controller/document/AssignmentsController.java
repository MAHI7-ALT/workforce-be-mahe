package org.imaginnovate.controller.document;

import org.imaginnovate.dto.documents.assignment.AssignmentDto;
import org.imaginnovate.dto.documents.assignment.AssignmentPostDto;
import org.imaginnovate.service.document.AssignmentsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/assignments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AssignmentsController {

    @Inject
    AssignmentsService assignmentsService;

    @GET
    public Response getAllAssignments() {
        return assignmentsService.getAllAssignments();
    }

    @POST
    public Response createAssignment(AssignmentPostDto assignmentDto) {
        return assignmentsService.createAssignment(assignmentDto);
    }

    @PUT
    public Response updateAssignment(@PathParam("id") int id,AssignmentDto assignmentDto) {
        return assignmentsService.updateAssignment(id, assignmentDto);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAssignmentById(@PathParam("id") Integer id) {
        return assignmentsService.deleteAssignment(id);
    }

    @GET
    @Path("/{id}")
    public Response getAssignmentById(@PathParam("id") Integer id) {
        return assignmentsService.getAssignmentById(id);
    }

    // @GET
    // @Path("/quiz/{id}")
    // public List<QuestionsDto> startQuiz(@PathParam("id") Integer id) {
    //     return assignmentsService.startQuiz(id);
    // }

   
}
