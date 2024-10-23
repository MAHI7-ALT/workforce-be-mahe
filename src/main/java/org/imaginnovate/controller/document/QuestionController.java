package org.imaginnovate.controller.document;

import org.imaginnovate.dto.documents.quastions.QuestionsDto;
import org.imaginnovate.dto.documents.quastions.QuestionsDtoPost;
import org.imaginnovate.service.document.QuestionsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController {

    @Inject
    QuestionsService questionService;


    @GET
    public Response getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @POST
    public Response createQuestion(QuestionsDtoPost questionDto){
        return questionService.createQuestion(questionDto);
    }

    @PUT
    public Response updateQuestion(@PathParam("id") int id,QuestionsDto questionDto){
        return questionService.updateQuestion(id, questionDto);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteQuestionById(@PathParam("id") Integer id){
        return questionService.deleteQuestion(id);
    }

    @GET
    @Path("/{id}")
    public Response getQuestionById(@PathParam("id") int id){
        return questionService.getQuestionById(id);
    }

}
