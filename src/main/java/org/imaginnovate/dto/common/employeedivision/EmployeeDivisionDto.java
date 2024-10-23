package org.imaginnovate.dto.common.employeedivision;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "employee", "division","primaryDivision", "createdBy", "createdAt", "modifiedBy",
        "modifiedAt", "deletedBy", "deletedAt" })
public class EmployeeDivisionDto extends BaseAuditFieldsDTO {

    @JsonIgnore
    private Integer id;
    private Integer employee;
    private Integer division;
    private Boolean primaryDivision;

    public EmployeeDivisionDto(Integer id, Integer employee, Integer division, Boolean primaryDivision,Integer createdBy, LocalDateTime createdAt,
    Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt){
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.employee = employee;
        this.division = division;
        this.primaryDivision=primaryDivision;
    }



}
