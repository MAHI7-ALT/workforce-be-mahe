package org.imaginnovate.entity.document;
import java.time.LocalDate;

import org.imaginnovate.baseClass.common.BaseAuditFieldsPost;

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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dc_document_history")
public class DocumentHistory extends  BaseAuditFieldsPost{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Documents document;

    @Column(name = "serial_no", nullable = false)
    private Integer serialNo;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @Column(name="is_recurring", nullable = false)
    private Boolean isRecurring;

    @Column(name = "recurring_type", length = 10)
    private String recurringType;

    @Column(name = "recurring_time_frame")
    private Integer recurringTimeFrame;

    @Column(name = "recurring_fixed_on")
    private LocalDate recurringFixedOn;
}
