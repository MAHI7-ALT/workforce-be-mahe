
package org.imaginnovate.dto.documents;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class QuestionOptionDto extends BaseAuditFieldsDTO {

    private Integer id;
    private Integer question;
    private String content;
    private Boolean isCorrect;

    public QuestionOptionDto(Integer id, Integer question, String content, Boolean isCorrect, Integer createdBy,
            LocalDateTime createdAt, Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy,
            LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.question = question;
        this.content = content;
        this.isCorrect = isCorrect;
    }

}
