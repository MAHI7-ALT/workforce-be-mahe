package org.imaginnovate.repository.common;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.imaginnovate.dto.common.division.DivisionDto;
import org.imaginnovate.dto.common.division.DivisionDtoPost;
import org.imaginnovate.entity.common.Division;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DivisionRepo implements PanacheRepositoryBase<Division, Integer> {
@SuppressWarnings("unchecked")
public List<DivisionDto> findAllDivisions() {
    String sql = "SELECT d.id, d.name, COALESCE(p.name, 'No Parent') AS parentDivision, " +
                 "d.created_by AS createdBy, d.created_at AS createdAt, " +
                 "d.modified_by AS modifiedBy, d.modified_at AS modifiedAt, " +
                 "d.deleted_by AS deletedBy, d.deleted_at AS deletedAt " +
                 "FROM divisions d " +
                 "LEFT JOIN divisions p ON d.parent_id = p.id";

    List<Object[]> results = getEntityManager().createNativeQuery(sql).getResultList();

    return results.stream().map(row -> new DivisionDto(
            (Integer) row[0],
            (String) row[1],
            (String) row[2],
            (Integer) row[3],
            ((Timestamp) row[4]).toLocalDateTime(),
            (Integer) row[5],
            row[6] != null ? ((Timestamp) row[6]).toLocalDateTime() : null,
            (Integer) row[7],
            row[8] != null ? ((Timestamp) row[8]).toLocalDateTime() : null
    )).collect(Collectors.toList());
}

    
    public List<Division> findByDivisionId(int divisionId) {
        return find("id", divisionId).list();
    }    

    public Optional<DivisionDtoPost> findDivisionById(int id) {
        return Optional.ofNullable(getEntityManager().createQuery(
                "SELECT new org.imaginnovate.dto.common.division.DivisionDtoPut(d.id, d.name, p.id, d.createdBy.id, d.createdAt, " +
                        "d.modifiedBy.id, d.modifiedAt, d.deletedBy.id, d.deletedAt) FROM Division d " +
                        "LEFT JOIN d.parent p WHERE d.id = :id",
                DivisionDtoPost.class)
                .setParameter("id", id)
                .getSingleResult());
    }
    
    

    public Division findByName(String name) {
        return find("name", name).firstResult();
    }

    public Division findById(Division division) {
        return find("id", division.getId()).firstResult();
    }
}
