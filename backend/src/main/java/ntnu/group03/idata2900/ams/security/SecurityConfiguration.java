package ntnu.group03.idata2900.ams.security;

import ntnu.group03.idata2900.ams.util.SecurityAccessUtil;
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

@Configuration
@EnableMethodSecurity
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
    public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
        // Allow JWT authentication
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/user/**").hasAnyAuthority(SecurityAccessUtil.USER, SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/authenticate").permitAll()
                        .requestMatchers("/api/assets").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/assets/{id}").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/assets/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/assetOnSites/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/sites/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/services/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/comments/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/servicesCompleted/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/companies/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/users/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/datasheets/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers("/api/spareParts/**").hasAuthority(SecurityAccessUtil.ADMIN)
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * This method is called to decide what encryption to use for password checking
     *
     * @return The password encryptor
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
