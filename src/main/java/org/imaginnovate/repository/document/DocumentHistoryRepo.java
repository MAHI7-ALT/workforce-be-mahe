
package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.DocumentHistoryDto;
import org.imaginnovate.entity.document.DocumentHistory;
import org.imaginnovate.entity.document.Documents;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DocumentHistoryRepo implements PanacheRepositoryBase<DocumentHistory, Integer> {
        
      @SuppressWarnings("unchecked")
    public List<DocumentHistoryDto> getAllDocumentHistoryDetails(int pageNumber, int pageSize) {
        String sql = "SELECT dh.id, " +
        "dh.serial_no AS serialNo, " +
        "dh.document_id AS document, " +
        "dh.title, " +
        "dh.content, " +
        "dt.name AS documentType, " +
        "dh.is_recurring AS isRecurring, " +
        "dh.recurring_type AS recurringType, " +
        "dh.recurring_time_frame AS recurringTimeFrame, " +
        "dh.recurring_fixed_on AS recurringFixedOn, " +
        "u.name AS createdBy, " +
        "dh.created_at AS createdAt " +
        "FROM dc_document_history dh " +
        "LEFT JOIN dc_document_type dt ON dh.document_type_id = dt.id "+
        "LEFT JOIN users u ON dh.created_by = u.id";
        int firstResult = (pageNumber - 1) * pageSize;
        Query nativeQuery = getEntityManager().createNativeQuery(sql, DocumentHistoryDto.class).setFirstResult(firstResult)
                .setMaxResults(pageSize);
        return nativeQuery.getResultList();
    }
    @Transactional
    public void insertDocumentHistory(Documents document) {
        String insertHistorySql = "INSERT INTO dc_document_history " +
                "(document_id, serial_no, title, content, document_type_id, is_recurring, recurring_type, recurring_time_frame, recurring_fixed_on, created_at, created_by) " +
                "VALUES (:documentId, :serialNo, :title, :content, :documentTypeId, :isRecurring, :recurringType, :recurringTimeFrame, :recurringFixedOn, :createdAt, :createdBy)";
        
        getEntityManager().createNativeQuery(insertHistorySql)
                .setParameter("documentId", document.getId())
                .setParameter("serialNo", document.getSerialNo())
                .setParameter("title", document.getTitle())
                .setParameter("content", document.getContent())
                .setParameter("documentTypeId", document.getDocumentType().getId())
                .setParameter("isRecurring", document.getIsRecurring())
                .setParameter("recurringType", document.getRecurringType())
                .setParameter("recurringTimeFrame", document.getRecurringTimeFrame())
                .setParameter("recurringFixedOn", document.getRecurringFixedOn())
                .setParameter("createdAt", document.getCreatedAt())
                .setParameter("createdBy", document.getCreatedBy().getId())
                .executeUpdate();
    }
    
    @Transactional
        public Documents originalDocument(Documents existingDocument) {
                Documents originalDocument = new Documents();
                originalDocument.setId(existingDocument.getId());
                originalDocument.setSerialNo(existingDocument.getSerialNo());
                originalDocument.setTitle(existingDocument.getTitle());
                originalDocument.setContent(existingDocument.getContent());
                originalDocument.setDocumentType(existingDocument.getDocumentType());
                originalDocument.setIsRecurring(existingDocument.getIsRecurring());
                originalDocument.setRecurringType(existingDocument.getRecurringType());
                originalDocument.setRecurringTimeFrame(existingDocument.getRecurringTimeFrame());
                originalDocument.setRecurringFixedOn(existingDocument.getRecurringFixedOn());
                originalDocument.setCreatedBy(existingDocument.getCreatedBy());
                originalDocument.setCreatedAt(existingDocument.getCreatedAt());
                return originalDocument;
        }

}