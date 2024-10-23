package org.imaginnovate.dto.timesheets.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "description", "createdBy", "createdAt" })
public class TaskDtoPost{

    @JsonIgnore
    private Integer id;
    private String name;
    private String description;

}
