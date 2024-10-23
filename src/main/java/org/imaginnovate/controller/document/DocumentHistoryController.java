package org.imaginnovate.controller.document;

import org.imaginnovate.service.document.DocumentHistoryService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/document-history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DocumentHistoryController {

    @Inject
    DocumentHistoryService documentHistoryService;
    
    // @GET
    // public Response getAllDocumentHistory() {
    //     return  documentHistoryService.getAllDocumentHistory();
    // }

    // @POST
    // public Response createDocumentHistory(DocumentHistoryDto documentHistoryDto) {
    //     return documentHistoryService.createDocumentHistory(documentHistoryDto);
    // }

    // @PUT
    // public Response updateDocumentHistory(@PathParam("id") int id,DocumentHistoryDto documentHistoryDto) {
    //     return documentHistoryService.updateDocumentHistory(id, documentHistoryDto);
    // }

    // @GET
    // @Path("/{id}")
    // public Response getDocumentHistoryById(@PathParam("id") Integer id) {
    //     return documentHistoryService.getDocumentHistoryById(id);
    // }
     @GET
    @Path("/")
    public Response getDocumentHistory(
    @QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber,
    @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
    return documentHistoryService.getDocumentHistoryDetails(pageNumber, pageSize);
}
}
