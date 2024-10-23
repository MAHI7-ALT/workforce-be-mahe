
package org.imaginnovate.dto.timesheets.projecttask;

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
@JsonPropertyOrder({ "id", "project", "task", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy",
        "deletedAt" })
public class ProjectTaskDto extends BaseAuditFieldsDTO {

    @JsonIgnore
    private Integer id;
    private Integer project;
    private Integer task;

    public ProjectTaskDto(Integer id, Integer project, Integer task, Integer createdBy, LocalDateTime createdAt,
            Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.project = project;
        this.task = task;
    }

}
