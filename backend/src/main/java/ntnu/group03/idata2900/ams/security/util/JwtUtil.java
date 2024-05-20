package ntnu.group03.idata2900.ams.security.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for handling JWT tokens
 */
@Component
public class JwtUtil {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;
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
        return userDetails != null && userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
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
                .subject(userDetails.getUsername())
                .claim(JWT_AUTH_KEY, userDetails.getAuthorities())
                .issuedAt(new Date(TIME_NOW))
                .expiration(new Date(TIME_WHEN_USER_GETS_KICKED))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
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
    public Date extractExpiration(String token) {
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
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Checks if token is expired.
     *
     * @param token token
     * @return true if token is expired, false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * sets the secret key, necessary for testing.
     * @param secretKey
     */
    public void setSecretKey(String secretKey) {
        this.SECRET_KEY = secretKey;
    }
}
