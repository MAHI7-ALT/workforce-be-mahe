
package org.imaginnovate.dto.common.employeeproject;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeProjectDto extends BaseAuditFieldsDTO {

    @JsonIgnore
    private Integer id;
    private Integer employeeDivision; 
    private Integer project;

    public EmployeeProjectDto(Integer id, Integer employeeDivision, Integer project,
            Integer createdBy, LocalDateTime createdAt,
            Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.employeeDivision = employeeDivision;
        this.project = project;
    }

}
