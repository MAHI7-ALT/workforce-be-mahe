package org.imaginnovate.dto.common.employeedivision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "employeeId", "divisionId", "primaryDivision"})
public class EmployeeDivisionDtoPost{

    @JsonIgnore
    private Integer id;
    private Integer employee;
    private Integer division;
    private Boolean primaryDivision;
}
