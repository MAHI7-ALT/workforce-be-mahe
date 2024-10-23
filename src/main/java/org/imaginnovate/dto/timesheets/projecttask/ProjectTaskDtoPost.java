package org.imaginnovate.dto.timesheets.projecttask;

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
@JsonPropertyOrder({ "id", "project", "task" })
public class ProjectTaskDtoPost {

    @JsonIgnore
    private Integer id;
    private Integer project;
    private Integer task;
}
