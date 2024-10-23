package org.imaginnovate.repository.timesheet;

import java.util.List;

import org.imaginnovate.dto.timesheets.timesheet.TimesheetDto;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.timesheet.Timesheet;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimesheetRepo implements PanacheRepositoryBase<Timesheet, Integer> {

    public List<TimesheetDto> findAllTimesheets() {
        return getEntityManager().createQuery("SELECT new org.imaginnovate.dto.timesheets.timesheet.TimesheetDto("
                + "t.id, "
                + "ep.id, "
                + "pt.id, "
                + "t.description, "
                + "t.hoursWorked, "
                + "t.submittedBy.id, "
                + "t.submittedOn, "
                + "t.status.id, "
                + "t.approvedBy.id, "
                + "t.createdAt, "
                + "t.modifiedAt, "
                + "t.deletedBy.id, "
                + "t.deletedAt) "
                + "FROM Timesheet t "
                + "JOIN t.employeeProject ep "
                + "JOIN t.projectTask pt ", TimesheetDto.class).getResultList();
    }

    public List<TimesheetDto> findAllTimesheetsByStatus(Byte status) {
        String queryStr = "SELECT new org.imaginnovate.dto.timesheets.timesheet.TimesheetDto("
                + "t.id, ep.id, pt.id, t.description, t.hoursWorked, t.submittedBy.id, t.submittedOn, "
                + "t.status.id, t.approvedBy.id, t.createdAt, t.modifiedAt,t.deletedBy.id, t.deletedAt) "
                + "FROM Timesheet t "
                + "JOIN t.employeeProject ep " 
                + "JOIN t.projectTask pt "
                + "WHERE t.status.id = :status";

        return getEntityManager().createQuery(queryStr, TimesheetDto.class)
                .setParameter("status", status)
                .getResultList();
    }

    public boolean deleteTimesheet(int id) {
        return false;
    }

    public Timesheet findById(Integer id) {
        return id == null ? null : getEntityManager().find(Timesheet.class, id);
    }

    public void save(Timesheet timesheet) {
        getEntityManager().persist(timesheet);
    }

    public List<TimesheetDto> findTimesheetsByEmployeeId(Integer employeeId) {
        String query = "SELECT new org.imaginnovate.dto.timesheets.timesheet.TimesheetDto( " +
                "ts.id, " +
                "ep.id, " +
                "pt.id, " +
                "ts.description, " +
                "ts.hoursWorked, " +
                "ts.submittedBy.id, " +
                "ts.submittedOn, " +
                "ts.status.id, " +
                "ts.approvedBy.id, " +
                "ts.createdAt, " +
                "ts.modifiedAt, " +
                "ts.deletedBy.id, " +
                "ts.deletedAt " +
                ") " +
                "FROM Timesheet ts " +
                "JOIN ts.employeeProject ep " +
                "JOIN ts.projectTask pt " +
                "WHERE ts.submittedBy.id = :employeeId";
    
        return getEntityManager().createQuery(query, TimesheetDto.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }
    
    
    public List<Timesheet> findBySubmittedBy(Employee employee) {
        String query = "SELECT new org.imaginnovate.dto.TimesheetDto( " +
                "ts.id, " +
                "ep, " +
                "pt, " +
                "ts.description, " +
                "ts.hoursWorked, " +
                "ts.submittedBy.id, " +
                "ts.submittedOn, " +
                "ts.status.id, " +
                "ts.approvedBy.id, " +
                "ts.createdBy.id, " +
                "ts.createdAt, " +
                "ts.modifiedBy.id, " +
                "ts.modifiedAt, " +
                "ts.deletedBy.id, " +
                "ts.deletedAt " +
                ") " +
                "FROM Timesheet ts " +
                "JOIN ts.employeeProject ep " +
                "JOIN ts.projectTask pt " +
                "WHERE ep.employee.id = :employee";

        return getEntityManager().createQuery(query, Timesheet.class)
                .setParameter("employee", employee)
                .getResultList();
    }

    // @SuppressWarnings("unchecked")
    // public List<TimesheetDto> findTimesheetsByFilters(Byte status, Integer
    // userId, Integer projectId, int page, int size, String sort) {
    // StringBuilder sql = new StringBuilder("SELECT ts.id, ts.status, ts.user_id AS
    // userId, ts.project_id AS projectId, ts.submitted_on AS submittedOn ");
    // sql.append("FROM ts_timesheets ts ");
    // sql.append("WHERE 1 = 1 ");
    // if (status != null) {
    // sql.append("AND ts.status = :status ");
    // }
    // if (userId != null) {
    // sql.append("AND ts.user_id = :userId ");
    // }
    // if (projectId != null) {
    // sql.append("AND ts.project_id = :projectId ");
    // }
    // String[] sortParams = sort.split(",");
    // sql.append("ORDER BY ts.").append(sortParams[0]).append("
    // ").append(sortParams[1]);
    // Query nativeQuery = getEntityManager().createNativeQuery(sql.toString(),
    // TimesheetDto.class);
    // if (status != null) {
    // nativeQuery.setParameter("status", status);
    // }
    // if (userId != null) {
    // nativeQuery.setParameter("userId", userId);
    // }
    // if (projectId != null) {
    // nativeQuery.setParameter("projectId", projectId);
    // }
    // int firstResult = page * size;
    // nativeQuery.setFirstResult(firstResult).setMaxResults(size);
    // return nativeQuery.getResultList();
    // }

}