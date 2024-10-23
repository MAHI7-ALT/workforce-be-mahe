package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.AssignmentResultDto;
import org.imaginnovate.entity.document.AssignmentResults;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AssignmentResultsRepo implements PanacheRepositoryBase<AssignmentResults, Integer> {

    public List<AssignmentResultDto> findAllAssignmentResults() {
      return getEntityManager().createQuery("SELECT new org.imaginnovate.dto.documents.AssignementResultDto(ar.id,ar.assignment,ar.user,ar.attemptDate,ar.completedDate,ar.noOfQuestions,ar.score,ar.percentage,ar.status) FROM AssignmentResults ar", AssignmentResultDto.class).getResultList();
    }

}
