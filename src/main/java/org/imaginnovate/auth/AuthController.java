package org.imaginnovate.auth;

import org.imaginnovate.dto.LoginRequstBody;
import org.imaginnovate.repository.common.UserRepo;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @Inject
    UserRepo userRepository;

    @POST
    @Path("/login")
    public Response login(LoginRequstBody requestBody) {
        try {
            String token = authService.login(requestBody.getUserName(), requestBody.getPassword());
            return Response.ok(token).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(String userName) {
        try {
            authService.logout(userName);
            return Response.ok("User logged out successfully").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
