package org.imaginnovate.service.document;

import java.time.LocalDateTime;
import java.util.List;

import org.imaginnovate.dto.documents.AssignmentResultDto;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.document.AssignmentResults;
import org.imaginnovate.entity.document.Assignments;
import org.imaginnovate.entity.document.DocumentStatus;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.document.AssignmentResultsRepo;
import org.imaginnovate.repository.document.AssignmentsRepo;
import org.imaginnovate.repository.document.DocumentStatusRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AssignmentResultService {

    @Inject
    private AssignmentResultsRepo assignmentResultRepo;

    @Inject
    private UserRepo usersRepo;

    @Inject
    private AssignmentsRepo assignmentsRepo;

    @Inject
    private DocumentStatusRepo documentStatusRepo;

    public Response getAllAssignmentResults() {
        try {
            List<AssignmentResultDto> assignmenetResultsDto = assignmentResultRepo.findAllAssignmentResults();
            if (assignmenetResultsDto.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No assignment results found").build();
            }
            return Response.ok(assignmenetResultsDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response createAssignmentResult(AssignmentResultDto assignementResultDto) {
        try {
            User user = usersRepo.findById(assignementResultDto.getUser());
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }

            Assignments assignment = assignmentsRepo.findById(assignementResultDto.getAssignment());
            if (assignment == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }

            DocumentStatus documentStatus = documentStatusRepo.findById(assignementResultDto.getStatus());
            if (documentStatus == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("DocumentStatus not found").build();
            }
            AssignmentResults assignmentResult = new AssignmentResults();
            assignmentResult.setId(assignementResultDto.getId());
            assignmentResult.setAssignment(assignment);
            assignmentResult.setUser(user);
            assignmentResult.setAttemptDate(LocalDateTime.now());
            assignmentResult.setCompletedDate(LocalDateTime.now());
            assignmentResult.setNoOfQuestions(assignementResultDto.getNoOfQuestions());
            assignmentResult.setScore(assignementResultDto.getScore());
            assignmentResult.setPercentage(assignementResultDto.getPercentage());
            assignmentResult.setStatus(assignementResultDto.getStatus());

            assignmentResultRepo.persist(assignmentResult);
            assignementResultDto.setId(assignmentResult.getId());
            return Response.ok(assignementResultDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server error while creating assignment result").build();
        }

    }

    @Transactional
    public Response updateAssignmentResult(int id, AssignmentResultDto assignementResultDto) {
        try {
            AssignmentResults assignmentResult = assignmentResultRepo.findById(assignementResultDto.getId());
            if (assignmentResult == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment result not found").build();
            }

            User user = usersRepo.findById(assignementResultDto.getUser());
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
            Assignments assignment = assignmentsRepo.findById(assignementResultDto.getAssignment());
            if (assignment == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }

            DocumentStatus documentStatus = documentStatusRepo.findById(assignementResultDto.getStatus());
            if (documentStatus == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("DocumentStatus not found").build();
            }
            if (assignmentResult.getNoOfQuestions() != null) {
                assignmentResult.setNoOfQuestions(assignementResultDto.getNoOfQuestions());
            }
            if (assignmentResult.getScore() != null) {
                assignmentResult.setScore(assignementResultDto.getScore());
            }
            if (assignmentResult.getPercentage() != null) {
                assignmentResult.setPercentage(assignementResultDto.getPercentage());
            }
            if (assignmentResult.getStatus() != null) {
                assignmentResult.setStatus(assignementResultDto.getStatus());
            }
            assignmentResult.setId(assignementResultDto.getId());
            assignmentResult.setAssignment(assignment);
            assignmentResult.setUser(user);
            assignmentResult.setAttemptDate(LocalDateTime.now());
            assignmentResult.setCompletedDate(LocalDateTime.now());

            assignmentResultRepo.persist(assignmentResult);
            assignementResultDto.setId(assignmentResult.getId());
            return Response.ok(assignementResultDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server error while updating assignment result").build();
        }
    }

    public Response getAssignmentResultById(int id) {
        try {
            AssignmentResults assignmentResult = assignmentResultRepo.findById(id);
            if (assignmentResult == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment result not found").build();
            }
            AssignmentResultDto assignmentResultDto = new AssignmentResultDto();
            assignmentResultDto.setId(assignmentResult.getId());
            assignmentResultDto.setAssignment(assignmentResult.getAssignment().getId());
            assignmentResultDto.setUser(assignmentResult.getUser().getId());
            assignmentResultDto.setAttemptDate(assignmentResult.getAttemptDate());
            assignmentResultDto.setCompletedDate(assignmentResult.getCompletedDate());
            assignmentResultDto.setNoOfQuestions(assignmentResult.getNoOfQuestions());
            assignmentResultDto.setScore(assignmentResult.getScore());
            assignmentResultDto.setPercentage(assignmentResult.getPercentage());
            assignmentResultDto.setStatus(assignmentResult.getStatus());

            return Response.ok(assignmentResult).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }

    }

}
