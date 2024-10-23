package org.imaginnovate.repository.common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.imaginnovate.dto.common.employee.EmployeeDto;
import org.imaginnovate.entity.common.Employee;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRepo implements PanacheRepositoryBase<Employee, Integer> {
    public List<EmployeeDto> findAllEmployees() {
        String sql = "SELECT e.id, e.first_name AS firstName, e.last_name AS lastName, e.gender, e.email, " +
                "e.designation, e.start_date AS startDate, e.end_date AS endDate, " +
                "COALESCE(CONCAT(m.first_name, ' ', m.last_name), 'N/A') AS reportsTo, e.can_approve_timesheets AS canApproveTimesheets, "
                +
                "COALESCE(e.created_by) AS createdBy, e.created_at AS createdAt, e.modified_by AS modifiedBy, e.modified_at AS modifiedAt, "
                +
                "e.deleted_by AS deletedBy, e.deleted_at AS deletedAt " +
                "FROM employees e " +
                "LEFT JOIN employees m ON e.reports_to_id = m.id " +
                "ORDER BY e.id ASC";

        @SuppressWarnings("unchecked")
        List<Object[]> results = getEntityManager().createNativeQuery(sql).getResultList();

        List<EmployeeDto> employees = new ArrayList<>();
        for (Object[] row : results) {
            EmployeeDto employee = new EmployeeDto();
            employee.setId((Integer) row[0]);
            employee.setFirstName((String) row[1]);
            employee.setLastName((String) row[2]);
            employee.setGender((Character) row[3]);
            employee.setEmail((String) row[4]);
            employee.setDesignation((String) row[5]);
            employee.setStartDate(((Timestamp) row[6]).toLocalDateTime());
            employee.setEndDate(row[7] != null ? ((Timestamp) row[7]).toLocalDateTime() : null);
            employee.setReportsTo((String) row[8]);
            employee.setCanApproveTimesheets((Boolean) row[9]);
            employee.setCreatedBy((Integer)(row[10]));
            employee.setCreatedAt(((Timestamp) row[11]).toLocalDateTime());
            employee.setModifiedBy(row[12] != null ? (Integer) row[12] : null);
            employee.setModifiedAt(row[13] != null ? ((Timestamp) row[13]).toLocalDateTime() : null);
            employee.setDeletedBy(row[14] != null ? (Integer) row[14] : null);
            employee.setDeletedAt(row[15] != null ? ((Timestamp) row[15]).toLocalDateTime() : null);
            employees.add(employee);
        }

        return employees;
    }

    public Employee findById(Integer id) {
        try {
            return find("id", id).firstResult();
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee by ID", e);
        }
    }

    public Optional<Employee> findByIdOptional(Integer id) {
        try {
            return Optional.ofNullable(findById(id));
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee by ID optionally", e);
        }
    }

    public Employee findById(Employee reportsTo) {
        return findById(reportsTo.getId());
    }

    public boolean canApproveTimesheets(Integer employeeId) {
        Boolean result = getEntityManager().createQuery(
                "SELECT e.canApproveTimesheets FROM Employee e WHERE e.id = :employeeId",
                Boolean.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();
        return result != null && result;
    }

    public boolean canApprove(Employee employee) {
        return canApproveTimesheets(employee.getId());
    }

    public Employee findByEmployeeName(String employeeName) {
        String sql = "SELECT * FROM employees e WHERE CONCAT(e.first_name, ' ', e.last_name) = :employeeName";
        return (Employee) getEntityManager().createNativeQuery(sql, Employee.class)
                .setParameter("employeeName", employeeName)
                .getSingleResult();
    }

    public Employee findEmployeeById(Integer integer) {
        String sql = "SELECT * FROM employees e WHERE e.id = :reportsTo";
        return (Employee) getEntityManager().createNativeQuery(sql, Employee.class)
                .setParameter("reportsTo", integer)
                .getSingleResult();
    }

}
