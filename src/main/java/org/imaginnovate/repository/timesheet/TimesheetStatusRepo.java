package org.imaginnovate.repository.timesheet;

import java.util.List;

import org.imaginnovate.entity.timesheet.TimesheetStatus;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimesheetStatusRepo implements PanacheRepositoryBase<TimesheetStatus, Integer> {

    public TimesheetStatus findById(Byte id) {
        return id == null ? null : getEntityManager().find(TimesheetStatus.class, id);
    }

    public Object findByName(String name) {
        return name;
    }

    public List<TimesheetStatus> findAllTimesheetStatuses() {
        return getEntityManager().createQuery("SELECT NEW org.imaginnovate.entity.timesheet.TimesheetStatus(t.id,t.name) FROM TimesheetStatus t",
                TimesheetStatus.class).getResultList();
    }

    public TimesheetStatus findById(TimesheetStatus statusId) {
        return statusId==null ? null : getEntityManager().find(TimesheetStatus.class, statusId);
    }

}