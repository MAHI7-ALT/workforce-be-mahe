package org.imaginnovate.repository.document;

import java.util.List;
import java.util.stream.Collectors;

import org.imaginnovate.dto.documents.userdocument.GetUserDocumentContentDto;
import org.imaginnovate.dto.documents.userdocument.GetUserDocumentDashboardDto;
import org.imaginnovate.dto.documents.userdocument.GetUserDocumentDto;
import org.imaginnovate.dto.documents.userdocument.UserDocumentDto;
import org.imaginnovate.entity.document.UserDocuments;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserDocumentsRepo implements PanacheRepositoryBase<UserDocuments, Integer> {

    @Transactional
    @SuppressWarnings("unchecked")
    public List<GetUserDocumentDto> findAllUserDocumentsByUserId(int userId, int pageNumber, int pageSize) {
        String sql = "SELECT ud.document_id, d.title, ds.name, d.serial_no " +
                "FROM dc_user_documents ud " +
                "JOIN dc_documents d ON ud.document_id = d.id " +
                "JOIN dc_status ds ON ud.status_id = ds.id " +
                "WHERE ud.user_id = :userId";
        int firstResult = (pageNumber - 1) * pageSize;
        Query nativeQuery = getEntityManager().createNativeQuery(sql, GetUserDocumentDto.class)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .setParameter("userId", userId);
        return nativeQuery.getResultList();
    }

    @Transactional
    public GetUserDocumentContentDto findUserDocumentContentByUserIdAndDocumentId(int userId, int documentId,
            int pageNumber, int pageSize) {
        String sql = "SELECT d.id, d.title, d.content " +
                "FROM dc_user_documents ud " +
                "JOIN dc_documents d ON ud.document_id = d.id " +
                "WHERE ud.user_id = :userId AND ud.document_id = :documentId";
        int firstResult = (pageNumber - 1) * pageSize;
        Query nativeQuery = getEntityManager().createNativeQuery(sql, GetUserDocumentContentDto.class)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .setParameter("userId", userId)
                .setParameter("documentId", documentId);
        return (GetUserDocumentContentDto) nativeQuery.getSingleResult();
    }

    @Transactional
    public GetUserDocumentDashboardDto findDocumentCounts(int userId, int pageNumber, int pageSize) {
        String sql = "SELECT " +
                "  (SELECT COUNT(*) FROM dc_documents) AS totalDocuments, " +
                "  (SELECT COUNT(*) FROM dc_user_documents WHERE user_id = :userId) AS userDocuments, " +
                "  (SELECT COUNT(*) FROM dc_user_documents u" +
                "   JOIN dc_status ds ON u.status_id = ds.id " +
                "   WHERE u.user_id = :userId AND ds.name = 'completed') AS completedDocuments ";
        int firstResult = (pageNumber - 1) * pageSize;
        Query nativeQuery = getEntityManager().createNativeQuery(sql, GetUserDocumentDashboardDto.class)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .setParameter("userId", userId);
        return (GetUserDocumentDashboardDto) nativeQuery.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<GetUserDocumentDto> getAllUserDocuments() {
        String sql = "SELECT ud.document_id AS documentId, d.title AS documentTitle, ds.name AS status, d.serial_no AS serialNo "
                +
                "FROM dc_user_documents ud " +
                "JOIN dc_documents d ON ud.document_id = d.id " +
                "JOIN dc_status ds ON ud.status_id = ds.id";

        Query query = getEntityManager().createNativeQuery(sql);
        List<Object[]> results = query.getResultList();
        return results.stream()
                .map(result -> new GetUserDocumentDto(
                        ((Number) result[0]).intValue(),
                        (String) result[1],
                        (String) result[2],
                        ((Number) result[3]).intValue()))
                .collect(Collectors.toList());
}

public List<UserDocumentDto> findByUserId(Integer user) {
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
}


}
