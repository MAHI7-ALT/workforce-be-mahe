package org.imaginnovate.service.common;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.common.project.ProjectDto;
import org.imaginnovate.dto.common.project.ProjectDtoPost;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.entity.common.Project;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.DivisionRepo;
import org.imaginnovate.repository.common.ProjectRepo;
import org.imaginnovate.repository.common.UserRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class ProjectService {

    @Inject
    private ProjectRepo projectRepo;

    @Inject
    private DivisionRepo divisionRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllProjects() {
        try {
            List<ProjectDto> projectsDto = projectRepo.findAllProjects();
            if (projectsDto.isEmpty()) {
                return Response.status(Status.NOT_FOUND).entity("No projects found.").build();
            }
            return Response.ok(projectsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    @Transactional
    public Response createProject(ProjectDtoPost projectDto) {
        try {
            if (projectDto.getId() != null) {
                Project existingProject = projectRepo.findById(projectDto.getId());
                if (existingProject != null) {
                    return Response.status(Status.CONFLICT).entity("Project already exists.").build();
                }
            }

            Project project = new Project();
            project.setName(projectDto.getName());
            project.setDescription(projectDto.getDescription());

            if (projectDto.getDivision() != null && projectDto.getDivision() != 0) {
                Division division = divisionRepo.findById(projectDto.getDivision());
                if (division != null) {
                    project.setDivision(division);
                } else {
                    return Response.status(Status.NOT_FOUND).entity("Division not found.").build();
                }
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User createdBy = userRepo.findUserById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Status.NOT_FOUND).entity("CreatedBy user not found.").build();
                }
                project.setCreatedBy(createdBy);
            }

            project.setCreatedAt(LocalDateTime.now());
            project.setId(projectDto.getId());
            projectRepo.persist(project);
            projectDto.setId(project.getId());

            return Response.status(Status.CREATED).entity(projectDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    @Transactional
    public Response updateProjectById(int id, ProjectDtoPost projectDto) {
        try {
            Project project = projectRepo.findById(id);
            if (project == null) {
                return Response.status(Status.NOT_FOUND).entity("Project not found.").build();
            }

            if (projectDto.getName() != null) {
                project.setName(projectDto.getName());
            }

            if (projectDto.getDescription() != null) {
                project.setDescription(projectDto.getDescription());
            }

            if (projectDto.getDivision() != null && projectDto.getDivision() != 0) {
                Division division = divisionRepo.findById(projectDto.getDivision());
                if (division != null) {
                    project.setDivision(division);
                } else {
                    return Response.status(Status.NOT_FOUND).entity("Division not found.").build();
                }
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User modifiedBy = userRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("ModifiedBy user not found.").build();
                }
                project.setModifiedBy(modifiedBy);
                project.setModifiedAt(LocalDateTime.now());
            }

            projectRepo.persist(project);
            ProjectDto updatedProjectDto = convertToDto(project);
            return Response.ok(updatedProjectDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    private ProjectDto convertToDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setDivision(project.getDivision() != null ? project.getDivision().getId() : null);
        projectDto.setCreatedBy(project.getCreatedBy() != null ? project.getCreatedBy().getId() : null);
        projectDto.setCreatedAt(project.getCreatedAt());
        projectDto.setModifiedBy(project.getModifiedBy() != null ? project.getModifiedBy().getId() : null);
        projectDto.setModifiedAt(project.getModifiedAt());
        projectDto.setDeletedBy(project.getDeletedBy() != null ? project.getDeletedBy().getId() : null);
        projectDto.setDeletedAt(project.getDeletedAt());
        return projectDto;
    }

    public Response getProjectById(int id) {
        try {
            Project project = projectRepo.findById(id);
            if (project == null) {
                return Response.status(Status.NOT_FOUND).entity("Project not found.").build();
            }
            ProjectDto projectDto = convertToDto(project);
            return Response.ok(projectDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    @Transactional
    public Response deleteProjectById(int id) {
        try {
            Project project = projectRepo.findById(id);
            if (project == null) {
                return Response.status(Status.NOT_FOUND).entity("Project not found.").build();
            }
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User deletedBy = userRepo.findUserById(currentUserId);
                project.setDeletedBy(deletedBy);
            }

            project.setDeletedAt(LocalDateTime.now());
            projectRepo.persist(project);
            return Response.ok("Project successfully deleted.").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }
}
