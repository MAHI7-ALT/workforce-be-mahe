package org.imaginnovate.dto.documents.quastions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsDtoPost {

    @JsonIgnore
    private Integer id;
    private String content;
    private Integer assignment;


}
