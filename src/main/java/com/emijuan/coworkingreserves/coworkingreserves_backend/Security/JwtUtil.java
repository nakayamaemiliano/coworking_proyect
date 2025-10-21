package com.emijuan.coworkingreserves.coworkingreserves_backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private  final SecretKey secretKey;
    private  final long jwtExpirationMs;


    public JwtUtil(@Value("${app.jwt.secret}") String jwtSecret,
                   @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken (String userEmail,String role){
        return Jwts.builder()
                .subject(userEmail)
                .claim("rol",role)
                .issuedAt(new Date(System.currentTimeMillis() + jwtExpirationMs ))
                .signWith(secretKey,Jwts.SIG.HS256)
                .compact();

    }

    public String getUserEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return (String) parseClaims(token).get("rol");
    }


    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Token inválido: " + e.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }





}
