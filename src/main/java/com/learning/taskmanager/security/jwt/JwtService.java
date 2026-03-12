package com.learning.taskmanager.security.jwt;

import com.learning.taskmanager.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secretKey}")
    private String SECRET_KEY;
    @Value("${spring.jwt.accessExpiration}")
    private Long expirationMs;

    public String generateToken(AppUser user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username",user.getUsername())
                .claim("email",user.getEmail())
                .claim("role",user.getRoles().stream().map(role -> role.getName().name()).toList())
                .issuedAt(new Date())
                .signWith(getKey())
                .expiration(new Date(System.currentTimeMillis() + 1000 * expirationMs))
                .compact();
    }


    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
