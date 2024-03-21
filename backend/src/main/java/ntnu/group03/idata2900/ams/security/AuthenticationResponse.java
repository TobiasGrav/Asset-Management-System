package ntnu.group03.idata2900.ams.security;

/**
 * Data sent as a response after authentication succeeded
 */
public class AuthenticationResponse {
    private final String response;

    public AuthenticationResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return this.response;
    }
}
