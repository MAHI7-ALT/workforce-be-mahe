package org.imaginnovate.dto.common.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "employee", "password", "role", "isAssigner", "resetTokenExpiresAt" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDtoPost  {

    @JsonIgnore
    private Integer id;
    private String name;
    private Integer employee;
    private String password;
    private String role;
    private Boolean isAssigner;

}
