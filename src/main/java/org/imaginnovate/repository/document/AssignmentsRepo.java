package org.imaginnovate.repository.document;

import java.util.List;

import org.imaginnovate.dto.documents.assignment.AssignmentDto;
import org.imaginnovate.entity.document.Assignments;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AssignmentsRepo implements PanacheRepositoryBase<Assignments, Integer> {
    @Transactional
    public List<AssignmentDto> findAllAssignments() {
        String sql = "SELECT a.id, " +
                "a.document, " +
                "a.title, " +
                "a.time_limit, " +
                "a.passing_percentage, " +
                "a.status," +
                "a.max_questions, " +
                "a.created_by, " +
                "a.created_at, " +
                "a.modified_by, " +
                "a.modified_at, " +
                "a.deleted_by, " +
                "a.deleted_at " +
                "FROM dc_assignments a";

        Query query = getEntityManager().createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<AssignmentDto> results = query.getResultList();
        return results;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Object[]> startQuizQuery(Integer assignmentId) {
        Query query = getEntityManager().createNativeQuery(
                "WITH limited_questions AS (" +
                        "  SELECT q2.id " +
                        "  FROM dc_questions q2 " +
                        "  WHERE q2.assignment_id = :assignment_id " +
                        "  ORDER BY RAND() " +
                        "  LIMIT 3" +
                        ") " +
                        "SELECT q.id AS question_id, q.content AS question_text, qo.content AS options " +
                        "FROM dc_questions q " +
                        "WHERE q.id IN (SELECT id FROM limited_questions) " +
                        "ORDER BY q.id");
        query.setParameter("assignment_id", assignmentId);
        return query.getResultList();
    }

}
