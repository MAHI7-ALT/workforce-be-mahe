package org.imaginnovate.dto.documents;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFieldsPostDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DocumentHistoryDto extends BaseAuditFieldsPostDto {

    @JsonIgnore
    private Integer id;
    private Integer document;
    private String title;
    private String content;
    private Byte documentType;
    private Boolean isRecurring;
    private String recurringType;
    private Integer recurringTimeFrame;
    private LocalDate recurringFixedOn;
    
    public DocumentHistoryDto(Integer id, Integer document, String title, String content, Byte documentType, Boolean isRecurring, String recurringType, Integer recurringTimeFrame, LocalDate recurringFixedOn, Integer createdBy, LocalDateTime createdAt) {
        super(createdBy, createdAt);
        this.id = id;
        this.document = document;
        this.title = title;
        this.content = content;
        this.documentType = documentType;
        this.isRecurring = isRecurring;
        this.recurringType = recurringType;
        this.recurringTimeFrame = recurringTimeFrame;
        this.recurringFixedOn = recurringFixedOn;
    }
}
