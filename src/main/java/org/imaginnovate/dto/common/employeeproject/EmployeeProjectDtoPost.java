package org.imaginnovate.dto.common.employeeproject;
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
@JsonPropertyOrder({ "id", "employeeDivision", "project" })
public class EmployeeProjectDtoPost {

    @JsonIgnore
    private Integer id;
    private Integer employeeDivision;
    private Integer project;

  
}
