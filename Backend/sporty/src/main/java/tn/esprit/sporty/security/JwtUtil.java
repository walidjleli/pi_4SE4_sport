package tn.esprit.sporty.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import tn.esprit.sporty.Entity.User;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    // ✅ Use a strong generated key instead of a weak string key
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long accessTokenValidity = 60 * 60 * 1000;  // 1 hour

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenCreateTime)
                .setExpiration(tokenValidity)
                .signWith(secretKey)  // ✅ Use strong key
                .compact();
    }

    public Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)  // ✅ Use strong key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        return claims.getExpiration().after(new Date());
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
