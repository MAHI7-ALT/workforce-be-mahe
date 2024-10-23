package org.imaginnovate.entity.common;

import java.io.Serializable;

import org.imaginnovate.baseClass.common.BaseAuditFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Dependent
@Table(name = "employee_divisions", uniqueConstraints = @UniqueConstraint(columnNames = { "employee_id",
        "division_id" }))
@JsonPropertyOrder({ "id", "employee", "division", "primaryDivision", "createdBy", "createdAt", "modifiedBy",
        "modifiedAt", "deletedBy", "deletedAt" })
public class EmployeeDivision extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @Column(name = "primary_division", nullable = false, columnDefinition = "boolean default false")
    private Boolean primaryDivision;

} 