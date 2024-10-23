package org.imaginnovate.dto.common.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private Integer id;
    private String userName;
    private String employeeName;
    private String role;
    private String password;

}
