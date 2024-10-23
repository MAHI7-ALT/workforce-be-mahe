package org.imaginnovate.dto.timesheets.timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTimesheetsDto {
    private Integer  employeeProject;
    private Integer projectTask;
    private String description;
    private Short hoursWorked;

}
