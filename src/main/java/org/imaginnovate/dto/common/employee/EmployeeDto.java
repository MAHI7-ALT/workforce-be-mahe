
package org.imaginnovate.dto.common.employee;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "firstName", "lastName", "gender", "email", "designation", "startDate", "endDate",
        "reportsTo", "canApproveTimesheets", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy",
        "deletedAt" })
public class EmployeeDto extends BaseAuditFieldsDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private Character gender;
    private String email;
    private String designation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reportsTo;
    private Boolean canApproveTimesheets;

    public EmployeeDto(Integer id, String firstName, String lastName, Character gender, String email,
            String designation,
            LocalDateTime startDate, LocalDateTime endDate, String reportsTo, Boolean canApproveTimesheets,
            Integer createdBy,
            LocalDateTime createdAt, Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy,
            LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.designation = designation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportsTo = reportsTo;
        this.canApproveTimesheets = canApproveTimesheets;
    }

}
