package ntnu.group03.idata2900.ams.security;

import ntnu.group03.idata2900.ams.model.Role;
import ntnu.group03.idata2900.ams.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AccessUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final boolean active;
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    /**
     * Creates a new instance of AccessUserDetails.
     *
     * @param user user
     */
    public AccessUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.convertRoles(user.getRoles());
    }

    private void convertRoles(Set<Role> roles) {
        authorities.clear();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    /**
     * Returns authorities of user.
     *
     * @return authoresses
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    /**
     * Returns password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used for login in, which is user email.
     *
     * @return email
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Checks if account is expired.
     *
     * @return active
     */
    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    /**
     * Checks if account is locked.
     *
     * @return active
     */
    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    /**
     * Checks if credentials are expired.
     *
     * @return active
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    /**
     * Checks if account is enabled.
     *
     * @return active
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
