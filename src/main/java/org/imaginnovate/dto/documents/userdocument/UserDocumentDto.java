package org.imaginnovate.dto.documents.userdocument;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDocumentDto extends BaseAuditFieldsDTO{

    @JsonIgnore
    private Integer id;
    private Integer user;
    private Integer document;
    private LocalDateTime assignedOn;
    private LocalDate targetDate;
    private Integer status;


    public UserDocumentDto(Integer id, Integer user, Integer document, LocalDateTime assignedOn, LocalDate targetDate, Integer status, Integer createdBy, LocalDateTime createdAt, Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.user = user;
        this.document = document;
        this.assignedOn = assignedOn;
        this.targetDate = targetDate;
        this.status = status;
    }

}
