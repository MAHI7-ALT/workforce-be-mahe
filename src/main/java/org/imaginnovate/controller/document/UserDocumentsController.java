package org.imaginnovate.controller.document;

import org.imaginnovate.dto.documents.userdocument.PostUserDocumentDto;
import org.imaginnovate.service.document.UserDocumentService;

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

@Path("/user-documents")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserDocumentsController {

    @Inject
    UserDocumentService userDocumentsService;

    @GET
    public Response getAllUserDocuments(){
        return userDocumentsService.getAllUserDocuments();
    }

    @POST
    public Response createUserDocuments(PostUserDocumentDto userDocumentsDto){
        return userDocumentsService.createUserDocuments(userDocumentsDto);
    }

    @PUT
    public Response updateUserDocuments(@PathParam("id") int id,PostUserDocumentDto userDocumentsDto){
        return userDocumentsService.updateUserDocuments(id, userDocumentsDto);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserDocumentsById(@PathParam("id") Integer id){
        return userDocumentsService.deleteUserDocuments(id);
    }

    // @GET
    // @Path("/{userId}")
    // public Response getUserDocumentsByUserId(@PathParam("userId") Integer user){
    //     return userDocumentsService.getUserDocumentsByUserId(user);
    // }

    @GET
    @Path("/loggedin-user")
    public Response getAllUserDocuments(
            @QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber,
            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        return userDocumentsService.findAllUserDocumentsByUserId( pageNumber, pageSize);
    }
    @GET
    @Path("/content/{documentId}")
    public Response getUserDocumentContent(
            @PathParam("documentId") Integer documentId,
            @QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber,
            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        return userDocumentsService.getUserDocumentContent( documentId, pageNumber, pageSize);
    }
    @GET
    @Path("/dashboard/{userId}")
    public Response getDocumentCounts(@QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber,
    @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        return userDocumentsService.getDocumentCounts( pageNumber, pageSize);
    }

}
