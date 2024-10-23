package org.imaginnovate.controller.common;

import org.imaginnovate.dto.common.employeeproject.EmployeeProjectDtoPost;
import org.imaginnovate.service.common.EmployeeProjectService;

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


@Path("/employee-projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeProjectController {
    @Inject
    EmployeeProjectService employeeProjectsService;

    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllEmployeeProjects() {
        return employeeProjectsService.getAllEmployeeProjects();
    }

    @POST
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response createEmployeeProject(EmployeeProjectDtoPost employeeProjectsDto) {
        return employeeProjectsService.createEmployeeProject(employeeProjectsDto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response updateEmployeeProjectById(@PathParam("id") int id, EmployeeProjectDtoPost employeeProjectsDto) {
        return employeeProjectsService.updateEmployeeProjectById(id, employeeProjectsDto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response deleteEmployeeProjectById(@PathParam("id") int id) {
        return employeeProjectsService.deleteEmployeeProjectById(id);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response getEmployeeProjectById(@PathParam("id") int id) {
        return employeeProjectsService.getEmployeeProjectById(id);
    }
}
