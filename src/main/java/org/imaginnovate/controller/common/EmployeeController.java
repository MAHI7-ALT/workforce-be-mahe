package org.imaginnovate.controller.common;

import org.imaginnovate.dto.common.employee.EmployeeDtoPost;
import org.imaginnovate.service.common.EmployeeService;

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

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @GET
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @POST
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response createEmployee(EmployeeDtoPost employeeDto) {
        return employeeService.createEmployee(employeeDto);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response getEmployeeById(@PathParam("id") int id) {
        return employeeService.getEmployeeById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response updateEmployeeById(@PathParam("id") int id, EmployeeDtoPost employeeDto) {
        return employeeService.updateEmployeeById(id, employeeDto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response deleteEmployeeById(@PathParam("id") int id) {
        return employeeService.deleteEmployeeById(id);
    }
}
