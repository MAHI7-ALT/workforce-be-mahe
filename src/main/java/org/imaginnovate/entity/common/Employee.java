package org.imaginnovate.entity.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "employees")
@JsonPropertyOrder({ "id", "firstName", "lastName", "gender", "email", "designation", "startDate", "endDate",
        "reportsTo", "can_approve_timesheets", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy",
        "deletedAt" })
public class Employee extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "gender", nullable = false, length = 1)
    private Character gender;

    @Column(name = "email", unique = true, nullable = false, length = 80)
    private String email;

    @Column(name = "designation", nullable = false, length = 30)
    private String designation;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "reports_to_id", nullable = true)
    private Employee reportsTo;

    @Column(name = "can_approve_timesheets", nullable = false, columnDefinition = "boolean default false")
    private Boolean canApproveTimesheets;

    public Employee() {
        this.canApproveTimesheets = false;
    }
}