package org.imaginnovate.service.common;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.common.division.DivisionDto;
import org.imaginnovate.dto.common.division.DivisionDtoPost;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.DivisionRepo;
import org.imaginnovate.repository.common.UserRepo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class DivisionService {

    @Inject
    private DivisionRepo divisionsRepo;

    @Inject
    private UserRepo usersRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllDivisions() {

        try {
            List<DivisionDto> divisions = divisionsRepo.findAllDivisions();
            if (divisions.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No divisions found").build();
            }
            return Response.ok(divisions).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error while fetching divisions")
                    .build();
        }
    }

    @Transactional
    public Response createDivision(DivisionDtoPost divisionsDto) {

        try {
            if (divisionsDto.getName() != null) {
                Division existingDivision = divisionsRepo.findByName(divisionsDto.getName());
                if (existingDivision != null) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("The division already exists")
                            .build();
                }
            }

            User createdBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                createdBy = usersRepo.findById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("CreatedBy user not found")
                            .build();
                }

            }

            Division division = new Division();
            division.setName(divisionsDto.getName());
            division.setCreatedBy(createdBy);
            division.setCreatedAt(LocalDateTime.now());

            if (divisionsDto.getParent() == null) {
                division.setParent(null);
            } else {
                Division parentDivision = divisionsRepo.findById(divisionsDto.getParent());
                if (parentDivision != null) {
                    division.setParent(parentDivision);
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Parent division not found")
                            .build();
                }
            }

            divisionsRepo.persist(division);
            divisionsDto.setId(division.getId());

            return Response.status(Response.Status.CREATED)
                    .entity(divisionsDto)
                    .build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error.")
                    .build();
        }
    }

    public Response getDivisionById(int id) {

        try {
            Division division = divisionsRepo.findById(id);
            if (division == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Division not found")
                        .build();
            }
            return Response.ok(convertToDto(division)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }

    @Transactional
    public Response updateDivisionById(Integer id, DivisionDtoPost divisionDto) {
        try {
            Division division = divisionsRepo.findById(id);
            if (division == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Division not found")
                        .build();
            }

            User modifiedBy = null;
            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                modifiedBy = usersRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ModifiedBy user not found")
                            .build();
                }
            }

            if (divisionDto.getName() != null) {
                division.setName(divisionDto.getName());
            }

            if (divisionDto.getParent() != null && divisionDto.getParent() != 0) {
                Division parentDivision = divisionsRepo.findById(divisionDto.getParent());
                if (parentDivision != null) {
                    division.setParent(parentDivision);
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Parent division not found")
                            .build();
                }
            }

            division.setModifiedBy(modifiedBy);
            division.setModifiedAt(LocalDateTime.now());
            divisionsRepo.persist(division);
            DivisionDto updatedDto = convertToDto(division);

            return Response.ok(updatedDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error.")
                    .build();
        }
    }

    @Transactional
    public Response deleteDivision(Integer id) {

        try {
            Division division = divisionsRepo.findById(id);
            if (division == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Division not found")
                        .build();
            }

            User deletedBy = null;
            Integer currentUserId = authService.getCurrentUserId();

            if (currentUserId != null) {
                deletedBy = usersRepo.findById(currentUserId);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("DeletedBy user not found")
                            .build();
                }
            }

            division.setDeletedBy(deletedBy);
            division.setDeletedAt(LocalDateTime.now());

            divisionsRepo.persist(division);

            return Response.status(Response.Status.OK)
                    .entity("The division was deleted successfully")
                    .build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error.")
                    .build();
        }
    }

    private DivisionDto convertToDto(Division division) {
        DivisionDto dto = new DivisionDto();
        dto.setId(division.getId());
        dto.setName(division.getName());
        dto.setParentDivision(division.getParent() != null ? division.getParent().getName() : null);
        dto.setCreatedBy(division.getCreatedBy() != null ? division.getCreatedBy().getId() : null);
        dto.setCreatedAt(division.getCreatedAt());
        dto.setModifiedBy(division.getModifiedBy() != null ? division.getModifiedBy().getId() : null);
        dto.setModifiedAt(division.getModifiedAt());
        dto.setDeletedBy(division.getDeletedBy() != null ? division.getDeletedBy().getId() : null);
        dto.setDeletedAt(division.getDeletedAt());
        return dto;
    }
}
