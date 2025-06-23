package com.example.demo.jwtConfig;

import java.sql.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {
	

    private static final String SECRET_KEY = "my-super-secret-key";
    private static final long EXPIRATION_TIME = 86400000; // 1 day
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = validateTokenAndRetrieveSubject(token);
        return (username.equals(userDetails.getUsername()));
    }


    public String generateToken(String username, String role) {
        return JWT.create()
            .withSubject(username)
            .withClaim("role", role)
            .withIssuedAt(new java.util.Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    public String validateTokenAndRetrieveSubject(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
            .build()
            .verify(token)
            .getSubject();
    }
	
	

}
