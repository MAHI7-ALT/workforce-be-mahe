package org.imaginnovate.service.document;

import java.time.LocalDateTime;
import java.util.List;

import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.documents.quastions.QuestionsDto;
import org.imaginnovate.dto.documents.quastions.QuestionsDtoPost;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.document.Assignments;
import org.imaginnovate.entity.document.Questions;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.document.AssignmentsRepo;
import org.imaginnovate.repository.document.QuestionsRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class QuestionsService {

    @Inject
    private QuestionsRepo questionsRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AssignmentsRepo assignmentsRepo;

    @Inject
    private AuthService authService;

    public Response getAllQuestions() {
        try {
            List<QuestionsDto> questions = questionsRepo.getAllQuestions();
            if (questions == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Questions not found").build();
            } else {
                return Response.ok(questions).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();

        }
    }

    @Transactional
    public Response createQuestion(QuestionsDtoPost questionsDto) {
        try {
            Questions questions = questionsRepo.findById(questionsDto.getId());
            if (questions != null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Question already exists").build();
            }

            Assignments assignments = assignmentsRepo.findById(questionsDto.getAssignment());
            if (assignments == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }
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
            questions = new Questions();
            questions.setId(questionsDto.getId());
            questions.setContent(questionsDto.getContent());
            questions.setAssignment(assignments);
            questions.setCreatedBy(createdBy);
            questions.setCreatedAt(LocalDateTime.now());
            questionsRepo.persist(questions);
            return Response.ok(questionsDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response updateQuestion(int id, QuestionsDto questionsDto) {
        try {
            Questions questions = questionsRepo.findById(questionsDto.getId());
            if (questions == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Question not found").build();
            }
            Assignments assignments = assignmentsRepo.findById(questionsDto.getAssignment());
            if (assignments == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Assignment not found").build();
            }

            if (questionsDto.getCreatedBy() != null) {
                User createdBy = userRepo.findByIdOptional(questionsDto.getCreatedBy()).orElse(null);
                if (createdBy == null) {
                    return Response.status(Status.NOT_FOUND).entity("CreatedBy user not found.").build();
                }
                if (!userRepo.canAssign(questionsDto.getCreatedBy())) {
                    return Response.status(Status.FORBIDDEN).entity("User does not have rights to create questions.")
                            .build();
                }
                questions.setCreatedBy(createdBy);
            }

            if (questionsDto.getModifiedBy() != null) {
                User modifiedBy = userRepo.findByIdOptional(questionsDto.getModifiedBy()).orElse(null);
                if (modifiedBy == null) {
                    return Response.status(Status.NOT_FOUND).entity("ModifiedBy user not found.").build();
                }

                if (!userRepo.canAssign(questionsDto.getModifiedBy())) {
                    return Response.status(Status.FORBIDDEN).entity("User does not have approval rights.").build();
                }
                questions.setModifiedBy(modifiedBy);
            }
            questions.setId(questionsDto.getId());
            questions.setContent(questionsDto.getContent());
            questions.setAssignment(assignments);
            questions.setCreatedAt(questionsDto.getCreatedAt());
            questions.setModifiedAt(LocalDateTime.now());
            questionsRepo.persist(questions);
            return Response.ok(questionsDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response deleteQuestion(Integer id) {
        try {
            Questions questions = questionsRepo.findById(id);
            if (questions == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Question not found").build();
            }

            questions.setDeletedBy(questions.getDeletedBy());
            questions.setDeletedAt(LocalDateTime.now());
            questionsRepo.persist(questions);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    public Response getQuestionById(Integer id) {
        try {
            Questions questions = questionsRepo.findById(id);
            if (questions == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Question not found.").build();
            }
            QuestionsDto dto = new QuestionsDto();
            dto.setId(questions.getId());
            dto.setContent(questions.getContent());
            dto.setAssignment(questions.getAssignment().getId());
            dto.setCreatedBy(questions.getCreatedBy().getId());
            dto.setCreatedAt(questions.getCreatedAt());
            dto.setModifiedBy(questions.getModifiedBy().getId());
            dto.setModifiedAt(questions.getModifiedAt());
            dto.setDeletedBy(questions.getDeletedBy().getId());
            dto.setDeletedAt(questions.getDeletedAt());
            return Response.ok(dto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error while fetching question.").build();
        }
    }

}
