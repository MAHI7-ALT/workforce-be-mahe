
package org.imaginnovate.dto.common.project;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "description", "division", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy", "deletedAt" })
public class ProjectDto extends BaseAuditFieldsDTO {

    @JsonIgnore
    private Integer id;
    private String name;
    private String description;
    private Integer division;

 

    public ProjectDto(Integer id, String name, String description, Integer division, Integer createdBy,
                LocalDateTime createdAt, Integer modifiedBy, LocalDateTime modifiedAt,Integer deletedBy, LocalDateTime deletedAt) {
    super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
    this.id = id;
    this.name = name;
    this.description = description;
    this.division = division;
}

}
