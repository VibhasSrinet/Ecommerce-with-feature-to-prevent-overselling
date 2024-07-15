package org.example.productcatalogservice_may2024.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private SecretKey secretKey;


    public JwtUtil( @Value("${JWT_SECRET}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}

