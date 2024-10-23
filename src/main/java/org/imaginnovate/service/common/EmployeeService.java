package org.imaginnovate.service.common;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.common.employee.EmployeeDto;
import org.imaginnovate.dto.common.employee.EmployeeDtoPost;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.EmployeeRepo;
import org.imaginnovate.repository.common.UserRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class EmployeeService {

    @Inject
    private EmployeeRepo employeeRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllEmployees() {
        try {
            List<EmployeeDto> employees = employeeRepo.findAllEmployees();
            if (employees == null || employees.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No employees found.").build();
            }
            return Response.ok(employees).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    @Transactional
    public Response createEmployee(EmployeeDtoPost employeeDto) {
        try {
            Employee existingEmployee = employeeRepo.findById(employeeDto.getId());
            if (existingEmployee != null) {
                return Response.status(Response.Status.CONFLICT).entity("Employee already exists.").build();
            }
    
            if (!isValidEmailDomain(employeeDto.getEmail())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Email must be @imaginnovate.com or @fleetenable.com or @logisticsstudios.com").build();
            }
    
            Employee reportsTo = null;
            if (employeeDto.getReportsTo() != null) {
                reportsTo = employeeRepo.findEmployeeById(employeeDto.getReportsTo());
                if (reportsTo == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("ReportsTo employee not found.").build();
                }
            }
    
            User createdBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                createdBy = userRepo.findUserById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("CreatedBy user not found.").build();
                }
            }
            Employee employee = new Employee();
            employee.setId(employeeDto.getId());
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            employee.setCanApproveTimesheets(employeeDto.getCanApproveTimesheets());
            employee.setDesignation(employeeDto.getDesignation());
            employee.setEmail(employeeDto.getEmail());
            employee.setGender(employeeDto.getGender());
            employee.setEndDate(employeeDto.getEndDate());
            employee.setStartDate(employeeDto.getStartDate());
            employee.setReportsTo(reportsTo);
            employee.setCreatedBy(createdBy);
            employee.setCreatedAt(LocalDateTime.now());
    
            employeeRepo.persist(employee);
            employeeDto.setId(employee.getId());
    
            return Response.status(Response.Status.CREATED).entity(employeeDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }
    
    private boolean isValidEmailDomain(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }
    
        String domain = email.substring(email.indexOf("@") + 1);
        return domain.equals("imaginnovate.com") || 
               domain.equals("fleetenable.com") || 
               domain.equals("logisticsstudio.com");
    }
    

    @Transactional
    public Response updateEmployeeById(int id, EmployeeDtoPost employeeDto) {
        try {
            Employee existingEmployee = employeeRepo.findById(id);
            if (existingEmployee == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee not found.")
                        .build();
            }

            if (employeeDto.getFirstName() != null) {
                existingEmployee.setFirstName(employeeDto.getFirstName());
            }

            if (employeeDto.getLastName() != null) {
                existingEmployee.setLastName(employeeDto.getLastName());
            }

            if (employeeDto.getReportsTo() != null) {
                Employee reportsTo = employeeRepo.findEmployeeById(employeeDto.getReportsTo());
                if (reportsTo == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ReportsTo employee not found.")
                            .build();
                }
                existingEmployee.setReportsTo(reportsTo);
            }

            if (employeeDto.getEmail() != null) {
                existingEmployee.setEmail(employeeDto.getEmail());
            }

            if (employeeDto.getDesignation() != null) {
                existingEmployee.setDesignation(employeeDto.getDesignation());
            }

            if (employeeDto.getGender() != null) {
                existingEmployee.setGender(employeeDto.getGender());
            }

            if (employeeDto.getStartDate() != null) {
                existingEmployee.setStartDate(employeeDto.getStartDate());
            }
            if (employeeDto.getEndDate() != null) {
                existingEmployee.setEndDate(employeeDto.getEndDate());
            }

            if (employeeDto.getCanApproveTimesheets() != null) {
                existingEmployee.setCanApproveTimesheets(employeeDto.getCanApproveTimesheets());
            }

            User modifiedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                modifiedBy = userRepo.findUserById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ModifiedBy user not found.")
                            .build();
                }
                existingEmployee.setModifiedBy(modifiedBy);
                existingEmployee.setModifiedAt(LocalDateTime.now());
            }
            employeeRepo.persist(existingEmployee);
            employeeDto.setId(existingEmployee.getId());
            return Response.ok(employeeDto).build();

        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error.")
                    .build();
        }
    }

    @Transactional
    public Response deleteEmployeeById(int id) {
        try {
            Employee employee = employeeRepo.findById(id);
            if (employee == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Employee not found.").build();
            }

            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User deletedBy = userRepo.findUserById(currentUserId);
                employee.setDeletedBy(deletedBy);
            }

            employee.setDeletedAt(LocalDateTime.now());
            employeeRepo.persist(employee);

            return Response.status(Response.Status.OK).entity("Employee successfully deleted.").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    public Response getEmployeeById(int id) {
        try {
            Employee employee = employeeRepo.findById(id);
            if (employee == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Employee not found.").build();
            }
            return Response.ok(convertToDto(employee)).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error.").build();
        }
    }

    private EmployeeDtoPost convertToDto(Employee employee) {
        EmployeeDtoPost employeeDto = new EmployeeDtoPost();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDesignation(employee.getDesignation());
        employeeDto.setGender(employee.getGender());
        employeeDto.setStartDate(employee.getStartDate());
        employeeDto.setEndDate(employee.getEndDate());
        employeeDto.setReportsTo(employee.getReportsTo() != null ? employee.getReportsTo().getId() : null);
        employeeDto.setCanApproveTimesheets(employee.getCanApproveTimesheets());
        return employeeDto;
    }
}
