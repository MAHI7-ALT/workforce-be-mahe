package org.imaginnovate.dto.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponseDto {

    private Integer id;
    private Integer assignmentResult;
    private Integer question;
    private String chosenOption;
}
