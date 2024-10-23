package org.imaginnovate.controller.document;


import org.imaginnovate.dto.documents.AssignmentResultDto;
import org.imaginnovate.service.document.AssignmentResultService;

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

@Path("/assignmentresults")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AssignmentResultController {

    @Inject 
    AssignmentResultService assignmentResultService;
   
    @GET
    public Response getAllAssignmentResults() {
        return assignmentResultService.getAllAssignmentResults();
    }

    @POST
    public Response createAssignmentResult(AssignmentResultDto assignmentResultDto) {
        return assignmentResultService.createAssignmentResult(assignmentResultDto);
    }

    @PUT
    public Response updateAssignmentResult(@PathParam("id") int id, AssignmentResultDto assignementResultDto) {
        return assignmentResultService.updateAssignmentResult(id, assignementResultDto);
    }


}
