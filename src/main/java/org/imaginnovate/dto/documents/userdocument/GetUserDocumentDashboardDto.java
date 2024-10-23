package org.imaginnovate.dto.documents.userdocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDocumentDashboardDto {

    private Long totalDocuments;
    private Long userDocuments;
    private Long completedDocuments;

}
