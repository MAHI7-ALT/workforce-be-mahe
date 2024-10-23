
package org.imaginnovate.service.document;

import java.util.List;

import org.imaginnovate.entity.document.DocumentStatus;
import org.imaginnovate.repository.document.DocumentStatusRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class DocumentStatusService {

    @Inject
    DocumentStatusRepo documentStatusRepo;


    @Transactional
    public Response getAllDocumentStatus(){
        try{
            List<DocumentStatus> documentStatus = documentStatusRepo.getAllDocumentStatus();
            if (documentStatus==null){
                return Response .status (Response.Status.NOT_FOUND).entity("DocumentStatus not found").build();
            }else {
                return Response.ok(documentStatus).build();
            }
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }
   
    
    @Transactional
    public Response createDocumentStatus(DocumentStatus documentStatus){
        try{
            if (documentStatus != null){
                DocumentStatus documentStatus1 = documentStatusRepo.findById(documentStatus.getId());
                if (documentStatus1 != null){
                    return Response.status(Response.Status.CONFLICT).entity("DocumentStatus already exists").build();
                }
            }
            DocumentStatus documentStatus2 = new DocumentStatus();
            documentStatus2.setId(documentStatus.getId());
            documentStatus2.setName(documentStatus.getName());
            documentStatusRepo.persist(documentStatus);
            documentStatus2.setId(documentStatus.getId());
            return Response.ok(documentStatus).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    public Response getDocumentStatusById(int id ){
        try{
            DocumentStatus documentStatus = documentStatusRepo.findById(id);
            if (documentStatus==null){
                return Response .status (Response.Status.NOT_FOUND).entity("DocumentStatus not found").build();
            }else {
                return Response.ok(documentStatus).build();
            }
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }
}
