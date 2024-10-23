package org.imaginnovate.service.document;

import java.util.List;

import org.imaginnovate.entity.document.DocumentType;
import org.imaginnovate.repository.document.DocumentTypeRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class DocumentTypeService {

    @Inject
    DocumentTypeRepo documentTypeRepo;

    public Response getAllDocumentTypes() {
        try {
            List<DocumentType> documentTypes = documentTypeRepo.findAlllDocumentTypes();
            if (documentTypes == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Document type not found").build();
            } else {
                return Response.ok(documentTypes).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }



    @Transactional
    public Response createDocumentType(DocumentType documentType) {
        try {
            if (documentType == null || documentType.getName() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid document type data provided.")
                        .build();
            }
            documentTypeRepo.persist(documentType);
            return Response.status(Response.Status.OK)
                    .entity(documentType)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }
}
