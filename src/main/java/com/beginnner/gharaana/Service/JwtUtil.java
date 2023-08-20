package com.beginnner.gharaana.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String secret = "your-secret-key"; // Change this to your own secret key
    private final long expirationTimeInMillis = 86400000; // 24 hours in milliseconds

    public String generateToken(String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTimeInMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", email);
        claims.put("iat", now);
        claims.put("exp", expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }





    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractUserEmail(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            return null; // Handle extraction error or missing claim as needed
        }
    }
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            Date expirationDate = claims.getExpiration();

            // Check if the token is expired
            if (expirationDate.before(new Date())) {
                return false;
            }

            // Verify the token's signature
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            // If parsing and verification succeed, the token is valid
            return true;
        } catch (Exception e) {
            // An exception indicates token verification failure
            return false;
        }
    }


}

