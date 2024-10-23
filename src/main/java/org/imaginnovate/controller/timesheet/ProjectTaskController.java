package org.imaginnovate.controller.timesheet;

import org.imaginnovate.dto.timesheets.projecttask.ProjectTaskDtoPost;
import org.imaginnovate.service.timesheet.ProjectTaskService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/project-tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectTaskController {

    @Inject
    ProjectTaskService projectTasksService;

    @GET
    @RolesAllowed({ "ADMIN" })
    public Response getAllProjectsTasks() {
        return projectTasksService.getAllProjectTasks();
    }

    @POST
    @Transactional
    @RolesAllowed({ "ADMIN" })
    public Response createProjectTask(ProjectTaskDtoPost projectTasksDto) {
        return projectTasksService.createProjectTask(projectTasksDto);

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response getProjectTaskById(@PathParam("id") Integer id) {
        return projectTasksService.getProjectTaskById(id);

    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({ "ADMIN" })
    public Response deleteProjectTaskById(@PathParam("id") int id) {
        return projectTasksService.deleteProjectTaskById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({ "ADMIN" })
    public Response updateProjectTaskById(@PathParam("id") int id, ProjectTaskDtoPost projectTasksDto) {
        return projectTasksService.updateProjectTaskById(id, projectTasksDto);
    }

}