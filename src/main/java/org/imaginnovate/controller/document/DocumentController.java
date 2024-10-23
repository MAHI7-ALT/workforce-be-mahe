package org.imaginnovate.controller.document;

import org.imaginnovate.dto.documents.document.DocumentPostDto;
import org.imaginnovate.dto.documents.document.DocumentPutDto;
import org.imaginnovate.service.document.DocumentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/document")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DocumentController {

    @Inject
    DocumentService documentService;
    @GET
    @Path("/")
    public Response getDocumentDetails(
    @QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber,
    @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
    return documentService.getDocumentDetails(pageNumber, pageSize);
}
    @GET
    @Path("/{id}")
    public Response getDocumentById(@PathParam("id") Integer id) {
       return documentService.getDocumentById(id);
    }
    @POST
    public Response createDocument(DocumentPostDto documentDto) {
        return documentService.createDocument(documentDto);
    }
    @PUT
    @Path("/{id}")
    public Response updateDocument(@PathParam("id") int id, DocumentPutDto documentPutDto) {
        return documentService.updateDocumentById(id, documentPutDto);
    }
    @DELETE
    @Path("/{id}")
    public Response deleteDocument(@PathParam("id") int id) {
        return documentService.deleteDocumentById(id);
    }
}