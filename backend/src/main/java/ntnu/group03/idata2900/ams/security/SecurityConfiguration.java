package ntnu.group03.idata2900.ams.security;

import ntnu.group03.idata2900.ams.security.util.SecurityAccessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@EnableMethodSecurity
@Tag(name = "Security Configuration", description = "Security settings for the application")
public class SecurityConfiguration {

    /**
     * A service providing our users from the database
     */
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * This method will be called automatically by the framework to find out what authentication to use.
     * Here we tell that we want to load users from a database
     *
     * @param auth Authentication builder
     * @throws Exception
     */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * This method will be called automatically by the framework to find out what authentication to use.
     *
     * @param http HttpSecurity setting builder
     * @throws Exception
     */
    @Bean
    @Operation(summary = "Configure authorization filter chain", description = "Sets up the authorization filter chain for the application, including JWT authentication.")
    public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
        // Allow JWT authentication
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/user/**").hasAnyAuthority(SecurityAccessUtil.USER, SecurityAccessUtil.ADMIN, SecurityAccessUtil.MANAGER, SecurityAccessUtil.TECHNICIAN)
                        .requestMatchers("/api/manager/**").hasAnyAuthority(SecurityAccessUtil.MANAGER, SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/technician/**").hasAnyAuthority(SecurityAccessUtil.TECHNICIAN, SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/authenticate").permitAll()
                        .requestMatchers("/api/assets/**").hasAnyAuthority(SecurityAccessUtil.ADMIN, SecurityAccessUtil.USER)
                        .requestMatchers("/api/datasheets/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/v3/api-docs/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/swagger-ui/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/swagger-ui.html").hasAuthority(SecurityAccessUtil.ADMIN)
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Operation(summary = "Authentication manager", description = "Creates an authentication manager bean.")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * This method is called to decide what encryption to use for password checking
     *
     * @return The password encryptor
     */
    @Operation(summary = "Password encoder", description = "Defines the password encoder bean using BCrypt.")
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
