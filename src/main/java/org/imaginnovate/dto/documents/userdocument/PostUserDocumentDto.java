package org.imaginnovate.dto.documents.userdocument;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserDocumentDto {

    @JsonIgnore
    private Integer id;
    private Integer user;
    private Integer document;
    @JsonIgnore
    private LocalDateTime assignedOn;
    private LocalDate targetDate;
    private Integer status;
   

}
