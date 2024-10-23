
package org.imaginnovate.dto.documents.quastions;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionsDto  extends BaseAuditFieldsDTO{

    @JsonIgnore
    private Integer id;
    private String content;
    private Integer assignment;

    public QuestionsDto(Integer id, String content, Integer assignment, Integer createdBy, LocalDateTime createdAt,
            Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.content = content;
        this.assignment = assignment;
    }

}
