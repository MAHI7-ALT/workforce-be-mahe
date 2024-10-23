package org.imaginnovate.service.document;

import java.util.List;

import org.imaginnovate.dto.documents.AssignmentResponseDto;
import org.imaginnovate.entity.document.AssignmentResponse;
import org.imaginnovate.entity.document.Questions;
import org.imaginnovate.repository.document.AssignmentResponseRepo;
import org.imaginnovate.repository.document.QuestionsRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AssignmentResponseService {

    @Inject
    private AssignmentResponseRepo assignmentResponseRepo;

    @Inject
    private QuestionsRepo questionsRepo;


    public Response getAllAssignmentResponses() {
        try{
            List<AssignmentResponseDto> assignmentResponseDto = assignmentResponseRepo.getAllAssignmentResponses();
            if (assignmentResponseDto==null){
                return Response .status (Response.Status.NOT_FOUND).entity("AssignmentResponse not found").build();
            }else {
                return Response.ok(assignmentResponseDto).build();
            }

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }


    @Transactional
    public Response createAssignmentResponse(AssignmentResponseDto assignmentResponseDto) {
        try {
            Questions questions = questionsRepo.findById(assignmentResponseDto.getQuestion());
            if (questions == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Questions not found").build();
            }
            AssignmentResponse assignmentResponse =new AssignmentResponse();

            assignmentResponse.setId(assignmentResponseDto.getId());
            assignmentResponse.setQuestion(questions);
            assignmentResponse.setChosenOption(assignmentResponseDto.getChosenOption());
            assignmentResponse.setAssignmentResult(assignmentResponseDto.getAssignmentResult());

            assignmentResponseRepo.persist(assignmentResponse);
            assignmentResponseDto.setId(assignmentResponse.getId());

            return Response.status(Response.Status.CREATED).entity(assignmentResponseDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }


    @Transactional
    public Response updateAssignmentResponse(int id, AssignmentResponseDto assignmentResponseDto) {
        try {
            AssignmentResponse assignmentResponse = assignmentResponseRepo.findById(assignmentResponseDto.getId());
            if (assignmentResponse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("AssignmentResponse not found").build();
            }
            Questions questions = questionsRepo.findById(assignmentResponseDto.getQuestion());
            if (questions == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Questions not found").build();
            }
            if(assignmentResponse.getChosenOption()!=null){
                assignmentResponse.setChosenOption(assignmentResponseDto.getChosenOption());
            }

            if(assignmentResponse.getAssignmentResult()!=null){
                assignmentResponse.setAssignmentResult(assignmentResponseDto.getAssignmentResult());
            }
            assignmentResponse.setId(assignmentResponse.getId());
            assignmentResponse.setQuestion(questions);
            assignmentResponseRepo.persist(assignmentResponse);
            assignmentResponseDto.setId(assignmentResponse.getId());
            return Response.ok("AssignmentResponse updated successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }


    public Response getAssignmentResponseById(int id){
        try{
            AssignmentResponse assignmentResponse = assignmentResponseRepo.findById(id);
            if (assignmentResponse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("AssignmentResponse not found").build();
            }
            AssignmentResponseDto assignmentResponseDto = new AssignmentResponseDto();
            assignmentResponseDto.setId(assignmentResponse.getId());
            assignmentResponseDto.setQuestion(assignmentResponse.getQuestion().getId());
            assignmentResponseDto.setChosenOption(assignmentResponse.getChosenOption());
            assignmentResponseDto.setAssignmentResult(assignmentResponse.getAssignmentResult().getId());

            return Response.ok(assignmentResponseDto).build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

}