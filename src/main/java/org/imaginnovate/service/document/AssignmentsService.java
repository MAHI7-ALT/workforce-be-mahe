package org.imaginnovate.service.document;

import java.time.LocalDateTime;
import java.util.List;

import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.documents.assignment.AssignmentDto;
import org.imaginnovate.dto.documents.assignment.AssignmentPostDto;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.document.Assignments;
import org.imaginnovate.entity.document.Documents;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.document.AssignmentsRepo;
import org.imaginnovate.repository.document.DocumentsRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AssignmentsService {

    @Inject
    private AssignmentsRepo assignmentsRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AuthService authService;

    @Inject
    private DocumentsRepo documentsRepo;

    public Response getAllAssignments() {
        // try {
        List<AssignmentDto> assignmentsDto = assignmentsRepo.findAllAssignments();
        if (assignmentsDto.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No assignments found").build();
        }
        return Response.ok(assignmentsDto).build();
        // } catch (Exception e) {
        // return
        // Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal
        // Server error").build();
        // }
    }

    @Transactional
    public Response createAssignment(AssignmentPostDto assignmentDto) {
        try {
            if (assignmentDto.getId() != null) {
                Assignments existingAssignment = assignmentsRepo.findById(assignmentDto.getId());
                if (existingAssignment != null) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("The assignment already exists")
                            .build();
                }
            }

            Documents document = documentsRepo.findById(assignmentDto.getDocument());
            if (document == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Document not found")
                        .build();
            }

            Assignments assignment = new Assignments();
            assignment.setDocument(document);
            assignment.setTitle(assignmentDto.getTitle());
            assignment.setTimeLimit(assignmentDto.getTimeLimit());
            assignment.setPassingPercentage(assignmentDto.getPassingPercentage());
            assignment.setMaxQuestions(assignmentDto.getMaxQuestions());
            assignment.setStatus(assignmentDto.getStatus());
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
            assignment.setCreatedBy(createdBy);
            assignment.setCreatedAt(LocalDateTime.now());
            assignmentsRepo.persist(assignment);
            assignmentDto.setId(assignment.getId());
            return Response.ok(assignmentDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response updateAssignment(int id, AssignmentDto assignmentDto) {
        try {
            Assignments assignment = assignmentsRepo.findById(id);
            if (assignment == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }
            Documents document = documentsRepo.findById(assignmentDto.getDocument());
            if (document == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Document not found").build();
            }
            if (assignment.getTitle() != null) {
                assignment.setTitle(assignmentDto.getTitle());
            }
            if (assignment.getTimeLimit() != null) {
                assignment.setTimeLimit(assignmentDto.getTimeLimit());
            }
            if (assignment.getPassingPercentage() != null) {
                assignment.setPassingPercentage(assignmentDto.getPassingPercentage());
            }
            if (assignment.getStatus() != null) {
                assignment.setStatus(assignmentDto.getStatus());
            }
            if (assignment.getMaxQuestions() != null) {
                assignment.setMaxQuestions(assignmentDto.getMaxQuestions());
            }

            assignment.setDocument(document);
            assignment.setId(assignmentDto.getId());

            assignmentsRepo.persist(assignment);
            assignmentDto.setId(assignment.getId());
            return Response.ok(assignmentDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response deleteAssignment(int id) {
        try {
            Assignments assignment = assignmentsRepo.findById(id);
            if (assignment == null || assignment.getDeletedAt() != null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }
            assignment.setDeletedBy(assignment.getDeletedBy());
            assignment.setDeletedAt(LocalDateTime.now());
            assignmentsRepo.persist(assignment);
            return Response.ok("Assignment deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    public Response getAssignmentById(int id) {
        try {
            Assignments assignment = assignmentsRepo.findById(id);
            if (assignment == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }
            AssignmentDto assignmentDto = new AssignmentDto();
            assignmentDto.setId(assignment.getId());
            assignmentDto.setDocument(assignment.getDocument().getId());
            assignmentDto.setTitle(assignment.getTitle());
            assignmentDto.setTimeLimit(assignment.getTimeLimit());
            assignmentDto.setPassingPercentage(assignment.getPassingPercentage());
            assignmentDto.setStatus(assignment.getStatus());
            assignmentDto.setMaxQuestions(assignment.getMaxQuestions());
            return Response.ok(assignment).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

}
