package org.imaginnovate.baseClass.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseAuditFieldsPost {

    private Integer createdBy;
    private LocalDateTime createdAt;

}
