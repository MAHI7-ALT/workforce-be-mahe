package org.imaginnovate.dto.common.project;
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
@JsonPropertyOrder({ "id", "name", "description", "division" })
public class ProjectDtoPost{

    @JsonIgnore
    private Integer id;
    private String name;
    private String description;
    private Integer division;
}
