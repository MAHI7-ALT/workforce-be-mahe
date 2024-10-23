package org.imaginnovate.entity.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "users")
@JsonPropertyOrder({ "id", "name", "employee", "password", "role", "isAssigner", "resetTokenExpiresAt", "createdBy",
        "createdAt", "modifiedBy", "modifiedAt", "deletedBy", "deletedAt", "jwtToken" })
public class User extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Username
    @Column(name = "name", nullable = false, length = 60, unique = true)
    private String name ;
 
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Password
    @Column(name = "password", nullable = false, length = 80)
    
    private String password;

    @Roles
    @Column(name = "role", nullable = false, length = 15)
    private String role;

    @Column(name = "is_assigner", nullable = false, columnDefinition = "boolean default false")
    private Boolean isAssigner;

    @Column(name = "reset_token_expires_at")
    private LocalDateTime resetTokenExpiresAt;

}
