package org.imaginnovate.repository.common;

import java.util.List;

import org.imaginnovate.dto.common.project.ProjectDto;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.entity.common.Project;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectRepo implements PanacheRepositoryBase<Project, Integer> {

    public List<ProjectDto> findAllProjects() {
        return getEntityManager()
                .createQuery("SELECT NEW org.imaginnovate.dto.common.project.ProjectDto(p.id, p.name, p.description, p.division.id, "
                        +
                        "p.createdBy.id, p.createdAt, p.modifiedBy.id, p.modifiedAt, p.deletedBy.id, p.deletedAt) " +
                        "FROM Project p", ProjectDto.class)
                .getResultList();
    }

    public List<Project> findByDivisionId(Division divisions) {
        String jpql = "SELECT p FROM Project p WHERE p.division = :division";
        return getEntityManager().createQuery(jpql, Project.class).setParameter("division", divisions).getResultList();
    }

    public Project findById(Project projectId) {
        return getEntityManager().find(Project.class, projectId);
    }

}