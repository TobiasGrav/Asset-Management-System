package ntnu.group03.idata2900.ams.security;

import io.jsonwebtoken.Jwts;
import ntnu.group03.idata2900.ams.security.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void testExtractUser() {
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.extractUser(token);
        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(token, userDetails));

        UserDetails otherUserDetails = mock(UserDetails.class);
        when(otherUserDetails.getUsername()).thenReturn("otheruser");

        assertFalse(jwtUtil.validateToken(token, otherUserDetails));
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtUtil.generateToken(userDetails);
        assertFalse(jwtUtil.isTokenExpired(token));

        // Manually create an expired token
        SecretKey secretKey = new SecretKeySpec("1234@£fdsuifsdufsduåpåsdlvødslkfdgpiosdhgopreqht3498yt34yud!&!%#&##¤¤%%%".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        String expiredToken = Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(secretKey)
                .compact();

        assertTrue(jwtUtil.isTokenExpired(expiredToken));
    }

    @Test
    void testExtractExpiration() {
        String token = jwtUtil.generateToken(userDetails);
        Date expirationDate = jwtUtil.extractExpiration(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }
}
