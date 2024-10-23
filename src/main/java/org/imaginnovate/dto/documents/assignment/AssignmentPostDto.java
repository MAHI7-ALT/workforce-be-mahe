package org.imaginnovate.dto.documents.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AssignmentPostDto {
 @JsonIgnore
    private Integer id;
    private Integer document;
    private String title;
    private Short timeLimit;
    private Double passingPercentage;
    private Boolean status;
    private Short maxQuestions;

    public  AssignmentPostDto(Integer id, Integer document, String title, Short timeLimit, Double passingPercentage,
            Boolean status, Short maxQuestions){
        this.id = id;
        this.document = document;
        this.title = title;
        this.timeLimit = timeLimit;
        this.passingPercentage = passingPercentage;
        this.status = status;
        this.maxQuestions = maxQuestions;
    }

}
