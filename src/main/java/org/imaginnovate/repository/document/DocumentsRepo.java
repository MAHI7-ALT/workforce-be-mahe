package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.document.DocumentsDto;
import org.imaginnovate.entity.document.Documents;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@SuppressWarnings("unchecked")
@ApplicationScoped
public class DocumentsRepo implements PanacheRepositoryBase<Documents, Integer> {

    @PersistenceContext
    EntityManager em;

    public List<DocumentsDto> getAllDocumentDetails(int pageNumber, int pageSize) {
        String sql = "SELECT d.id, " +
                "dt.name AS documentType, " + 
                "d.serial_no AS serialNo," +
                "d.title," +
                "d.content," +
                "d.is_recurring AS isRecurring," +
                "d.recurring_type AS recurringType," +
                "d.recurring_time_frame AS recurringTimeFrame," +
                "d.recurring_fixed_on AS recurringFixedOn," +
                "u.name AS createdBy," +
                "d.created_at AS createdAt " +
                "FROM dc_documents d join dc_document_type dt on d.document_type_id=dt.id " +
                "LEFT JOIN users u on d.created_by=u.id " +
                "WHERE d.deleted_by is NULL ORDER BY d.serial_no";
        int firstResult = (pageNumber - 1) * pageSize;
        Query nativeQuery = em.createNativeQuery(sql, DocumentsDto.class).setFirstResult(firstResult)
                .setMaxResults(pageSize);
        return nativeQuery.getResultList();
    }

}