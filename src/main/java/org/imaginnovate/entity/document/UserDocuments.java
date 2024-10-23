package org.imaginnovate.entity.document;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.imaginnovate.baseClass.common.BaseAuditFields;
import org.imaginnovate.entity.common.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dc_user_documents")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDocuments extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Documents document;

    @Column(name = "assigned_on", nullable = false)
    private LocalDateTime assignedOn;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private DocumentStatus status;

}
