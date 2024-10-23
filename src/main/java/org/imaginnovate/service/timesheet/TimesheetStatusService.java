package org.imaginnovate.service.timesheet;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.entity.timesheet.TimesheetStatus;
import org.imaginnovate.repository.timesheet.TimesheetStatusRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TimesheetStatusService {

    @Inject
    private TimesheetStatusRepo timesheetStatusRepo;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllTimesheetStatus() {
        try {
            List<TimesheetStatus> timesheetStatuse = timesheetStatusRepo.findAllTimesheetStatuses();
            if (timesheetStatuse.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no timesheetStatuses ").build();
            }
            return Response.status(Response.Status.OK).entity(timesheetStatuse).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response createTimesheetStatus(TimesheetStatus timesheetStatusDto) {
        try {
            TimesheetStatus existingTimesheetStatus = timesheetStatusRepo.findById(timesheetStatusDto.getId());
            if (existingTimesheetStatus != null) {
                return Response.status(Response.Status.CONFLICT).entity("The timesheetStatus already existed").build();
            }
            TimesheetStatus timesheetStatus = new TimesheetStatus();
            timesheetStatus.setId(timesheetStatusDto.getId());
            timesheetStatus.setName(timesheetStatusDto.getName());

            timesheetStatusRepo.persist(timesheetStatus);
            timesheetStatusDto.setId(timesheetStatus.getId());
            return Response.status(Response.Status.OK).entity(timesheetStatusDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

    public Response getTimesheetStatusById(Byte id) {
        try {
            TimesheetStatus timesheetStatus = timesheetStatusRepo.findById(id);
            if (timesheetStatus == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("There is no timesheetStatus found with Id " + "getId()").build();
            }
            return Response.status(Response.Status.OK).entity(timesheetStatus).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error").build();
        }
    }

}