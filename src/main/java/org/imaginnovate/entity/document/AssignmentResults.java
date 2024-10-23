package org.imaginnovate.entity.document;

import java.time.LocalDateTime;

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
@Table(name = "dc_assignment_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResults  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignments assignment;

    @Column(name = "attempt_date", nullable = false)
    private LocalDateTime attemptDate;

    @Column(name = "completed_date")
    private LocalDateTime completedDate;

    @Column(name = "no_of_questions", nullable = false)
    private Short noOfQuestions;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "percentage", nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double percentage;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private DocumentStatus status;
}
