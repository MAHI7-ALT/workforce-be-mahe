package org.imaginnovate.controller.common;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.imaginnovate.dto.common.division.DivisionDtoPost;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.service.common.DivisionService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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

@Path("/divisions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DivisionController {

    @Inject
    DivisionService divisionService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UserRepo userRepo;

    
    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllDivisions() {
            return divisionService.getAllDivisions();
    }

    @POST
    @RolesAllowed({"ADMIN"})
    public Response createDivision( DivisionDtoPost divisionsDto) {
        return divisionService.createDivision(divisionsDto);
    }

    @PUT
    @RolesAllowed({"ADMIN"})
    @Path("{divisionId}")
    public Response updateDivision( @PathParam("divisionId") Integer id, DivisionDtoPost divisionDto) {
        return divisionService.updateDivisionById(id, divisionDto);
    }

    @DELETE
    @RolesAllowed({"ADMIN"})
    @Path("{divisionId}")
    public Response deleteDivision(@PathParam("divisionId") Integer id) {
        return divisionService.deleteDivision(id);
    }

    @GET
    @RolesAllowed({"ADMIN"})
    @Path("{divisionId}")
    public Response getDivisionById(@PathParam("divisionId") Integer id) {
        return divisionService.getDivisionById(id);
    }
}
