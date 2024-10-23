package org.imaginnovate.controller.document;

import org.imaginnovate.entity.document.DocumentType;
import org.imaginnovate.service.document.DocumentTypeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/documentTypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentTypeController {

    @Inject 
    DocumentTypeService documentTypeService;

    @GET
    public Response getAllDocumentTypes (){
        return documentTypeService.getAllDocumentTypes();
    }

    @POST
    public Response createDocumentType(DocumentType documentType) {
        return documentTypeService.createDocumentType(documentType);
    }

  

}
