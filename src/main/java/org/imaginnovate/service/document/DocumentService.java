package org.imaginnovate.service.document;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.documents.document.DocumentPostDto;
import org.imaginnovate.dto.documents.document.DocumentPutDto;
import org.imaginnovate.dto.documents.document.DocumentsDto;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.document.DocumentType;
import org.imaginnovate.entity.document.Documents;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.document.DocumentHistoryRepo;
import org.imaginnovate.repository.document.DocumentTypeRepo;
import org.imaginnovate.repository.document.DocumentsRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class DocumentService {

    private final Logger logger = LogManager.getLogger(getClass());

    @Inject
    private DocumentsRepo documentsRepo;

    @Inject
    private DocumentTypeRepo documentTypeRepo;

    @Inject
    private DocumentHistoryRepo documentHistoryRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AuthService authService;

    @PersistenceContext
    EntityManager entityManager;

    public Response getDocumentDetails(Integer pageNumber, Integer pageSize) {
        try {
            List<DocumentsDto> documents = documentsRepo.getAllDocumentDetails(pageNumber, pageSize);
            return Response.ok(documents).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error.")
                    .build();
        }
    }

    public Response getDocumentById(Integer id) {
        try {
            Documents document = documentsRepo.findById(id);
            if (document == null) {
                throw new NotFoundException("Document not found ");
            }
            DocumentsDto documentDto = new DocumentsDto();
            documentDto.setId(document.getId());
            documentDto
                    .setDocumentType(document.getDocumentType() != null ? document.getDocumentType().getName() : null);
            documentDto.setSerialNo(document.getSerialNo());
            documentDto.setTitle(document.getTitle());
            documentDto.setContent(document.getContent());
            documentDto.setIsRecurring(document.getIsRecurring());
            documentDto.setRecurringType(document.getRecurringType());
            documentDto.setRecurringTimeFrame(document.getRecurringTimeFrame());
            documentDto.setRecurringFixedOn(document.getRecurringFixedOn());
            documentDto.setCreatedAt(document.getCreatedAt());
            documentDto.setCreatedBy(document.getCreatedBy().getName());
            return Response.status(Response.Status.OK).entity(documentDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server Error")
                    .build();
        }
    }

    @Transactional
    public Response createDocument(DocumentPostDto documentDto) {
        try {
            String title = documentDto.getTitle();
            Long count = (Long) entityManager
                    .createNativeQuery("SELECT COUNT(*) FROM dc_documents WHERE title = :title")
                    .setParameter("title", title).getSingleResult();
            if (count > 0) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Document with the given title already exists.").build();
            }

            DocumentType docType = entityManager.find(DocumentType.class, documentDto.getDocumentType());
            if (docType == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid DocumentType ").build();
            }
            Documents document = new Documents();
            document.setSerialNo(documentDto.getSerialNo());
            document.setTitle(documentDto.getTitle());
            document.setContent(documentDto.getContent());
            document.setIsRecurring(documentDto.getIsRecurring());
            document.setRecurringType(documentDto.getRecurringType());
            document.setRecurringTimeFrame(documentDto.getRecurringTimeFrame());
            document.setRecurringFixedOn(documentDto.getRecurringFixedOn());
            document.setDocumentType(docType);
            User createdBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                createdBy = userRepo.findById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("CreatedBy user not found")
                            .build();
                }

                boolean isAuthorizedToCreateByUser = userRepo.isAuthorizedToCreate(currentUserId);
                if (!isAuthorizedToCreateByUser) {
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("This user does not have the rights to create")
                            .build();
                }
            }
            document.setCreatedBy(createdBy);
            document.setCreatedAt(LocalDateTime.now());
            documentsRepo.persist(document);
            return Response.status(Response.Status.CREATED).entity(documentDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error.")
                    .build();
        }
    }

    @Transactional
    public Response updateDocumentById(Integer id, DocumentPutDto updatedDocument) {
        try {
            Documents existingDocument = documentsRepo.findById(id);
            if (existingDocument == null) {
                return Response.status(Status.NOT_FOUND).entity("Document not found.").build();
            }

            Documents originalDocuments = documentHistoryRepo.originalDocument(existingDocument);

            if (updatedDocument.getSerialNo() != null)
                existingDocument.setSerialNo(existingDocument.getSerialNo());
            if (updatedDocument.getTitle() != null)
                existingDocument.setTitle(updatedDocument.getTitle());
            if (updatedDocument.getContent() != null)
                existingDocument.setContent(updatedDocument.getContent());
            if (updatedDocument.getDocumentType() != null) {
                DocumentType documentType = documentTypeRepo.findById(updatedDocument.getDocumentType());
                if (documentType != null) {
                    existingDocument.setDocumentType(documentType);
                } else {
                    return Response.status(Status.NOT_FOUND).entity("Document type not found.").build();
                }
            }
            if (updatedDocument.getIsRecurring() != null)
                existingDocument.setIsRecurring(updatedDocument.getIsRecurring());
            if (updatedDocument.getRecurringType() != null)
                existingDocument.setRecurringType(updatedDocument.getRecurringType());
            if (updatedDocument.getRecurringTimeFrame() != null)
                existingDocument.setRecurringTimeFrame(updatedDocument.getRecurringTimeFrame());
            if (updatedDocument.getRecurringFixedOn() != null)
                existingDocument.setRecurringFixedOn(updatedDocument.getRecurringFixedOn());

            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User createdBy = userRepo.findById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("CreatedBy user not found").build();
                }

                boolean isAuthorizedToCreateByUser = userRepo.isAuthorizedToCreate(currentUserId);
                if (!isAuthorizedToCreateByUser) {
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("This user does not have the rights to create").build();
                }

                existingDocument.setCreatedBy(createdBy);
            }
            existingDocument.setCreatedAt(LocalDateTime.now());
            documentsRepo.persist(existingDocument);
            documentHistoryRepo.insertDocumentHistory(originalDocuments);

            DocumentPutDto responseDto = new DocumentPutDto();
            responseDto.setId(existingDocument.getId());
            responseDto.setSerialNo(existingDocument.getSerialNo());
            responseDto.setTitle(existingDocument.getTitle());
            responseDto.setContent(existingDocument.getContent());
            responseDto.setDocumentType(existingDocument.getDocumentType().getId());
            responseDto.setIsRecurring(existingDocument.getIsRecurring());
            responseDto.setRecurringType(existingDocument.getRecurringType());
            responseDto.setRecurringTimeFrame(existingDocument.getRecurringTimeFrame());
            responseDto.setRecurringFixedOn(existingDocument.getRecurringFixedOn());
            return Response.status(Response.Status.OK).entity(responseDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }

    @Transactional
    public Response deleteDocumentById(Integer id) {
        try {
            Documents document = documentsRepo.findById(id);
            if (document.getDeletedBy() != null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Document not found.").build();
            }
            User deletedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                deletedBy = userRepo.findByIdOptional(currentUserId).orElse(null);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("DeletedBy user not found").build();
                }

                boolean canApprove = userRepo.isAuthorizedToCreate(currentUserId);
                if (!canApprove) {
                    return Response.status(Response.Status.FORBIDDEN).entity("User does not have deletedBy rights")
                            .build();
                }

                document.setDeletedBy(deletedBy);
            }

            document.setDeletedAt(LocalDateTime.now());
            documentsRepo.persist(document);
            return Response.status(Response.Status.OK)
                    .entity("Document successfully deleted.").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error..").build();
        }
    }

}
