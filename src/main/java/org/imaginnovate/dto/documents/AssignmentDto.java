package org.imaginnovate.dto.documents;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class AssignmentDto extends BaseAuditFieldsDTO{

    @JsonIgnore
    private Integer id;
    private Integer document;
    private String title;
    private Short timeLimit;
    private Double passingPercentage;
    private Boolean status;
    private Short maxQuestions; 

    public  AssignmentDto(Integer id, Integer document, String title, Short timeLimit, Double passingPercentage,
            Boolean status, Short maxQuestions, Integer createdBy, LocalDateTime createdAt, Integer modifiedBy,
            LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.document = document;
        this.title = title;
        this.timeLimit = timeLimit;
        this.passingPercentage = passingPercentage;
        this.status = status;
        this.maxQuestions = maxQuestions;
    }

}
