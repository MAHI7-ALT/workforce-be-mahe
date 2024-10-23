package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.AssignmentResponseDto;
import org.imaginnovate.entity.document.AssignmentResponse;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AssignmentResponseRepo  implements PanacheRepositoryBase<AssignmentResponse, Integer> {

    public List<AssignmentResponseDto> getAllAssignmentResponses() {
         return  getEntityManager().createQuery("select new org.imaginnovate.dto.documents.AssignmentResponseDto(ar.id, ar.assignmentResult, ar.question, ar.chosenOption) from AssignmentResponse ar", AssignmentResponseDto.class).getResultList();
    }

  


    

}
