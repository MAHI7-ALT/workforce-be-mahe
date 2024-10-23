package org.imaginnovate.Repository.document;

import org.imaginnovate.repository.document.DocumentsRepo;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class DocumentRepoTest {
    @Inject
    DocumentsRepo documentsRepo;

    // @SuppressWarnings("unused")
    // @Test
    // public void testGetAllDocuments() {
    //     List<DocumentsDto> documents = documentsRepo.getAllDocuments();
        
        
    // }

}
