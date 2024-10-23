package org.imaginnovate.dto.common.employee;

import java.time.LocalDateTime;

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
@JsonPropertyOrder({ "id", "firstName", "lastName", "gender", "email", "designation", "startDate", "endDate", "reportsTo", "canApproveTimesheets" })
public class EmployeeDtoPost {

    @JsonIgnore
    private Integer id;
    private String firstName;
    private String lastName;
    private Character gender;
    private String email;
    private String designation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer reportsTo;
    private Boolean canApproveTimesheets;

}
