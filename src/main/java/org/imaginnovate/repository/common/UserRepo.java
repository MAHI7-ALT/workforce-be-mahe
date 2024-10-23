package org.imaginnovate.repository.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.imaginnovate.dto.LoginRequstBody;
import org.imaginnovate.dto.common.user.UserDto;
import org.imaginnovate.entity.common.User;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class UserRepo implements PanacheRepositoryBase<User, Integer> {

    @SuppressWarnings("unchecked")
  public List<UserDto> findAllUsers() {
    String sql = "SELECT u.id, u.name AS userName, CONCAT(e.first_name, ' ', e.last_name) AS employeeName, u.role, u.is_assigner AS isAssigner, " +
                 "u.created_by AS createdBy, u.created_at AS createdAt, u.modified_by AS modifiedBy, u.modified_at AS modifiedAt, " +
                 "u.deleted_by AS deletedBy, u.deleted_at AS deletedAt " +
                 "FROM users u " +
                 "JOIN employees e ON u.employee_id = e.id";

    List<Object[]> results = getEntityManager().createNativeQuery(sql).getResultList();

    List<UserDto> users = new ArrayList<>();
    for (Object[] row : results) {
        Integer id = (Integer) row[0];
        String userName = (String) row[1];
        String employeeName = (String) row[2];
        String role = (String) row[3];
        Boolean isAssigner = (Boolean) row[4];
        Integer createdBy = (Integer) row[5];
        LocalDateTime createdAt = ((Timestamp) row[6]).toLocalDateTime();
        Integer modifiedBy = (Integer) row[7];
        LocalDateTime modifiedAt = row[8] != null ? ((Timestamp) row[8]).toLocalDateTime() : null;
        Integer deletedBy = (Integer) row[9];
        LocalDateTime deletedAt = row[10] != null ? ((Timestamp) row[10]).toLocalDateTime() : null;

        UserDto userDto = new UserDto(id, userName, employeeName, role, isAssigner, createdBy, createdAt, modifiedBy, modifiedAt, deletedBy, deletedAt);
        users.add(userDto);
    }

    return users;
}

    public User findById(Integer id) {
        return getEntityManager().find(User.class, id);
    }

    public Optional<User> findByUserName(String userName) {
        try {
            return Optional.ofNullable(getEntityManager().createQuery("SELECT u FROM User u WHERE u.name = :userName", User.class)
                    .setParameter("userName", userName)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void persist(LoginRequstBody userResponse) {
        User user = new User();
        user.setName(userResponse.getUserName());
        user.setPassword(userResponse.getPassword());
        getEntityManager().persist(user);
    }

    public void persist(User user) {
        getEntityManager().persist(user);
    }

    public User findByUserId(Integer id) {
        return getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
    }

    public boolean canAssignDocuments(Integer createdBy) {
       return getEntityManager().createQuery("SELECT u.isAssigner FROM User u WHERE u.id = :createdBy AND u.isAssigner = true", Boolean.class).setParameter("createdBy", createdBy).getResultList().size() > 0;
    }

    public User findById(User createdBy) {
        return getEntityManager().find(User.class, createdBy.getId());
    }

    public boolean canAssign(Object object) {
        return getEntityManager().createQuery("SELECT u.isAssigner FROM User u WHERE u.id = :createdBy AND u.isAssigner = true", Boolean.class).setParameter("createdBy", object).getSingleResult();
    }

    public User findUserById(User user) {
        return getEntityManager().createQuery("SELECT u FROM User u WHERE u.id = :createdBy", User.class).setParameter("createdBy", user).getSingleResult();
    }



    public User findUserById(Integer createdBy) {
        return getEntityManager().createQuery("SELECT u FROM User u WHERE u.id = :createdBy", User.class).setParameter("createdBy", createdBy).getSingleResult();
    }



    public User findByToken(String jwtToken) {
        return getEntityManager().createQuery("SELECT u FROM User u WHERE u.jwtToken = :jwtToken", User.class).setParameter("jwtToken", jwtToken).getSingleResult();
    }

    public boolean isAuthorizedToCreate(Integer createdBy) {
    try {
        return getEntityManager().createQuery(
            "SELECT u.isAssigner FROM User u WHERE u.id = :createdBy AND u.isAssigner = true", 
            Boolean.class
        ).setParameter("createdBy", createdBy).getSingleResult();
    } catch (NoResultException e) {
        return false; 
    }
}

    
   

  
 
}
