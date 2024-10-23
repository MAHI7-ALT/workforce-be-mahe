package org.imaginnovate.repository.timesheet;

import java.util.List;

import org.imaginnovate.dto.timesheets.projecttask.ProjectTaskDto;
import org.imaginnovate.entity.timesheet.ProjectTask;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class ProjectTaskRepo implements PanacheRepositoryBase<ProjectTask, Integer> {

    public List<ProjectTaskDto> findAllProjectTasks() {
        return getEntityManager().createQuery(
                "SELECT NEW org.imaginnovate.dto.timesheets.projecttask.ProjectTaskDto(pt.id, pt.project.id, pt.task.id, pt.createdBy.id, pt.createdAt, pt.modifiedBy.id, pt.modifiedAt, pt.deletedBy.id, pt.deletedAt) FROM ProjectTask pt LEFT JOIN pt.project",
                ProjectTaskDto.class).getResultList();
    }

    public ProjectTask findById(Integer id) {
        return id == null ? null : getEntityManager().find(ProjectTask.class, id);
    }

    public ProjectTask findByProjectAndTask(Integer project, Integer task) {
        List<ProjectTask> results = getEntityManager()
                .createQuery("SELECT pt FROM ProjectTask pt WHERE pt.project.id = :project AND pt.task.id = :task",
                        ProjectTask.class)
                .setParameter("project", project)
                .setParameter("task", task)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public ProjectTask findByName(String taskName) {
        try {
            return getEntityManager()
                    .createQuery("SELECT pt FROM ProjectTask pt WHERE pt.name = :taskName", ProjectTask.class)
                    .setParameter("taskName", taskName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
