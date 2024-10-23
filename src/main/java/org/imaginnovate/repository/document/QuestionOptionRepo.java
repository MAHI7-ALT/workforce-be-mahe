
package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.QuestionOptionDto;
import org.imaginnovate.entity.document.QuestionOption;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuestionOptionRepo implements PanacheRepositoryBase<QuestionOption, Integer> {

    public List<QuestionOptionDto> getAllQuestionOptions() {
        return getEntityManager().createQuery("select new org.imaginnovate.dto.documents.QuestionOptionDto(d.id, d.question, d.content, d.isCorrect, d.createdBy, d.createdAt, d.modifiedBy, d.modifiedAt, d.deletedBy, d.deletedAt) from QuestionOption d", QuestionOptionDto.class).getResultList();
    }

}
