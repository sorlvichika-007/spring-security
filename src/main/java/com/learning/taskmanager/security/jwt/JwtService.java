package com.learning.taskmanager.security.jwt;

import com.learning.taskmanager.model.AppUser;
import com.learning.taskmanager.model.RoleStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.jwt.secretKey}")
    private String SECRET_KEY;
    @Value("${spring.jwt.accessExpiration}")
    private Long expirationMs;
    private final Claims claims;

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


    public Claims parseToken(String token){
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            return null;
        }
    }

    public Long getUserId(){
        return Long.valueOf(claims.getSubject());
    }

    public boolean isExpiration(String token){
        return claims.getExpiration().before(new Date());
    }

    public RoleStatus getRole(){
        return RoleStatus.valueOf(claims.get("role",String.class));
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
