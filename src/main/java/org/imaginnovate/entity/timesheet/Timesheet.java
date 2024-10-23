
package org.imaginnovate.entity.timesheet;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.timesheet.TimesheetAuditFields;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeProject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ts_timesheets")
@JsonPropertyOrder({ "id", "employeeProjectId", "projectTaskId", "description", "hoursWorked", "submittedBy",
        "submittedOn", "status", "approvedBy", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy",
        "deletedAt" })
public class Timesheet extends TimesheetAuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_project_id", nullable = false)
    private EmployeeProject employeeProject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_task_id", nullable = false)
    private ProjectTask projectTask;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "hours_worked", nullable = false)
    private Short hoursWorked;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "submitted_by", nullable = false)
    private Employee submittedBy;

    @Column(name = "submitted_on", nullable = false)
    private LocalDateTime submittedOn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status", nullable = false)
    private TimesheetStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;

}