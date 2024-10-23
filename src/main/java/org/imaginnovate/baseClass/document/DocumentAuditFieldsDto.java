package org.imaginnovate.baseClass.document;

import java.time.LocalDateTime;

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
public class DocumentAuditFieldsDto {

    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer deletedBy;
    private LocalDateTime deletedAt;

}
