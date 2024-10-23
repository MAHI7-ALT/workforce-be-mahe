package org.imaginnovate.service.document;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.dto.documents.DocumentHistoryDto;
import org.imaginnovate.repository.document.DocumentHistoryRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class DocumentHistoryService {
     private final Logger logger=LogManager.getLogger(getClass());


    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private DocumentHistoryRepo documentHistoryRepo;


    public Response getDocumentHistoryDetails(Integer pageNumber, Integer pageSize) {
        try {
            List<DocumentHistoryDto> documents = documentHistoryRepo.getAllDocumentHistoryDetails(pageNumber, pageSize);
            return Response.ok(documents).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error.")
                    .build();
        }
    }

}