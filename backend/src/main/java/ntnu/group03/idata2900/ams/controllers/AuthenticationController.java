package ntnu.group03.idata2900.ams.controllers;

import ntnu.group03.idata2900.ams.dto.SignUpDto;
import ntnu.group03.idata2900.ams.security.AuthenticationRequest;
import ntnu.group03.idata2900.ams.security.AuthenticationResponse;
import ntnu.group03.idata2900.ams.security.JwtUtil;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * HTTP POST request to /authenticate
     *
     * @param authenticationRequest The request JSON object containing email and password
     * @return OK + JWT token; Or UNAUTHORIZED
     */
    @PostMapping("/api/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

}
