package org.imaginnovate.controller.common;

import org.imaginnovate.dto.common.employeedivision.EmployeeDivisionDtoPost;
import org.imaginnovate.service.common.EmployeeDivisionService;

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

@Path("/employee-divisions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeDivisionController {

    @Inject
    EmployeeDivisionService employeeDivisionService;

    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllEmployeeDivisions() {
        return employeeDivisionService.getAllEmployeeDivisions();
    }

    @POST
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response createEmployeeDivision(EmployeeDivisionDtoPost employeeDivisionsDto) {
        return employeeDivisionService.createEmployeeDivision(employeeDivisionsDto);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response getEmployeeDivisionById(@PathParam("id") int id) {
        return employeeDivisionService.getEmployeeDivisionById(id);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response deleteEmployeeDivisionById(@PathParam("id") int id) {
        return employeeDivisionService.deleteEmployeeDivisionById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response updateEmployeeDivisionById(@PathParam("id") int id, EmployeeDivisionDtoPost employeeDivisionsDto) {
        return employeeDivisionService.updateEmployeeDivisionById(id, employeeDivisionsDto);
    }

    // @GET
    // @Path("/my-divisions/{auth-token}")
    // public Response getLoggedInUserEmployeeDivisions(@PathParam("auth-token")
    // String authToken) {
    // if (authToken != null && authToken.startsWith("Bearer ")) {
    // authToken = authToken.substring(7);
    // }
    // return
    // employeeDivisionService.getEmployeeDivisionsForLoggedInUser(authToken);
    // }

}
