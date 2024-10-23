
package org.imaginnovate.dto.timesheets.timesheet;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.timesheet.TimesheetAuditFieldsDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "employeeProject", "projectTask", "description", "hoursWorked", "submittedBy",
        "submittedOn", "status", "approvedBy", "createdAt", "modifiedAt", "deletedBy",
        "deletedAt" })
public class TimesheetDto extends TimesheetAuditFieldsDto {

    @JsonIgnore
    private Integer id;
    private Integer employeeProject;
    private Integer projectTask;
    private String description;
    private Short hoursWorked;
    private Integer submittedBy;
    private LocalDateTime submittedOn;
    private Byte status;
    private Integer approvedBy;

    public TimesheetDto(Integer id, Integer employeeProject, Integer projectTask, String description,
            Short hoursWorked, Integer submittedBy, LocalDateTime submittedOn, Byte status, Integer approvedBy,
            LocalDateTime createdAt,LocalDateTime modifiedAt, Integer deletedBy,LocalDateTime deletedAt) {
        super( createdAt, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.employeeProject = employeeProject;
        this.projectTask = projectTask;
        this.description = description;
        this.hoursWorked = hoursWorked;
        this.submittedBy = submittedBy;
        this.submittedOn = submittedOn;
        this.status = status;
        this.approvedBy = approvedBy;
    }

}
