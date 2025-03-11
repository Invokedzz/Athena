package com.book.store.athena.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.book.store.athena.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenAuthService {

    @Value("spring.security.oauth2.client.registration")
    private String secret;

    public String generateJWToken (User user) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("athena_library")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expireTokenDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception){

            throw new TokenGenerationException(exception.getMessage());

        }

    }

    private Instant expireTokenDate () {

        return Instant.now().plusSeconds(7200);

    }

}
