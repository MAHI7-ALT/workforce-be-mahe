package org.imaginnovate.service.timesheet;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.timesheets.timesheet.EmployeeTimesheetsDto;
import org.imaginnovate.dto.timesheets.timesheet.TimesheetDto;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeDivision;
import org.imaginnovate.entity.common.EmployeeProject;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.entity.timesheet.ProjectTask;
import org.imaginnovate.entity.timesheet.Timesheet;
import org.imaginnovate.entity.timesheet.TimesheetStatus;
import org.imaginnovate.repository.common.DivisionRepo;
import org.imaginnovate.repository.common.EmployeeDivisionRepo;
import org.imaginnovate.repository.common.EmployeeProjectRepo;
import org.imaginnovate.repository.common.EmployeeRepo;
import org.imaginnovate.repository.common.ProjectRepo;
import org.imaginnovate.repository.common.UserRepo;
import org.imaginnovate.repository.timesheet.ProjectTaskRepo;
import org.imaginnovate.repository.timesheet.TimesheetRepo;
import org.imaginnovate.repository.timesheet.TimesheetStatusRepo;
import org.imaginnovate.service.common.EmployeeService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TimesheetService {
    @Inject
    TimesheetRepo timesheetRepo;

    @Inject
    ProjectRepo projectsRepo;

    @Inject
    DivisionRepo divisionRepo;

    @Inject
    EmployeeRepo employeesRepo;

    @Inject
    TimesheetStatusRepo timesheetStatusRepo;

    @Inject
    EmployeeProjectRepo employeeProjectRepo;

    @Inject
    EmployeeDivisionRepo employeeDivisionRepo;

    @Inject
    ProjectTaskRepo projectTaskRepo;

    @Inject
    EmployeeService employeeService;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllTimesheets() {
        try {
            List<TimesheetDto> timesheets = timesheetRepo.findAllTimesheets();
            if (timesheets == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No timesheets found").build();
            }
            return Response.status(Response.Status.OK).entity(timesheets).build();

        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    public Response getAllTimesheetsByStatus(Byte status) { 
        try {
            List<TimesheetDto> timesheets = timesheetRepo.findAllTimesheetsByStatus(status);
            if (timesheets == null || timesheets.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No timesheets found with status").build();
            }
            return Response.status(Response.Status.OK).entity(timesheets).build();

        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    @Transactional
    public Response deleteTimesheetById(Integer id) {
        try {
            Timesheet timesheet = timesheetRepo.findById(id);
            if (timesheet == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Timesheet with Id " + id + " not found")
                        .build();
            } else {
                timesheet.setDeletedBy(timesheet.getDeletedBy());
                timesheet.setDeletedAt(LocalDateTime.now());
                timesheetRepo.persist(timesheet);
            }
            return Response.status(Response.Status.OK).entity("Timesheet successfully deleted").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    public Response getTimesheetById(Integer id) {
        try {
            Timesheet timesheet = timesheetRepo.findById(id);
            if (timesheet == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Timesheet not found").build();
            }
            TimesheetDto timesheetDto = new TimesheetDto();
            Short hoursWorked=(short) (timesheet.getHoursWorked()/60);
            timesheetDto.setId(timesheet.getId());
            timesheetDto.setEmployeeProject(
                    timesheet.getEmployeeProject() != null ? timesheet.getEmployeeProject().getId() : null);
            timesheetDto.setProjectTask(timesheet.getProjectTask() != null ? timesheet.getProjectTask().getId() : null);
            timesheetDto.setDescription(timesheet.getDescription());
            timesheetDto.setHoursWorked(hoursWorked);
            timesheetDto.setSubmittedBy(timesheet.getSubmittedBy() != null ? timesheet.getSubmittedBy().getId() : null);
            timesheetDto.setSubmittedOn(timesheet.getSubmittedOn());
            timesheetDto.setStatus(timesheet.getStatus() != null ? timesheet.getStatus().getId() : null);
            timesheetDto.setApprovedBy(timesheet.getApprovedBy() != null ? timesheet.getApprovedBy().getId() : null);
            timesheetDto.setCreatedAt(timesheet.getCreatedAt());
            timesheetDto.setDeletedBy(timesheet.getDeletedBy() != null ? timesheet.getDeletedBy().getId() : null);
            timesheetDto.setDeletedAt(timesheet.getDeletedAt());
            timesheetDto.setModifiedAt(timesheet.getModifiedAt());

            return Response.ok(timesheetDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    public Response exportTimesheetsToCSV() {
        try {
            List<TimesheetDto> timesheets = timesheetRepo.findAllTimesheets();
            if (timesheets == null || timesheets.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("There are no timesheets").build();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(byteArrayOutputStream);
            writer.println(
                    "ID,Employee Project ID,Project Task ID,Description,Hours Worked,Submitted By,Submitted On,Status,Approved By,Created By,Created At,Modified By,Modified At,Deleted By,Deleted At");

            for (TimesheetDto ts : timesheets) {
                writer.println(
                        ts.getId() + "," +
                                ts.getEmployeeProject() + "," +
                                ts.getProjectTask() + "," +
                                ts.getDescription() + "," +
                                ts.getHoursWorked() + "," +
                                ts.getSubmittedBy() + "," +
                                ts.getSubmittedOn() + "," +
                                ts.getStatus() + "," +
                                (ts.getApprovedBy() != null ? ts.getApprovedBy() : "") + "," +
                                ts.getCreatedAt() + "," +
                                (ts.getModifiedAt() != null ? ts.getModifiedAt() : "") + "," +
                                (ts.getDeletedBy() != null ? ts.getDeletedBy() : "") + "," +
                                (ts.getDeletedAt() != null ? ts.getDeletedAt() : ""));
            }

            writer.flush();
            writer.close();

            return Response.ok(byteArrayOutputStream.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"timesheets.csv\"")
                    .build();

        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    @Transactional
    public Response exportUserTimesheetsToCSV() {
        try {
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User is not logged in").build();
            }
    
            // Use the updated method that filters by submittedBy
            List<TimesheetDto> timesheetDtos = timesheetRepo.findTimesheetsByEmployeeId(currentUserId);
            if (timesheetDtos == null || timesheetDtos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No timesheets found for this employee").build();
            }
    
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (PrintWriter writer = new PrintWriter(byteArrayOutputStream)) {
                writer.println("ID,Employee Project ID,Project Task ID,Description,Hours Worked,Submitted By,Submitted On,Status,Approved By,Created At,Modified At,Deleted By,Deleted At");
                for (TimesheetDto dto : timesheetDtos) {
                    writer.println(
                            dto.getId() + "," +
                                    dto.getEmployeeProject() + "," +
                                    dto.getProjectTask() + "," +
                                    (dto.getDescription() != null ? dto.getDescription() : "") + "," +
                                    (dto.getHoursWorked() != null ? dto.getHoursWorked() : "") + "," +
                                    (dto.getSubmittedBy() != null ? dto.getSubmittedBy() : "") + "," +
                                    (dto.getSubmittedOn() != null ? dto.getSubmittedOn() : "") + "," +
                                    (dto.getStatus() != null ? dto.getStatus() : "") + "," +
                                    (dto.getApprovedBy() != null ? dto.getApprovedBy() : "") + "," +
                                    dto.getCreatedAt() + "," +
                                    dto.getModifiedAt() + "," +
                                    (dto.getDeletedBy() != null ? dto.getDeletedBy() : "") + "," +
                                    (dto.getDeletedAt() != null ? dto.getDeletedAt() : ""));
                }
            }
            return Response.ok(byteArrayOutputStream.toByteArray())
                    .header("Content-Disposition", "attachment; filename=\"user_timesheets_" + currentUserId + ".csv\"")
                    .build();
    
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }
    
    
    @Transactional
    public Response postTimesheet(EmployeeTimesheetsDto timesheetDto) {
        try {
            EmployeeProject employeeProject = employeeProjectRepo.findById(timesheetDto.getEmployeeProject());
            if (employeeProject == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee Project not found for ID: " + timesheetDto.getEmployeeProject())
                        .build();
            }

            ProjectTask projectTask = projectTaskRepo.findById(timesheetDto.getProjectTask());
            if (projectTask == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Project Task not found for ID: " + timesheetDto.getProjectTask())
                        .build();
            }

            Timesheet timesheet = new Timesheet();
            timesheet.setEmployeeProject(employeeProject);
            timesheet.setProjectTask(projectTask);
            timesheet.setDescription(timesheetDto.getDescription());

            try {
                timesheet.setHoursWorked((short) (timesheetDto.getHoursWorked()/60));
            } catch (NumberFormatException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid format for hours worked. It should be a valid number.")
                        .build();
            }

            Integer currentUserId = authService.getCurrentUserId();
            Employee submittedBy = employeesRepo.findById(currentUserId);
            if (submittedBy == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Current user not found.")
                        .build();
            }
            timesheet.setSubmittedBy(submittedBy);
            timesheet.setSubmittedOn(LocalDateTime.now());

            TimesheetStatus status = timesheetStatusRepo.findById(1);
            if (status == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Status 'Pending' not found.")
                        .build();
            }
            timesheet.setStatus(status);
            timesheet.setCreatedAt(LocalDateTime.now());
            timesheetRepo.persist(timesheet);

            return Response.status(Response.Status.CREATED).entity(timesheetDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }

    @Transactional
    public Response updateEmployeeTimesheet(Integer id, EmployeeTimesheetsDto timesheetDto) {
        try {
            Timesheet timesheet = timesheetRepo.findById(id);
            if (timesheet == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Timesheet not found with ID: ")
                        .build();
            }

            Integer currentUserId = authService.getCurrentUserId();
            if (timesheet.getSubmittedBy() == null) {
                Employee submittedBy = employeesRepo.findById(currentUserId);
                if (submittedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Current user not found.")
                            .build();
                }
                timesheet.setSubmittedBy(submittedBy);
            }

            if (timesheet.getSubmittedOn() == null) {
                timesheet.setSubmittedOn(LocalDateTime.now());
            }

            if (timesheet.getStatus() == null) {
                TimesheetStatus pendingStatus = timesheetStatusRepo.findById(1);
                if (pendingStatus == null) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Default 'Pending' status not found.")
                            .build();
                }
                timesheet.setStatus(pendingStatus);
            }

            if (timesheetDto.getDescription() != null) {
                timesheet.setDescription(timesheetDto.getDescription());
            }

            if (timesheetDto.getHoursWorked() != null) {
                timesheet.setHoursWorked(timesheetDto.getHoursWorked());
            }

            timesheetRepo.persist(timesheet);

            return Response.status(Response.Status.OK).entity(timesheetDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server Error.").build();
        }
    }

    @Transactional
    public Response approveTimesheet(Integer id, Byte statusId) {
        try {
            Timesheet timesheet = timesheetRepo.findById(id);
            if (timesheet == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Timesheet not found with ID: ")
                        .build();
            }

            Integer currentUserId = authService.getCurrentUserId();
            User currentUser = userRepo.findById(currentUserId);
            if (currentUser == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Current user not found.")
                        .build();
            }

            if (!"ADMIN".equals(currentUser.getRole())) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Only Admin users can approve timesheets.")
                        .build();
            }

            TimesheetStatus approvedStatus = timesheetStatusRepo.findById(statusId);
            if (approvedStatus == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Approved status not found.")
                        .build();
            }

            timesheet.setStatus(approvedStatus);
            timesheet.setApprovedBy(currentUser.getEmployee());
            timesheetRepo.persist(timesheet);

            return Response.status(Response.Status.OK)
                    .entity("Timesheet approved successfully.")
                    .build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }

    @Transactional
    public Response approveTimesheetsByApprover(Integer id, Byte statusId) {
        try {
            Timesheet timesheet = timesheetRepo.findById(id);
            if (timesheet == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Timesheet not found with ID: " + id)
                        .build();
            }

            Integer currentUserId = authService.getCurrentUserId();
            User currentUser = userRepo.findById(currentUserId);
            if (currentUser == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Current user not found.")
                        .build();
            }

            if (!currentUser.getEmployee().getCanApproveTimesheets()) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("You are not authorized to approve timesheets.")
                        .build();
            }

            Employee timesheetEmployee = timesheet.getEmployeeProject().getEmployeeDivision().getEmployee();
            if (timesheetEmployee == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Timesheet employee data is missing.")
                        .build();
            }

            TimesheetStatus approvedStatus = timesheetStatusRepo.findById(statusId);
            if (approvedStatus == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Approved status not found.")
                        .build();
            }

            boolean isValidDivisionApproval = validateDivisionApproval(currentUser.getEmployee(), timesheetEmployee);
            if (!isValidDivisionApproval) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("You do not have permission to approve this timesheet based on division.")
                        .build();
            }

            Employee approver = findValidApprover(timesheetEmployee);
            if (approver == null) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("No valid approver found in the hierarchy.")
                        .build();
            }

            timesheet.setStatus(approvedStatus);
            timesheet.setApprovedBy(approver);
            timesheetRepo.persist(timesheet);

            return Response.status(Response.Status.OK)
                    .entity("Timesheet approved successfully.")
                    .build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }

    private boolean validateDivisionApproval(Employee currentEmployee, Employee timesheetEmployee) {
        List<EmployeeDivision> timesheetEmployeeDivisions = employeeDivisionRepo
                .findByEmployeeId(timesheetEmployee.getId());
        List<EmployeeDivision> currentEmployeeDivisions = employeeDivisionRepo
                .findByEmployeeId(currentEmployee.getId());
        boolean isPrimaryDivisionManager = false;
        for (EmployeeDivision teDivision : timesheetEmployeeDivisions) {
            if (teDivision.getPrimaryDivision()) {
                if (teDivision.getEmployee().getReportsTo() != null
                        && teDivision.getEmployee().getReportsTo().equals(currentEmployee)) {
                    isPrimaryDivisionManager = true;
                    break;
                }
            }
        }
        if (isPrimaryDivisionManager) {
            return true;
        }
        for (EmployeeDivision teDivision : timesheetEmployeeDivisions) {
            for (EmployeeDivision ceDivision : currentEmployeeDivisions) {
                if (ceDivision.getDivision().equals(teDivision.getDivision())) {
                    if (teDivision.getEmployee().getReportsTo() != null
                            && teDivision.getEmployee().getReportsTo().equals(currentEmployee)) {
                        return true;
                    }
                }
            }
        }

        return currentEmployee.equals(timesheetEmployee.getReportsTo());
    }

    private Employee findValidApprover(Employee timesheetEmployee) {
        Employee approver = timesheetEmployee.getReportsTo();
        while (approver != null) {
            if (approver.getCanApproveTimesheets()) {
                return approver;
            }
            approver = approver.getReportsTo();
        }
        return null;
    }

}
