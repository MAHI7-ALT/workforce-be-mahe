package org.imaginnovate.dto.documents.document;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DocumentPutDto {
    @JsonIgnore
    private Integer id;
    private Byte documentType;
    private Integer serialNo;
    private String title;
    private String content;
    private Boolean isRecurring;
    private String recurringType;
    private Integer recurringTimeFrame;
    private LocalDate recurringFixedOn;

    public DocumentPutDto(Integer id, Byte documentType, Integer serialNo, String title, String content,
            Boolean isRecurring, String recurringType, Integer recurringTimeFrame, java.sql.Date recurringFixedOn) {
        this.id = id;
        this.documentType = documentType;
        this.serialNo = serialNo;
        this.title = title;
        this.content = content;
        this.isRecurring = isRecurring;
        this.recurringType = recurringType;
        this.recurringTimeFrame = recurringTimeFrame;
        this.recurringFixedOn = recurringFixedOn != null ? recurringFixedOn.toLocalDate() : null;
    }

}
