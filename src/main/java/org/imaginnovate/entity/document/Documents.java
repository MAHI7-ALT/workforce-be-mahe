package org.imaginnovate.entity.document;

import java.time.LocalDate;

import org.imaginnovate.baseClass.document.DocumentAuditFields;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "dc_documents")
public class Documents extends DocumentAuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @Column(name = "serial_no", nullable = false)
    private Integer serialNo;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_recurring", columnDefinition = "boolean default false")
    private Boolean isRecurring;

    @Column(name = "recurring_type", length = 10)
    private String recurringType;

    @Column(name = "recurring_time_frame")
    private Integer recurringTimeFrame;

    @Column(name = "recurring_fixed_on")
    private LocalDate recurringFixedOn;

}
