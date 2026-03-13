package com.learning.taskmanager.security.jwt;

import com.learning.taskmanager.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.jwt.secretKey}")
    private String SECRET_KEY;
    @Value("${spring.jwt.accessExpiration}")
    private Long expirationMs;
    @Value("${spring.jwt.refreshExpiration}")
    private Long refreshExpiration;

    public String generateToken(AppUser user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username",user.getUsername())
                .claim("email",user.getEmail())
                .claim("role",user.getRoles().stream().map(role -> role.getName().name()).toList())
                .claim("permission",user.getRoles().stream().flatMap(role -> role.getPermissions().stream()).map(p -> p.getPermission().name()).collect(Collectors.toSet()))
                .issuedAt(new Date())
                .signWith(getKey())
                .expiration(new Date(System.currentTimeMillis() + 1000 * expirationMs))
                .compact();
    }

    public String generateRefreshToken(AppUser user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .subject(user.getId().toString())
                .claim("username",user.getUsername())
                .claim("email",user.getEmail())
                .claim("role",user.getRoles().stream().map(role -> role.getName().name()).toList())
                .issuedAt(new Date())
                .signWith(getKey())
                .expiration(new Date(System.currentTimeMillis() + 1000 * refreshExpiration))
                .compact();
    }

    public Jwt parseToken(String token){
        try {
            var claims = getClaims(token);
            return new Jwt(claims,getKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
