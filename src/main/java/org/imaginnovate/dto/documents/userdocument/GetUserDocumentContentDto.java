package org.imaginnovate.dto.documents.userdocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDocumentContentDto {

    private Integer documentId;
    private String documentTitle;
    private String documentContent;
    
}
