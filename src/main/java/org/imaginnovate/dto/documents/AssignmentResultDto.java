package org.imaginnovate.dto.documents;

import java.time.LocalDateTime;

import org.imaginnovate.entity.document.DocumentStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResultDto {

    @JsonIgnore
    private Integer id;
    private Integer assignment;
    private Integer user;
    private LocalDateTime attemptDate;
    private LocalDateTime completedDate;
    private Short noOfQuestions;
    private Integer score;
    private Double percentage;
    private DocumentStatus status;
    
}
