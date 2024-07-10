package org.example.vegetableshopping.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.vegetableshopping.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Tạo và xác thực token
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret.key}")
    private String jwtSecret;

    @Value("${app.jwt.expiration.milliseconds}")
    private long jwtExpiration;

    public String generateToken(String username) {
        Date expiration = new Date(new Date().getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(key())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            throw new AuthenticationException("Invalid JWT Token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new AuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new AuthenticationException("Jwt claims string is null or empty");
        }
    }

}
