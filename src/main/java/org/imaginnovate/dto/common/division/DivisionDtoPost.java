package org.imaginnovate.dto.common.division;

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
@JsonPropertyOrder({ "id", "name", "parent"})
public class DivisionDtoPost  {

    @JsonIgnore
    private Integer id;
    private String name;
    private Integer parent;
}
