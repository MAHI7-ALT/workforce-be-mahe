package org.imaginnovate.service.timesheet;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.timesheets.task.TaskDto;
import org.imaginnovate.dto.timesheets.task.TaskDtoPost;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.timesheet.Task;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.timesheet.TaskRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TaskService {

    @Inject
    private TaskRepo taskRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllTasks() {
        try {
            List<TaskDto> tasks = taskRepo.findAllTasks();
            if (tasks.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No tasks found.").build();
            }
            return Response.ok(tasks).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    @Transactional
    public Response createTask(TaskDtoPost taskDto) {
        try {
            if (taskDto.getId() != null) {
                Task existingTask = taskRepo.findById(taskDto.getId());
                if (existingTask != null) {
                    return Response.status(Response.Status.CONFLICT).entity("Task already exists.").build();
                }
            }

            Task task = new Task();
            task.setName(taskDto.getName());
            task.setDescription(taskDto.getDescription());

            Integer currentUserId = authService.getCurrentUserId();
            User createdBy = null;
            if (currentUserId != null) {
                createdBy = userRepo.findById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("CreatedBy user not found")
                            .build();
                }
            }

            task.setCreatedBy(createdBy);
            task.setCreatedAt(LocalDateTime.now());
            task.setId(taskDto.getId());
            taskRepo.persist(task);
            TaskDto dto = convertToDto(task);
            return Response.status(Response.Status.CREATED).entity(dto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    @Transactional
    public Response updateTaskById(Integer id, TaskDtoPost taskDto) {
        try {
            Task task = taskRepo.findById(id);
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Task not found.").build();
            }

            if (taskDto.getName() != null) {
                task.setName(taskDto.getName());
            }

            if (taskDto.getDescription() != null) {
                task.setDescription(taskDto.getDescription());
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User modifiedBy = userRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("ModifiedBy user not found.").build();
                }
                task.setModifiedBy(modifiedBy);
            }
            task.setModifiedAt(LocalDateTime.now());
            taskRepo.persist(task);

            TaskDto updatedTaskDto = convertToDto(task);
            return Response.ok(updatedTaskDto).build();

        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    private TaskDto convertToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCreatedBy(task.getCreatedBy() != null ? task.getCreatedBy().getId() : null);
        dto.setCreatedAt(task.getCreatedAt());
        dto.setDeletedBy(task.getDeletedBy() != null ? task.getDeletedBy().getId() : null);
        dto.setDeletedAt(task.getDeletedAt());
        dto.setModifiedBy(task.getModifiedBy() != null ? task.getModifiedBy().getId() : null);
        dto.setModifiedAt(task.getModifiedAt());
        return dto;
    }

    public Response getTaskById(Integer id) {
        try {
            Task task = taskRepo.findById(id);
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Task not found.").build();
            }
            TaskDto dto = convertToDto(task);
            return Response.ok(dto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    @Transactional
    public Response deleteTaskById(Integer id) {
        try {
            Task task = taskRepo.findById(id);
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Task not found.").build();
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User deletedBy = userRepo.findUserById(currentUserId);
                if (deletedBy != null) {
                    task.setDeletedBy(deletedBy);
                }
            }

            task.setDeletedAt(LocalDateTime.now());
            taskRepo.persist(task);
            return Response.ok("Task successfully deleted.").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }
}
