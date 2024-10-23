package org.imaginnovate.entity.document;

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

@Entity
@Table(name = "dc_assignment_response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "assignment_result_id", nullable = false)
    private AssignmentResults assignmentResult;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Questions question;

    @Lob
    @Column(name = "chosen_option",  columnDefinition = "TEXT", nullable = false)
    private String chosenOption;

    public void setAssignmentResult(Integer assignmentResult2) {
        this. assignmentResult.setId(assignmentResult2);
    }


}
