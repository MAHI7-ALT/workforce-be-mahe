package org.imaginnovate.baseClass.timesheet;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimesheetAuditFieldsDto {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer deletedBy;
    private LocalDateTime deletedAt;

}
