package ntnu.group03.idata2900.ams.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for handling JWT tokens
 */
@Component
public class JwtUtil {

    //@Value("${jwt_secret_key}")
    private String SECRET_KEY = "1234";
    private static final String JWT_AUTH_KEY = "roles";

    /**
     * Check if a token is valid for a given user
     *
     * @param token       Token to validate
     * @param userDetails Object containing user details
     * @return True if the token matches the current user and is still valid
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUser(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Generates a token which is used to validate user when browsing website.
     * <b>Token is valid for 12 hours after it is generated.</b>
     *
     * @param userDetails details of user which is to receive a token
     * @return token
     */
    public String generateToken(UserDetails userDetails) {
        final long TIME_NOW = System.currentTimeMillis();
        final long MILLISECONDS_IN_HOUR = 60 * 60 * 1000;
        final long HOURS_WHICH_TOKEN_LASTS = 12;
        final long TIME_WHEN_USER_GETS_KICKED = TIME_NOW + (MILLISECONDS_IN_HOUR * HOURS_WHICH_TOKEN_LASTS);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(JWT_AUTH_KEY, userDetails.getAuthorities())
                .setIssuedAt(new Date(TIME_NOW))
                .setExpiration(new Date(TIME_WHEN_USER_GETS_KICKED))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Find username from a JWT token
     *
     * @param token JWT token
     * @return Username
     */
    public String extractUser(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Finds expiration date from token.
     *
     * @param token token
     * @return expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts claims form token.
     *
     * @param token token
     * @param claimsResolver function which is applied to claims to retrieve correct claim.
     * @return claims
     * @param <T> type of claim which is to be extracted
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from token.
     * @param token token
     * @return claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if token is expired.
     *
     * @param token token
     * @return true if token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
