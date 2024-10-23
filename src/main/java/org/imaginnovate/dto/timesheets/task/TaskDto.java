
package org.imaginnovate.dto.timesheets.task;

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
@JsonPropertyOrder({ "id", "name", "description", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy",
        "deletedAt" })
public class TaskDto extends BaseAuditFieldsDTO {

    @JsonIgnore
    private Integer id;
    private String name;
    private String description;

    public TaskDto(Integer id, String name, String description, Integer createdBy, LocalDateTime createdAt,
            Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
