package org.imaginnovate.dto.documents.document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DocumentsDto {
    @JsonIgnore
    private Integer id;
    private String documentType;
    private Integer serialNo;
    private String title;
    private String content;
    private Boolean isRecurring;
    private String recurringType;
    private Integer recurringTimeFrame;
    private LocalDate recurringFixedOn;
    private String createdBy;
    private LocalDateTime createdAt;

    public DocumentsDto(Integer id, String documentType, Integer serialNo, String title, String content,
            Boolean isRecurring, String recurringType, Integer recurringTimeFrame, java.sql.Date recurringFixedOn,
            String createdBy, java.sql.Timestamp createdAt) {
        this.id = id;
        this.documentType = documentType;
        this.serialNo = serialNo;
        this.title = title;
        this.content = content;
        this.isRecurring = isRecurring;
        this.recurringType = recurringType;
        this.recurringTimeFrame = recurringTimeFrame;
        this.recurringFixedOn = recurringFixedOn != null ? recurringFixedOn.toLocalDate() : null;
        this.createdBy = createdBy;
        this.createdAt = createdAt != null ? createdAt.toLocalDateTime() : null;
    }

  
}
