package org.imaginnovate.service.common;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.common.employeeproject.EmployeeProjectDto;
import org.imaginnovate.dto.common.employeeproject.EmployeeProjectDtoPost;
import org.imaginnovate.entity.common.EmployeeDivision;
import org.imaginnovate.entity.common.EmployeeProject;
import org.imaginnovate.entity.common.Project;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.EmployeeDivisionRepo;
import org.imaginnovate.repository.common.EmployeeProjectRepo;
import org.imaginnovate.repository.common.ProjectRepo;
import org.imaginnovate.repository.common.UserRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class EmployeeProjectService {

    @Inject
    private EmployeeProjectRepo employeeProjectsRepo;

    @Inject
    private EmployeeDivisionRepo employeeDivisionRepo;

    @Inject
    private UserRepo usersRepo;

    @Inject
    private ProjectRepo projectRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllEmployeeProjects() {
        try {
            List<EmployeeProjectDto> employeeProjectsDto = employeeProjectsRepo.findAllEmployeeProjects();
            if (employeeProjectsDto.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No employee projects found.").build();
            }
            return Response.ok(employeeProjectsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Transactional
    public Response createEmployeeProject(EmployeeProjectDtoPost employeeProjectsDto) {
        try {
            if (employeeProjectsDto.getEmployeeDivision() == null || employeeProjectsDto.getProject() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("EmployeeDivision and Project must be provided.").build();
            }

            List<EmployeeProject> existingEmployeeProjects = employeeProjectsRepo.findByEmployeeAndProject(
                    employeeProjectsDto.getEmployeeDivision(), employeeProjectsDto.getProject());

            if (!existingEmployeeProjects.isEmpty()) {
                return Response.status(Response.Status.CONFLICT).entity("Employee-Project already exists.").build();
            }

            Project project = projectRepo.findById(employeeProjectsDto.getProject());
            if (project == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found.").build();
            }

            EmployeeDivision employeeDivision = employeeDivisionRepo
                    .findById(employeeProjectsDto.getEmployeeDivision());
            if (employeeDivision == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("EmployeeDivision not found.").build();
            }

            User createdBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                createdBy = usersRepo.findById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("CreatedBy user not found")
                            .build();
                }

            }

            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setProject(project);
            employeeProject.setEmployeeDivision(employeeDivision);
            employeeProject.setCreatedAt(LocalDateTime.now());
            employeeProject.setCreatedBy(createdBy);

            employeeProjectsRepo.persist(employeeProject);
            employeeProjectsDto.setId(employeeProject.getId());

            return Response.status(Response.Status.CREATED).entity(employeeProjectsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }

    @Transactional
    public Response updateEmployeeProjectById(int id, EmployeeProjectDtoPost employeeProjectsDto) {
        try {
            EmployeeProject employeeProject = employeeProjectsRepo.findById(id);
            if (employeeProject == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee Project not found.")
                        .build();
            }

            if (employeeProjectsDto.getProject() != null) {
                Project project = projectRepo.findById(employeeProjectsDto.getProject());
                if (project == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Project not found.")
                            .build();
                }
                employeeProject.setProject(project);
            }

            if (employeeProjectsDto.getEmployeeDivision() != null) {
                EmployeeDivision employeeDivision = employeeDivisionRepo
                        .findById(employeeProjectsDto.getEmployeeDivision());
                if (employeeDivision == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Employee Division not found.")
                            .build();
                }
                employeeProject.setEmployeeDivision(employeeDivision);
            }
            User modifiedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                modifiedBy = usersRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ModifiedBy user not found.")
                            .build();
                }
                employeeProject.setModifiedBy(modifiedBy);
            }

            employeeProject.setModifiedAt(LocalDateTime.now());
            employeeProjectsRepo.persist(employeeProject);
            employeeProjectsDto.setId(employeeProject.getId());
            return Response.ok(employeeProjectsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error.")
                    .build();
        }
    }

    @Transactional
    public Response deleteEmployeeProjectById(int id) {
        try {
            EmployeeProject employeeProject = employeeProjectsRepo.findById(id);
            if (employeeProject == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Employee Project not found.").build();
            }

            User deletedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                deletedBy = usersRepo.findById(currentUserId);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("DeletedBy user not found.").build();
                }

                employeeProject.setDeletedBy(deletedBy);
                employeeProject.setDeletedAt(LocalDateTime.now());
            }

            employeeProjectsRepo.delete(employeeProject);
            return Response.noContent().build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error")
                    .build();
        }
    }

    public Response getEmployeeProjectById(int id) {
        try {
            EmployeeProject employeeProject = employeeProjectsRepo.findById(id);
            if (employeeProject == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Employee Project not found.").build();
            }

            EmployeeProjectDto employeeProjectsDto = new EmployeeProjectDto();
            employeeProjectsDto.setId(employeeProject.getId());
            employeeProjectsDto.setEmployeeDivision(employeeProject.getEmployeeDivision().getId());
            employeeProjectsDto.setProject(employeeProject.getProject().getId());
            employeeProjectsDto.setCreatedBy(
                    employeeProject.getCreatedBy() != null ? employeeProject.getCreatedBy().getId() : null);
            employeeProjectsDto.setCreatedAt(employeeProject.getCreatedAt());
            employeeProjectsDto.setModifiedBy(
                    employeeProject.getModifiedBy() != null ? employeeProject.getModifiedBy().getId() : null);
            employeeProjectsDto.setModifiedAt(employeeProject.getModifiedAt());
            employeeProjectsDto.setDeletedBy(
                    employeeProject.getDeletedBy() != null ? employeeProject.getDeletedBy().getId() : null);
            employeeProjectsDto.setDeletedAt(employeeProject.getDeletedAt());

            return Response.ok(employeeProjectsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }
}
