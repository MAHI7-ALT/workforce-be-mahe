package org.imaginnovate.service.document;

import java.time.LocalDateTime;
import java.util.List;

import org.imaginnovate.dto.documents.QuestionOptionDto;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.document.QuestionOption;
import org.imaginnovate.entity.document.Questions;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.document.QuestionOptionRepo;
import org.imaginnovate.repository.document.QuestionsRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class QuestionOptionService {

    @Inject 
    QuestionOptionRepo questionOptionRepo;

    @Inject 
    QuestionsRepo questionRepo;

    @Inject
    UserRepo userRepo;

    
    public Response getAllQuestionOptions(){
        try{
            List<QuestionOptionDto> questionOptions = questionOptionRepo.getAllQuestionOptions();
            if (questionOptions==null){
                return Response .status (Response.Status.NOT_FOUND).entity("QuestionOptions not found").build();
            }else {
                return Response.ok(questionOptions).build();
            }

        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }

    }

    @Transactional
    public Response createQuestionOption(QuestionOptionDto questionOptionDto){
        try{

            QuestionOption questionOption = questionOptionRepo.findById(questionOptionDto.getId());
            if(questionOption == null){
                return Response.status(Response.Status.NOT_FOUND).entity("QuestionOption not found").build();
            }
            Questions question = questionRepo.findById(questionOptionDto.getQuestion());
            if(question == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Questions not found").build();
            }

            if (questionOptionDto.getCreatedBy() != null) {
                User createdBy = userRepo.findByIdOptional(questionOptionDto.getCreatedBy()).orElse(null);
                if (createdBy == null) {
                    return Response.status(Status.NOT_FOUND).entity("CreatedBy user not found.").build();
                }

                if (!userRepo.canAssign(questionOptionDto.getCreatedBy())) {
                    return Response.status(Status.FORBIDDEN).entity("User does not have rights to create question Options.").build();
                }
                questionOption.setCreatedBy(createdBy);
            }



            questionOption.setId(questionOptionDto.getId());
            questionOption.setQuestion(question);
            questionOption.setContent(questionOptionDto.getContent());
            questionOption.setIsCorrect(questionOptionDto.getIsCorrect());
            questionOption.setCreatedAt(questionOptionDto.getCreatedAt());
            questionOptionRepo.persist(questionOption);
            questionOptionDto.setId(questionOption.getId());
            return Response.status(Response.Status.OK).entity("QuestionOption added successfully").build();

        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response updateQuestionOption(int id,QuestionOptionDto questionOptionDto){  
        try{
            QuestionOption questionOption = questionOptionRepo.findById(questionOptionDto.getId());
            if(questionOption == null){
                return Response.status(Response.Status.NOT_FOUND).entity("QuestionOption not found").build();
            }
            Questions question = questionRepo.findById(questionOptionDto.getQuestion());
            if(question == null){   
                return Response.status(Response.Status.NOT_FOUND).entity("Questions not found").build();
            }
            User createdBy = null;
            if (questionOptionDto.getCreatedBy() != null) {
                createdBy = userRepo.findById(questionOptionDto.getCreatedBy());
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("CreatedBy user not found")
                            .build();
                }

                boolean canAssign = userRepo.canAssignDocuments(questionOptionDto.getCreatedBy());
                if (!canAssign) {
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("Userdoes not have createdBy rights")
                            .build();
                }
            }

            if(questionOptionDto.getModifiedBy() != null){
                User modifiedBy = userRepo.findByIdOptional(questionOptionDto.getModifiedBy()).orElse(null);
                if (modifiedBy == null) {
                    return Response.status(Status.NOT_FOUND).entity("ModifiedBy user not found.").build();
                }

                boolean canAssign = userRepo.canAssignDocuments(questionOptionDto.getModifiedBy());
                if (!canAssign) {
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("Userdoes not have modifiedBy rights")
                            .build();
                }
                questionOption.setModifiedBy(modifiedBy);
            }

          if(questionOptionDto.getIsCorrect() != null){
              questionOption.setIsCorrect(questionOptionDto.getIsCorrect());
          }
          if(questionOptionDto.getContent() != null){
              questionOption.setContent(questionOptionDto.getContent());
          }

          questionOption.setCreatedAt(questionOptionDto.getCreatedAt());
          questionOptionDto.setModifiedAt(questionOption.getModifiedAt());
          questionOptionDto.setDeletedAt(questionOption.getDeletedAt());
            questionOptionRepo.persist(questionOption);
            questionOptionDto.setId(questionOption.getId());
            return Response.status(Response.Status.OK).entity("QuestionOption updated successfully").build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response deleteQuestionOption(int id){
        try{
            QuestionOption questionOption = questionOptionRepo.findById(id);
            if(questionOption == null){
                return Response.status(Response.Status.NOT_FOUND).entity("QuestionOption not found").build();
            }

            questionOption.setDeletedBy(questionOption.getDeletedBy());
            questionOption.setDeletedAt(LocalDateTime.now());
            questionOptionRepo.persist(questionOption);
            return Response.status(Response.Status.OK).entity("QuestionOption deleted successfully").build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    public Response getQuestionOptionsById(int id){
        try{
            QuestionOption questionOption = questionOptionRepo.findById(id);
            if(questionOption == null){
                return Response.status(Response.Status.NOT_FOUND).entity("QuestionOption not found").build();
            }
            
            Questions question = questionRepo.findById(questionOption.getQuestion().getId());
           if(question == null){
               return Response.status(Response.Status.NOT_FOUND).entity("Questions not found").build();
           }

            QuestionOptionDto questionOptionDto = new QuestionOptionDto();
            questionOptionDto.setId(questionOption.getId());
            questionOptionDto.setContent(questionOption.getContent());
            questionOptionDto.setIsCorrect(questionOption.getIsCorrect());
            questionOptionDto.setQuestion(question.getId());
            questionOptionDto.setCreatedBy(questionOption.getCreatedBy().getId());
            questionOptionDto.setModifiedBy(questionOption.getModifiedBy().getId());
            questionOptionDto.setDeletedBy(questionOption.getDeletedBy().getId());
            questionOptionDto.setCreatedAt(questionOption.getCreatedAt());
            questionOptionDto.setModifiedAt(questionOption.getModifiedAt());
            questionOptionDto.setDeletedAt(questionOption.getDeletedAt());
            return Response.ok(questionOptionDto).build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }
}
