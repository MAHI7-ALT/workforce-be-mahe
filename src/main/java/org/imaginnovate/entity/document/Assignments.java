package org.imaginnovate.entity.document;

import org.imaginnovate.baseClass.common.BaseAuditFields;

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
@Table(name = "dc_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignments extends BaseAuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Documents document;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "time_limit", nullable = false)
    private Short timeLimit;

    @Column(name = "passing_percentage", nullable = false, columnDefinition = "DECIMAL(4,2)")
    private Double passingPercentage;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean status;

    @Column(name = "max_questions", nullable = false)
    private Short maxQuestions;

}
