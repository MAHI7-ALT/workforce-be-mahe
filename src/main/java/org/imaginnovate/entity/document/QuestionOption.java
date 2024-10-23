
package org.imaginnovate.entity.document;
import java.io.Serializable;

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
@Table(name = "dc_question_options")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOption extends BaseAuditFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Questions question;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(name = "is_correct", nullable = false,columnDefinition = "boolean default false")
    private Boolean isCorrect;

}
