package org.imaginnovate.controller.common;

import org.imaginnovate.dto.common.user.UserDtoPost;
import org.imaginnovate.service.common.UserService;

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


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService usersService;

    @GET
    @RolesAllowed({"ADMIN"})
    public Response getAllUsers() {
        return usersService.getAllUsers();
    }

    @POST
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response createUser(UserDtoPost usersDto) {
        return usersService.createUser(usersDto);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN","USER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Integer id) {
        return  usersService.getUserById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response updateUserById(@PathParam("id") Integer id, UserDtoPost usersDto) {
        return usersService.updateUserById(id, usersDto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response deleteUserById(@PathParam("id") Integer id) {
        return usersService.deleteUserById(id);
    }

    @POST
    @Path("/{id}/changePassword")
    @Transactional
    @RolesAllowed({"ADMIN","USER"})
    public  Response changePassword(@PathParam ("id") Integer id,String password) {
        return usersService.changePassword(id,password);
    }

    @GET
    @Path("/get-user-details/{id}")
    @RolesAllowed({"USER"})
    public Response getUserDetails(@PathParam ("id") Integer id){
        return usersService.getUserDetailsById(id);
    }
}
