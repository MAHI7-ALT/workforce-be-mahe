
package org.imaginnovate.entity.timesheet;

import java.io.Serializable;

import org.imaginnovate.baseClass.common.BaseAuditFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ts_tasks")
@JsonPropertyOrder({ "id", "name", "description", "createdBy", "createdAt", "modifiedBy", "modifiedAt",
        "deletedBy", "deletedAt" })
public class Task extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 40)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;


}
