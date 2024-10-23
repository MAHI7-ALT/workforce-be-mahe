package org.imaginnovate.jwt;

import java.security.PublicKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.imaginnovate.entity.common.User;
import org.imaginnovate.repository.common.UserRepo;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.util.KeyUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JwtGenerator {

    @Inject
    UserRepo userRepo;

    @Inject
    JWTParser jwtParser;

    @ConfigProperty(name = "mp.jwt.sign.key-location")
    String privateKeyLocation;

    @ConfigProperty(name = "mp.jwt.verify.publickey.location")
    String publicKeyLocation;

    public String generateJwt(String username) {
        User user = userRepo.findByUserName(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Set<String> roles = new HashSet<>(Collections.singleton(user.getRole()));

        return Jwt.issuer("http://localhost:8080")
                .subject(user.getName())
                .groups(roles)
                .claim("userId", user.getId())
                .issuedAt(System.currentTimeMillis())
                .expiresAt(System.currentTimeMillis() + 3600000) // 1 hour expiry
                .sign();
    }

    public Integer getCurrentUserId(String jwtToken) {
        try {
            PublicKey publicKey = KeyUtils.readPublicKey(publicKeyLocation);
            var jwt = jwtParser.verify(jwtToken, publicKey);
            return jwt.getClaim("userId");
            
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse or verify JWT token", e);
        } catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }

}
