
package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.quastions.QuestionsDto;
import org.imaginnovate.entity.document.Questions;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuestionsRepo implements PanacheRepositoryBase<Questions, Integer> {

    public List<QuestionsDto> getAllQuestions() {
        return getEntityManager().createQuery("select new org.imaginnovate.dto.documents.QuestionsDto(q.id, q.content, q.assignment, q.createdBy, q.createdAt, q.modifiedBy, q.modifiedAt, q.deletedBy, q.deletedAt) from Questions q", QuestionsDto.class).getResultList();
       
    }

}
