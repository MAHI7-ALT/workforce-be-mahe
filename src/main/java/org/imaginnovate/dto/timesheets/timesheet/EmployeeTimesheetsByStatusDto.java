package org.imaginnovate.dto.timesheets.timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTimesheetsByStatusDto {
    private String  employeeProjectName;
    private String  employeeDivisionName;
    private String taskName;
    private String description;
    private String HoursWorked;
    private Byte Status;
    private Integer approvedBy;

}
