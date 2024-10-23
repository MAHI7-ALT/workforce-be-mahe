package org.imaginnovate.dto.common.user;

import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "userName", "employeeName", "role", "isAssigner", "createdBy", "createdAt", "modifiedBy", "modifiedAt", "deletedBy", "deletedAt" })
public class UserDto extends BaseAuditFieldsDTO {

    private Integer id;
    private String userName;
    private String employeeName; 
    private String role;
   private Boolean isAssigner;

    public UserDto(Integer id, String userName, String employeeName, String role, Boolean isAssigner ,Integer createdBy, LocalDateTime createdAt, Integer modifiedBy, LocalDateTime modifiedAt, Integer deletedBy, LocalDateTime deletedAt) {
        super(createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        this.id = id;
        this.userName = userName;
        this.employeeName = employeeName;
        this.role = role;
      this.isAssigner = isAssigner;
    }
}
