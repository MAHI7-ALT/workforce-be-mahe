package org.imaginnovate.service.common;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.common.employeedivision.EmployeeDivisionDto;
import org.imaginnovate.dto.common.employeedivision.EmployeeDivisionDtoPost;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeDivision;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.DivisionRepo;
import org.imaginnovate.repository.common.EmployeeDivisionRepo;
import org.imaginnovate.repository.common.EmployeeRepo;
import org.imaginnovate.repository.common.UserRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class EmployeeDivisionService {

    @Inject
    private EmployeeDivisionRepo employeeDivisionsRepo;

    @Inject
    private DivisionRepo divisionsRepo;

    @Inject
    private UserRepo usersRepo;

    @Inject
    private EmployeeRepo employeesRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    @Transactional
    public Response getAllEmployeeDivisions() {
        try {
            List<EmployeeDivisionDto> employeeDivisionsDto = employeeDivisionsRepo.findAllEmployeeDivisions();
            if (employeeDivisionsDto.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No employee divisions found").build();
            }
            return Response.ok(employeeDivisionsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Transactional
    public Response createEmployeeDivision(EmployeeDivisionDtoPost employeeDivisionsDto) {
        try {
            if (employeeDivisionsDto.getEmployee() == 0 || employeeDivisionsDto.getDivision() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Employee and Division must be provided")
                        .build();
            }

            Division division = divisionsRepo.findById(employeeDivisionsDto.getDivision());
            if (division == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Division not found").build();
            }

            Employee employee = employeesRepo.findById(employeeDivisionsDto.getEmployee());
            if (employee == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
            }

            boolean exists = employeeDivisionsRepo.existsByDivisionIdAndEmployeeId(
                    employeeDivisionsDto.getDivision(),
                    employeeDivisionsDto.getEmployee());
            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Employee Division already exists")
                        .build();
            }

            User createdBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                createdBy = usersRepo.findByIdOptional(currentUserId).orElse(null);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("CreatedBy user not found").build();
                }
            }

            EmployeeDivision employeeDivision = new EmployeeDivision();
            employeeDivision.setEmployee(employee);
            employeeDivision.setDivision(division);
            employeeDivision.setPrimaryDivision(employeeDivisionsDto.getPrimaryDivision());
            employeeDivision.setCreatedBy(createdBy);
            employeeDivision.setCreatedAt(LocalDateTime.now());

            employeeDivisionsRepo.persist(employeeDivision);
            employeeDivisionsDto.setId(employeeDivision.getId());

            return Response.status(Response.Status.CREATED).entity(employeeDivisionsDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    public Response getEmployeeDivisionById(int id) {
        try {
            EmployeeDivision ed = employeeDivisionsRepo.findById(id);
            if (ed == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Employee Division not found").build();
            }

            EmployeeDivisionDto employeeDivisionDto = convertToDto(ed);
            return Response.ok(employeeDivisionDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Transactional
    public Response updateEmployeeDivisionById(Integer id, EmployeeDivisionDtoPost employeeDivisionsDto) {
        try {
            EmployeeDivision employeeDivision = employeeDivisionsRepo.findById(id);
            if (employeeDivision == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee Division not found")
                        .build();
            }

            if (employeeDivisionsDto.getPrimaryDivision() != null) {
                employeeDivision.setPrimaryDivision(employeeDivisionsDto.getPrimaryDivision());
            }

            if (employeeDivisionsDto.getEmployee() != null && employeeDivisionsDto.getEmployee() != 0) {
                Employee employee = employeesRepo.findById(employeeDivisionsDto.getEmployee());
                if (employee != null) {
                    employeeDivision.setEmployee(employee);
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Employee not found")
                            .build();
                }
            }

            if (employeeDivisionsDto.getDivision() != null && employeeDivisionsDto.getDivision() != 0) {
                Division division = divisionsRepo.findById(employeeDivisionsDto.getDivision());
                if (division != null) {
                    employeeDivision.setDivision(division);
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Division not found")
                            .build();
                }
            }

            User modifiedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                modifiedBy = usersRepo.findByIdOptional(currentUserId).orElse(null);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ModifiedBy user not found")
                            .build();
                }
                employeeDivision.setModifiedBy(modifiedBy);
            }

            employeeDivision.setModifiedAt(LocalDateTime.now());
            employeeDivisionsRepo.persist(employeeDivision);
            EmployeeDivisionDto updatedDto = convertToDto(employeeDivision);
            return Response.ok(updatedDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }

    @Transactional
    public Response deleteEmployeeDivisionById(Integer id) {
        try {
            EmployeeDivision ed = employeeDivisionsRepo.findById(id);
            if (ed == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("EmployeeDivision not found").build();
            }

            User deletedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                deletedBy = usersRepo.findByIdOptional(currentUserId).orElse(null);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("DeletedBy user not found").build();
                }
                ed.setDeletedBy(deletedBy);
            }

            ed.setDeletedAt(LocalDateTime.now());
            employeeDivisionsRepo.persist(ed);

            return Response.ok("EmployeeDivision deleted successfully").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }

    private EmployeeDivisionDto convertToDto(EmployeeDivision ed) {
        EmployeeDivisionDto dto = new EmployeeDivisionDto();
        dto.setId(ed.getId());
        dto.setEmployee(ed.getEmployee() != null ? ed.getEmployee().getId() : null);
        dto.setDivision(ed.getDivision() != null ? ed.getDivision().getId() : null);
        dto.setPrimaryDivision(ed.getPrimaryDivision());
        dto.setCreatedBy(ed.getCreatedBy() != null ? ed.getCreatedBy().getId() : null);
        dto.setCreatedAt(ed.getCreatedAt());
        dto.setModifiedBy(ed.getModifiedBy() != null ? ed.getModifiedBy().getId() : null);
        dto.setModifiedAt(ed.getModifiedAt());
        dto.setDeletedBy(ed.getDeletedBy() != null ? ed.getDeletedBy().getId() : null);
        dto.setDeletedAt(ed.getDeletedAt());
        return dto;
    }
}
