package com.kiranaos.kiranaos_auth_service.security;

import com.kiranaos.kiranaos_auth_service.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private SecretKey getSigningKey(){
        byte[] keyBytes=jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String email){
        Instant now=Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtProperties.getAccessTokenExpiryMs())))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }

    public String hashToken(String token){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 algorithm not found: "+e);
        }
    }

    public Claims extractAllClaims(String accessToken){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(Date.from(Instant.now()));
    }

    public Boolean isTokenValid(String token, String email){
        String extractedEmail=extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }
}
