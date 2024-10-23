    package org.imaginnovate.repository.timesheet;

    import java.util.List;

import org.imaginnovate.dto.timesheets.task.TaskDto;
import org.imaginnovate.entity.timesheet.Task;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

    @ApplicationScoped
    public class TaskRepo implements PanacheRepositoryBase<Task, Integer> {

        public List<TaskDto> findAllTasks() {
            return getEntityManager().createQuery("SELECT NEW org.imaginnovate.dto.timesheets.task.TaskDto(t.id,t.name,t.description" +
                    ",t.createdBy.id,t.createdAt,t.modifiedBy.id,t.modifiedAt,t.deletedBy.id,t.deletedAt) FROM Task t",
                    TaskDto.class).getResultList();
        }

        public Task findById(Integer id) {
            return getEntityManager().find(Task.class, id);
        }

    }