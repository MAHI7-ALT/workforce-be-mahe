package org.imaginnovate.repository.common;

import java.util.List;

import org.imaginnovate.dto.common.employeedivision.EmployeeDivisionDto;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeDivision;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class EmployeeDivisionRepo implements PanacheRepositoryBase<EmployeeDivision, Integer> {

    public List<EmployeeDivisionDto> findAllEmployeeDivisions() {
        return getEntityManager().createQuery(
                "SELECT NEW org.imaginnovate.dto.common.employeedivision.EmployeeDivisionDto(" +
                        "ed.id, ed.employee.id, ed.division.id, ed.primaryDivision," +
                        "ed.createdBy.id, ed.createdAt, ed.modifiedBy.id, ed.modifiedAt, ed.deletedBy.id, ed.deletedAt) "
                        +
                        "FROM EmployeeDivision ed " +
                        "ORDER BY ed.employee.id ASC, ed.division.id ASC",
                EmployeeDivisionDto.class)
                .getResultList();
    }

    public EmployeeDivision findByEmployeeDivisionId(Employee employee) {
        return getEntityManager().find(EmployeeDivision.class, employee);
    }

    public EmployeeDivision findById(int id) {
        return getEntityManager().find(EmployeeDivision.class, id);
    }

    public void persist(EmployeeDivision employeeDivision) {
        getEntityManager().persist(employeeDivision);
    }

    public List<EmployeeDivision> findByEmployeeId(Integer id) {
        return getEntityManager().createQuery(
                "SELECT ed FROM EmployeeDivision ed WHERE ed.employee.id = :id",
                EmployeeDivision.class)
                .setParameter("id", id)
                .getResultList();
    }

    public boolean existsByDivisionIdAndEmployeeId(Integer division, Integer employee) {
        return getEntityManager().createQuery(
                "SELECT COUNT(ed) FROM EmployeeDivision ed WHERE ed.division.id = :division AND ed.employee.id = :employee",
                Long.class)
                .setParameter("division", division)
                .setParameter("employee", employee)
                .getSingleResult() > 0;
    }

    public boolean canApproveInDivision(Employee approvingEmployee) {
        return getEntityManager().createQuery(
                "SELECT COUNT(ed) > 0 FROM EmployeeDivision ed " +
                        "JOIN ed.employee e " +
                        "WHERE e.id = :id AND e.canApproveTimesheets = true",
                Boolean.class)
                .setParameter("id", approvingEmployee.getId())
                .getSingleResult();
    }

    public EmployeeDivision findById(EmployeeDivision employeeDivision) {
        return getEntityManager().find(EmployeeDivision.class, employeeDivision.getId());
    }

    public boolean canApproveTimesheetsInSomeDivisions(Integer id, Division division) {
        return getEntityManager().createQuery(
                "SELECT COUNT(ed) > 0 FROM EmployeeDivision ed " +
                        "JOIN ed.employee e " +
                        "WHERE e.id = :id AND e.canApproveTimesheets = true AND ed.division = :division",
                Boolean.class)
                .setParameter("id", id)
                .setParameter("division", division)
                .getSingleResult();
    }

    public boolean existsByEmployeeIdAndDivisionId(Integer id, Division division) {
        return getEntityManager().createQuery(
                "SELECT COUNT(ed) FROM EmployeeDivision ed WHERE ed.employee.id = :id AND ed.division = :division",
                Long.class)
                .setParameter("id", id)
                .setParameter("division", division)
                .getSingleResult() > 0;
    }

    public EmployeeDivision findByName(String employeeDivisionName) {
        try {
            return getEntityManager()
                    .createQuery("SELECT ed FROM EmployeeDivision ed WHERE ed.name = :employeeDivisionName",
                            EmployeeDivision.class)
                    .setParameter("employeeDivisionName", employeeDivisionName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}