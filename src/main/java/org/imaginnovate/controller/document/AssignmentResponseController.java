package org.imaginnovate.controller.document;

import org.imaginnovate.dto.documents.AssignmentResponseDto;
import org.imaginnovate.service.document.AssignmentResponseService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/assignment-responses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssignmentResponseController {

    @Inject 
    AssignmentResponseService assignmentResponseService;

    @GET
    public Response getAllAssignmentResponses() {
        return assignmentResponseService.getAllAssignmentResponses();
    }

    @POST
    public Response createAssignmentResponse(AssignmentResponseDto assignmentResponseDto) {
        return assignmentResponseService.createAssignmentResponse(assignmentResponseDto);
    }

    @PUT
    @Path("{id}")
    public Response updateAssignmentResponse(@PathParam("id") int id, AssignmentResponseDto assignmentResponseDto) {
        return assignmentResponseService.updateAssignmentResponse(id, assignmentResponseDto);
    }

}
