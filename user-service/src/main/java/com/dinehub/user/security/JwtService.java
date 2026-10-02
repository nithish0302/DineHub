package com.dinehub.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private  long expirationTime;


    public String generateToken(String userEmail){
        SecretKey key= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().subject(userEmail).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expirationTime)).signWith(key).compact();
    }
}
