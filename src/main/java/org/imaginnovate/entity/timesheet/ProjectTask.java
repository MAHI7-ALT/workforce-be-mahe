
package org.imaginnovate.entity.timesheet;

import java.io.Serializable;

import org.imaginnovate.baseClass.common.BaseAuditFields;
import org.imaginnovate.entity.common.Project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "ts_project_tasks", uniqueConstraints = @UniqueConstraint(columnNames = { "project_id", "task_id" }))
@JsonPropertyOrder({ "id", "projectId", "taskId", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy",
        "deletedAt" })
public class ProjectTask extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

}
