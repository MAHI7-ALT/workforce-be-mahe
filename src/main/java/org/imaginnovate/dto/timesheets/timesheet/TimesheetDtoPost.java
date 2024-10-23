package org.imaginnovate.dto.timesheets.timesheet;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "employeeProject", "projectTask", "description", "hoursWorked", "submittedBy", "submittedOn", "status", "approvedBy", "createdBy", "createdAt" })
public class TimesheetDtoPost {

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

    public TimesheetDtoPost(Integer id, Integer employeeProject, Integer projectTask, String description, Short hoursWorked, Integer submittedBy, LocalDateTime submittedOn, Byte status, Integer approvedBy) {
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
