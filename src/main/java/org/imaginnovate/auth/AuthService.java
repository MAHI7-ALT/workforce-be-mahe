package org.imaginnovate.auth;

import java.time.LocalDateTime;
import java.util.Optional;

import org.imaginnovate.entity.common.CurrentUser;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.jwt.JwtGenerator;
import org.imaginnovate.repository.common.UserRepo;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepo userRepository;

    @Inject
    JwtGenerator jwtGenerator;

    @Inject
    CurrentUser currentUser;

    @Transactional
    public String login(String userName, String password) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (user.getDeletedBy() != null || user.getDeletedAt() != null) {
            throw new RuntimeException("User does not have access to login");
        }

        if (!BcryptUtil.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtGenerator.generateJwt(user.getName());
        user.setResetTokenExpiresAt(LocalDateTime.now().plusHours(1));
        userRepository.persist(user);
        currentUser.setUserId(user.getId());

        return token;
    }

    @Transactional
    public void logout(String userName) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        userRepository.persist(user);
        currentUser.setUserId(null);
        System.out.println("User " + userName + " has been successfully logged out.");
    }

    public Integer getCurrentUserId() {
        return currentUser.getUserId();
    }
}
