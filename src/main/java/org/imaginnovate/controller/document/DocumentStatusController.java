package org.imaginnovate.controller.document;

import org.imaginnovate.entity.document.DocumentStatus;
import org.imaginnovate.service.document.DocumentStatusService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/documentstatus")
public class DocumentStatusController {

    @Inject
    DocumentStatusService documentStatusService;

    @GET
    public Response getAllDocumentStatus() {
        return documentStatusService.getAllDocumentStatus();
    }

    @POST
    public Response createDocumentStatus(DocumentStatus documentStatus) {
        return documentStatusService.createDocumentStatus(documentStatus);
    }

    @GET 
    @Path("/{id}")
    public Response getDocumentStatusById(@PathParam("id") Integer id) {
        return documentStatusService.getDocumentStatusById(id);
    }
    
}
