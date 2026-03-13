package com.learning.taskmanager.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class Jwt {

    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpiration(String token){
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId(){
        return Long.valueOf(claims.getSubject());
    }

    public List<String> getRoles(){
        return claims.get("role", List.class);
    }

    public List<String> getPermissions(){
        return claims.get("permission", List.class);
    }

    public String generate(){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }
}