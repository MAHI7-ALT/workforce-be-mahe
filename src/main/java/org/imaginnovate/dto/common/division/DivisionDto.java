package org.imaginnovate.dto.common.division;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "parentDivision", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy", "deletedAt" })
public class DivisionDto extends BaseAuditFieldsDTO {


    public Integer id;
    public String name;
    public String parentDivision;

    public DivisionDto(Integer id, String name, String parentDivision, Integer createdBy, LocalDateTime createdAt,
                       Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.name = name;
        this.parentDivision = parentDivision;
    }

}
