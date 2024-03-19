package ntnu.group03.idata2900.ams.security;

import lombok.Getter;

/**
 * Data sent as a response after authentication succeeded
 */
@Getter
public record AuthenticationResponse(String jwtToken) {

}
