package org.imaginnovate.service.common;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imaginnovate.auth.AuthService;
import org.imaginnovate.dto.common.user.UserDetailsDto;
import org.imaginnovate.dto.common.user.UserDto;
import org.imaginnovate.dto.common.user.UserDtoPost;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.EmployeeRepo;
import org.imaginnovate.repository.common.UserRepo;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepo usersRepo;

    @Inject
    private EmployeeRepo employeesRepo;

    @Inject
    private AuthService authService;

    private final Logger logger = LogManager.getLogger(getClass());

    public Response getAllUsers() {
        try {
            List<UserDto> users = usersRepo.findAllUsers();
            if (users.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No users found").build();
            }
            return Response.status(Response.Status.OK).entity(users).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server error").build();
        }
    }

    @Transactional
    public Response createUser(UserDtoPost userDto) {
        try {
            if (userDto.getId() != null && userDto.getId() > 0) {
                if (usersRepo.findById(userDto.getId()) != null) {
                    return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
                }
            }

            User user = new User();
            user.setName(userDto.getName());
            user.setRole(userDto.getRole());
            user.setIsAssigner(userDto.getIsAssigner());

            if (userDto.getEmployee() != null) {
                Employee employee = employeesRepo.findById(userDto.getEmployee());
                if (employee != null) {
                    user.setEmployee(employee);
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Employee with ID " + userDto.getEmployee() + " not found").build();
                }
            }

            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User createdBy = usersRepo.findById(currentUserId);
                if (createdBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("CreatedBy user not found").build();
                }
                user.setCreatedBy(createdBy);
            }

            String hashedPassword = BcryptUtil.bcryptHash(userDto.getPassword());
            user.setPassword(hashedPassword);
            user.setCreatedAt(LocalDateTime.now());

            usersRepo.persist(user);
            userDto.setId(user.getId());
            return Response.status(Response.Status.CREATED).entity(userDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    public Response getUserById(Integer id) {
        try {
            User user = usersRepo.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
            UserDtoPost dto = new UserDtoPost();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmployee(user.getEmployee() != null ? user.getEmployee().getId() : null);
            dto.setRole(user.getRole());
            dto.setIsAssigner(user.getIsAssigner());
            return Response.status(Response.Status.OK).entity(dto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error.").build();
        }
    }

    @Transactional
    public Response updateUserById(Integer id, UserDtoPost userDto) {
        try {
            User user = usersRepo.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }

            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }

            if (userDto.getRole() != null) {
                user.setRole(userDto.getRole());
            }

            if (userDto.getIsAssigner() != null) {
                user.setIsAssigner(userDto.getIsAssigner());
            }

            if (userDto.getEmployee() != null) {
                Employee employee = employeesRepo.findById(userDto.getEmployee());
                if (employee != null) {
                    user.setEmployee(employee);
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Employee with ID " + userDto.getEmployee() + " not found").build();
                }
            }

            if (userDto.getPassword() != null) {
                String hashedPassword = BcryptUtil.bcryptHash(userDto.getPassword());
                user.setPassword(hashedPassword);
            }

            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User modifiedBy = usersRepo.findById(currentUserId);
                if (modifiedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ModifiedBy user not found").build();
                }
                user.setModifiedBy(modifiedBy);
                user.setModifiedAt(LocalDateTime.now());
            }

            usersRepo.persist(user);
            UserDtoPost updatedDto = new UserDtoPost();
            updatedDto.setId(user.getId());
            updatedDto.setName(user.getName());
            updatedDto.setEmployee(user.getEmployee() != null ? user.getEmployee().getId() : null);
            updatedDto.setRole(user.getRole());
            updatedDto.setIsAssigner(user.getIsAssigner());

            return Response.status(Response.Status.OK).entity(updatedDto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    @Transactional
    public Response deleteUserById(Integer id) {
        try {
            User user = usersRepo.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }

            Integer currentUserId = authService.getCurrentUserId();
            if (currentUserId != null) {
                User deletedBy = usersRepo.findById(currentUserId);
                if (deletedBy == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("DeletedBy user not found").build();
                }
                user.setDeletedBy(deletedBy);
                user.setDeletedAt(LocalDateTime.now());
                usersRepo.persist(user);
                return Response.status(Response.Status.OK).entity("User deleted successfully").build();
            }

            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Cannot determine current user").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server error.").build();
        }
    }

    @Transactional
    public Response changePassword(int id, String password) {
        try {
            User user = usersRepo.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
            String hashedPassword = BcryptUtil.bcryptHash(password);
            user.setPassword(hashedPassword);
            usersRepo.persist(user);

            return Response.status(Response.Status.OK).entity("Password changed successfully").build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server error while changing password").build();
        }
    }

    public Response getUserDetailsById(Integer id) {
        try {
            User user = usersRepo.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
            UserDetailsDto dto = new UserDetailsDto();
            dto.setId(user.getId());
            dto.setUserName(user.getName());
            dto.setEmployeeName(user.getEmployee().getFirstName());
            dto.setRole(user.getRole());
            return Response.status(Response.Status.OK).entity(dto).build();
        } catch (Exception e) {
            logger.error("Internal Server Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error").build();
        }
    }

}
