package org.imaginnovate.repository.common;

import java.util.List;

import org.imaginnovate.dto.common.employeeproject.EmployeeProjectDto;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeProject;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmployeeProjectRepo implements PanacheRepositoryBase<EmployeeProject, Integer> {

    @Transactional
    public List<EmployeeProjectDto> findAllEmployeeProjects() {
        return getEntityManager()
                .createQuery("SELECT NEW org.imaginnovate.dto.common.employeeproject.EmployeeProjectDto(" +
                        "ep.id, " +
                        "ep.employeeDivision.id, " +
                        "ep.project.id, " +
                        "ep.createdBy.id, " +
                        "ep.createdAt, " +
                        "ep.modifiedBy.id, " +
                        "ep.modifiedAt, " +
                        "ep.deletedBy.id, " +
                        "ep.deletedAt) " +
                        "FROM EmployeeProject ep", EmployeeProjectDto.class)
                .getResultList();
    }

    public EmployeeProject findById(int id) {
        return id == 0 ? null : getEntityManager().find(EmployeeProject.class, id);
    }

    public void delete(EmployeeProject employeeProjects) {
        getEntityManager().remove(employeeProjects);
    }

    public void persist(EmployeeProject employeeProjects) {
        getEntityManager().persist(employeeProjects);
    }

    @Transactional
    public List<EmployeeProject> findByEmployeeAndProject(Integer employeeDivisionId, Integer projectId) {
        return getEntityManager().createQuery(
                "SELECT ep FROM EmployeeProject ep " +
                        "WHERE ep.employeeDivision.id = :employeeDivisionId AND ep.project.id = :projectId",
                EmployeeProject.class)
                .setParameter("employeeDivisionId", employeeDivisionId)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public List<EmployeeProject> findByEmployeeId(Integer employeeId) {
        return getEntityManager().createQuery(
                "SELECT ep FROM EmployeeProject ep " +
                        "JOIN ep.employeeDivision ed " +
                        "WHERE ed.employeeId.id = :employeeId",
                EmployeeProject.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }

    public boolean isAssignedToProject(Integer employeeDivisionId, Integer projectId) {
        return getEntityManager().createQuery(
                "SELECT ep FROM EmployeeProject ep " +
                        "WHERE ep.employeeDivision.id = :employeeDivisionId AND ep.project.id = :projectId",
                EmployeeProject.class)
                .setParameter("employeeDivisionId", employeeDivisionId)
                .setParameter("projectId", projectId)
                .getResultList().size() > 0;
    }

    public boolean canApproveInProject(Employee employee) {
        return getEntityManager().createQuery(
                "SELECT COUNT(ep) > 0 FROM EmployeeProject ep " +
                        "JOIN ep.employeeDivision ed " +
                        "JOIN ed.employee e " +
                        "WHERE e.id = :employeeId AND e.canApproveTimesheets = true",
                Boolean.class)
                .setParameter("employeeId", employee.getId())
                .getSingleResult();
    }

    public boolean canApproveTimesheetsInSomeProject(Integer employeeId, Integer projectId) {
        return getEntityManager().createQuery(
                "SELECT COUNT(ep) > 0 FROM EmployeeProject ep " +
                        "JOIN ep.employeeDivision ed " +
                        "JOIN ed.employee e " +
                        "WHERE ep.project.id = :projectId AND e.id = :employeeId AND e.canApproveTimesheets = true",
                Boolean.class)
                .setParameter("employeeId", employeeId)
                .setParameter("projectId", projectId)
                .getSingleResult();
    }

    public EmployeeProject findById(EmployeeProject employeeProjectId) {
        return getEntityManager().find(EmployeeProject.class, employeeProjectId.getId());
    }

    public EmployeeProject findByName(String employeeProjectName) {
        try {
            return getEntityManager()
                    .createQuery("SELECT ep FROM EmployeeProject ep WHERE ep.name = :employeeProjectName",
                            EmployeeProject.class)
                    .setParameter("employeeProjectName", employeeProjectName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
