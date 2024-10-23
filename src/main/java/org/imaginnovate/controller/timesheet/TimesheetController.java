package org.imaginnovate.controller.timesheet;

import org.imaginnovate.dto.timesheets.timesheet.EmployeeTimesheetsDto;
import org.imaginnovate.repository.timesheet.TimesheetRepo;
import org.imaginnovate.service.timesheet.TimesheetService;

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

@Path("/timesheets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TimesheetController {

    @Inject
    TimesheetService timesheetService;

    @Inject
    TimesheetRepo timesheetRepo;

    @GET
    @RolesAllowed({ "ADMIN" })
    public Response getAllTimesheets() {
        return timesheetService.getAllTimesheets();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "USER" })
    public Response getTimesheetById(@PathParam("id") Integer id) {
        return timesheetService.getTimesheetById(id);
    }

    @GET
    @Path("/status/{status}")
    @RolesAllowed({ "ADMIN", "USER" })
    public Response getTimesheetByStatus(@PathParam("status") Byte status) {
        return timesheetService.getAllTimesheetsByStatus(status);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response deleteTimesheetById(@PathParam("id") int id) {
        return timesheetService.deleteTimesheetById(id);
    }

    @GET
    @Path("/export/csv")
    @RolesAllowed({ "ADMIN" })
    public Response exportTimesheetsToCsv() {
        return timesheetService.exportTimesheetsToCSV();
    }

    @GET
    @Path("/user/export/csv")
    @RolesAllowed({ "ADMIN", "USER" })
    public Response exportUserTimesheetsToCsv() {
        return timesheetService.exportUserTimesheetsToCSV();
    }

    @POST
    @RolesAllowed({ "ADMIN", "USER" })
    public Response postTimesheet(EmployeeTimesheetsDto timesheetDto) {
        return timesheetService.postTimesheet(timesheetDto);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "USER" })
    public Response updateEmployeeTimesheet(@PathParam("id") Integer id, EmployeeTimesheetsDto timesheetDto) {
        return timesheetService.updateEmployeeTimesheet(id, timesheetDto);
    }

    @POST
    @Path("/{id}/{statusId}/approve")
    @RolesAllowed({ "ADMIN" })
    public Response approveTimesheet(@PathParam("id") Integer id, @PathParam("statusId") Byte statusId) {
        return timesheetService.approveTimesheet(id, statusId);
    }

    @POST
    @Path("/{id}/{statusId}/approve-by")
    @RolesAllowed({ "ADMIN" })
    public Response approveTimesheetByApprover(@PathParam("id") Integer id, @PathParam("statusId") Byte statusId) {
        return timesheetService.approveTimesheetsByApprover(id, statusId);
    }

    // @GET
    // @Path("/filter")
    // public Response getAllTimesheetsByFilters(
    // @QueryParam("status") Byte status,
    // @QueryParam("userId") Integer userId,
    // @QueryParam("projectId") Integer projectId,
    // @QueryParam("page") @DefaultValue("0") int page,
    // @QueryParam("size") @DefaultValue("10") int size,
    // @QueryParam("sort") @DefaultValue("submittedOn,desc") String sort) {

    // // Validate page and size
    // if (page < 0) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity("Page index cannot be negative").build();
    // }
    // if (size <= 0) {
    // return Response.status(Response.Status.BAD_REQUEST)
    // .entity("Page size must be greater than zero").build();
    // }

    // try {
    // List<TimesheetDto> timesheets = timesheetRepo.findTimesheetsByFilters(status,
    // userId, projectId, page, size, sort);

    // if (timesheets == null || timesheets.isEmpty()) {
    // return Response.status(Response.Status.NOT_FOUND).entity("No timesheets found
    // with the given filters").build();
    // }

    // return Response.status(Response.Status.OK).entity(timesheets).build();
    // } catch (Exception e) {
    // return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    // .entity("Internal Server error while fetching timesheets").build();
    // }
    // }

}
