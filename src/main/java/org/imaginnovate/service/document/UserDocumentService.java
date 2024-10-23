package org.imaginnovate.service.document;

import java.time.LocalDateTime;
import java.util.List;

import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.documents.userdocument.GetUserDocumentContentDto;
import org.imaginnovate.dto.documents.userdocument.GetUserDocumentDashboardDto;
import org.imaginnovate.dto.documents.userdocument.GetUserDocumentDto;
import org.imaginnovate.dto.documents.userdocument.PostUserDocumentDto;
import org.imaginnovate.dto.documents.userdocument.UserDocumentDto;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.document.DocumentStatus;
import org.imaginnovate.entity.document.Documents;
import org.imaginnovate.entity.document.UserDocuments;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.document.DocumentStatusRepo;
import org.imaginnovate.repository.document.DocumentsRepo;
import org.imaginnovate.repository.document.UserDocumentsRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserDocumentService {

    @Inject
    DocumentsRepo documentsRepo;

    @Inject
    UserRepo usersRepo;

    @Inject 
    DocumentStatusRepo documentStatusRepo;

    @Inject
    UserDocumentsRepo userDocumentsRepo;

    @Inject 
    private AuthService authService;

    @Transactional
    public Response getAllUserDocuments() {
       try {
            List<GetUserDocumentDto> userDocumentsDto = userDocumentsRepo.getAllUserDocuments();
            if (userDocumentsDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Documents not found").build();
            } else {
                return Response.ok(userDocumentsDto).build();
            }
         } catch (Exception e) {
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
         }
    } 

    @Transactional
    public Response createUserDocuments(PostUserDocumentDto userDocumentDto) {
        try {
            User user = usersRepo.findById(userDocumentDto.getUser());
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
    
            Documents documents = documentsRepo.findById(userDocumentDto.getDocument());
            if (documents == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Document not found").build();
            }
    
           Integer currentUserId = authService.getCurrentUserId();
        User createdBy = null;
        if (currentUserId != null) {
            createdBy = usersRepo.findById(currentUserId);
            if (createdBy == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("CreatedBy user not found")
                        .build();
            }

            boolean isAuthorizedToCreateByUser = usersRepo.isAuthorizedToCreate(currentUserId);
            if (!isAuthorizedToCreateByUser) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("This user does not have the rights to create")
                        .build();
            }
        }
            UserDocuments userDocuments = new UserDocuments();
            userDocuments.setId(userDocumentDto.getId());
            userDocuments.setUser(user);
            userDocuments.setDocument(documents);
    
            DocumentStatus status = documentStatusRepo.findById(userDocumentDto.getStatus());
            if (status == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("DocumentStatus not found.").build();
            }
    
            userDocuments.setStatus(status);
            userDocuments.setAssignedOn(LocalDateTime.now());
            userDocuments.setTargetDate(userDocumentDto.getTargetDate());
            userDocuments.setCreatedBy(createdBy);
            userDocuments.setCreatedAt(LocalDateTime.now());
            userDocumentsRepo.persist(userDocuments);
            userDocumentDto.setId(userDocuments.getId());
    
            return Response.ok(userDocumentDto).build();
    
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }
    

    @Transactional
    public Response updateUserDocuments(int id ,PostUserDocumentDto userDocumentDto) {
        try {
            UserDocuments userDocuments = userDocumentsRepo.findById(id);
            if (userDocuments == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("UserDocuments not found").build();
            }

            User user = usersRepo.findById(userDocumentDto.getUser());
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }

            Documents documents = documentsRepo.findById(userDocumentDto.getDocument());
            if (documents == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Document not found").build();
            }
            
            User modifiedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                modifiedBy = usersRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ModifiedBy user not found")
                            .build();
                }
            }

            userDocuments = new UserDocuments();
            userDocuments.setId(userDocumentDto.getId());
            userDocuments.setUser(user);
            userDocuments.setDocument(documents);
            userDocuments.setCreatedAt(LocalDateTime.now());
            userDocuments.setModifiedAt(LocalDateTime.now());
            userDocuments.setModifiedBy(modifiedBy);
            
            userDocumentsRepo.persist(userDocuments);
            userDocumentDto.setId(userDocuments.getId());
            return Response.ok(userDocumentDto).build();
    
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response deleteUserDocuments(Integer id) {
        try {
            UserDocuments userDocuments = userDocumentsRepo.findById(id);
            if (userDocuments == null || userDocuments.getDeletedAt() != null) {
                return Response.status(Response.Status.NOT_FOUND).entity("UserDocuments not found").build();
            }

            User deletedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                deletedBy = usersRepo.findById(currentUserId);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("DeletedBy user not found.").build();
                }
                boolean canApprove = usersRepo.isAuthorizedToCreate(currentUserId);
                if (!canApprove) {
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("This user does not have deletedBy rights")
                            .build();
                }
            }
    
            userDocuments.setDeletedBy(userDocuments.getDeletedBy());
            userDocuments.setDeletedAt(LocalDateTime.now());
            userDocumentsRepo.persist(userDocuments);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    public Response getAllUserDocumentsByUserId(Integer  user) {
       // try {
            List<UserDocumentDto> userDocuments = userDocumentsRepo.findByUserId(user);
            if (userDocuments == null||userDocuments.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("UserDocuments not found").build();
            }
            return Response.status(Response.Status.OK).entity(userDocuments).build();
        // } catch (Exception e) {
        //     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        // }
    }


    @Transactional
    public Response findAllUserDocumentsByUserId(int pageNumber, int pageSize) {
        Integer currentUserId = authService.getCurrentUserId(); 
        if (currentUserId == null ) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authenticated.").build();
        }
    
        List<GetUserDocumentDto> result = userDocumentsRepo.findAllUserDocumentsByUserId(currentUserId, pageNumber, pageSize);
        
        if (result == null ) {
            return Response.status(Response.Status.OK).entity(result).build();
        }
        
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    @Transactional
    public Response getUserDocumentContent(int documentId, int pageNumber, int pageSize) {
        Integer currentUserId = authService.getCurrentUserId();
    
        if (currentUserId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authenticated.").build();
        }
        GetUserDocumentContentDto result;
        try {
            result = userDocumentsRepo.findUserDocumentContentByUserIdAndDocumentId(currentUserId, documentId, pageNumber, pageSize);
        } catch (NoResultException e) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    @Transactional
    public Response getDocumentCounts(int pageNumber, int pageSize) {
        Integer currentUserId = authService.getCurrentUserId();
    
        if (currentUserId == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not authenticated.").build();
        }
        GetUserDocumentDashboardDto result = userDocumentsRepo.findDocumentCounts(currentUserId, pageNumber, pageSize);
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
}
