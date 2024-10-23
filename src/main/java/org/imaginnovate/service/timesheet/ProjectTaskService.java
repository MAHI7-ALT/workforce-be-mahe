package org.imaginnovate.service.timesheet;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.timesheets.projecttask.ProjectTaskDto;
import org.imaginnovate.dto.timesheets.projecttask.ProjectTaskDtoPost;
import org.imaginnovate.entity.common.Project;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.timesheet.ProjectTask;
import org.imaginnovate.entity.timesheet.Task;
import org.imaginnovate.repository.common.ProjectRepo;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.timesheet.ProjectTaskRepo;
import org.imaginnovate.repository.timesheet.TaskRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProjectTaskService {

    @Inject
    ProjectTaskRepo projectTasksRepo;

    @Inject
    ProjectRepo projectsRepo;

    @Inject
    TaskRepo tasksRepo;

    @Inject
    UserRepo userRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllProjectTasks() {
        try {
            List<ProjectTaskDto> projectTasks = projectTasksRepo.findAllProjectTasks();
            if (projectTasks.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("There are no project tasks").build();
            }
            return Response.status(Response.Status.OK).entity(projectTasks).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response createProjectTask(ProjectTaskDtoPost projectTasksDto) {
        try {
            ProjectTask existingProjectTask = projectTasksRepo.findByProjectAndTask(projectTasksDto.getProject(),
                    projectTasksDto.getTask());
            if (existingProjectTask != null) {
                return Response.status(Response.Status.CONFLICT).entity("Project Task already exists").build();
            }

            Project project = projectsRepo.findById(projectTasksDto.getProject());
            if (project == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }

            Task task = tasksRepo.findById(projectTasksDto.getTask());
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
            }
            User createdBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                createdBy = userRepo.findUserById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("CreatedBy user not found").build();
                }
            }

            ProjectTask newProjectTask = new ProjectTask();
            newProjectTask.setProject(project);
            newProjectTask.setTask(task);
            newProjectTask.setCreatedBy(createdBy);
            newProjectTask.setCreatedAt(LocalDateTime.now());

            projectTasksRepo.persist(newProjectTask);
            projectTasksDto.setId(newProjectTask.getId());

            return Response.status(Response.Status.CREATED).entity(projectTasksDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    @Transactional
    public Response updateProjectTaskById(Integer id, ProjectTaskDtoPost projectTasksDto) {
        try {
            ProjectTask projectTask = projectTasksRepo.findById(id);
            if (projectTask == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project Task not found").build();
            }

            if (projectTasksDto.getProject() != null) {
                Project project = projectsRepo.findById(projectTasksDto.getProject());
                if (project != null) {
                    projectTask.setProject(project);
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
                }
            }

            if (projectTasksDto.getTask() != null) {
                Task task = tasksRepo.findById(projectTasksDto.getTask());
                if (task != null) {
                    projectTask.setTask(task);
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
                }
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User modifiedBy = userRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("ModifiedBy user not found").build();
                }
                projectTask.setModifiedBy(modifiedBy);
            }

            projectTask.setModifiedAt(LocalDateTime.now());
            projectTasksRepo.persist(projectTask);
            ProjectTaskDto updatedDto = convertToDto(projectTask);

            return Response.ok(updatedDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    @Transactional
    public Response deleteProjectTaskById(Integer id) {
        try {
            ProjectTask projectTask = projectTasksRepo.findById(id);
            if (projectTask == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project Task not found").build();
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User deletedBy = userRepo.findUserById(currentUserId);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("DeletedBy user not found").build();
                }
                projectTask.setDeletedBy(deletedBy);
                projectTask.setDeletedAt(LocalDateTime.now());

                projectTasksRepo.persist(projectTask);
            }

            return Response.status(Response.Status.OK).entity("Project Task deleted successfully").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    public Response getProjectTaskById(Integer id) {
        try {
            ProjectTask projectTask = projectTasksRepo.findById(id);
            if (projectTask == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project Task not found").build();
            }
            ProjectTaskDto dto = convertToDto(projectTask);
            return Response.status(Response.Status.OK).entity(dto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    private ProjectTaskDto convertToDto(ProjectTask projectTask) {
        ProjectTaskDto dto = new ProjectTaskDto();
        dto.setId(projectTask.getId());
        dto.setProject(projectTask.getProject() != null ? projectTask.getProject().getId() : null);
        dto.setTask(projectTask.getTask() != null ? projectTask.getTask().getId() : null);
        dto.setCreatedBy(projectTask.getCreatedBy() != null ? projectTask.getCreatedBy().getId() : null);
        dto.setCreatedAt(projectTask.getCreatedAt());
        dto.setModifiedBy(projectTask.getModifiedBy() != null ? projectTask.getModifiedBy().getId() : null);
        dto.setModifiedAt(projectTask.getModifiedAt());
        dto.setDeletedBy(projectTask.getDeletedBy() != null ? projectTask.getDeletedBy().getId() : null);
        dto.setDeletedAt(projectTask.getDeletedAt());
        return dto;
    }
}
