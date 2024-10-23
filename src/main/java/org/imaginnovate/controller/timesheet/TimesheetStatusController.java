package org.imaginnovate.controller.timesheet;

import org.imaginnovate.entity.timesheet.TimesheetStatus;
import org.imaginnovate.service.timesheet.TimesheetStatusService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/timesheet-status")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimesheetStatusController {

    @Inject
    TimesheetStatusService timesheetStatusService;

    @GET
    @Transactional
    @RolesAllowed({"LEAD"})
    public Response getAllTimesheetStatuses() {
        return timesheetStatusService.getAllTimesheetStatus();
    }

    @POST
    @Transactional
    // @RolesAllowed("ADMIN")
    public Response createTimesheetStatus(TimesheetStatus timesheetStatusDto) {
        return timesheetStatusService.createTimesheetStatus(timesheetStatusDto);
    }

    @GET
    @Path("/{id}")
    // @RolesAllowed({"ADMIN"})
    public Response getTimesheetStatusById(@PathParam("id") Byte id) {
        return timesheetStatusService.getTimesheetStatusById(id);
    }

}
