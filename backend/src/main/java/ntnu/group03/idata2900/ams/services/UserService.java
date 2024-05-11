package ntnu.group03.idata2900.ams.services;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SignUpDto;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.model.Role;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.repositories.AssetOnSiteRepository;
import ntnu.group03.idata2900.ams.repositories.RoleRepository;
import ntnu.group03.idata2900.ams.repositories.SiteRepository;
import ntnu.group03.idata2900.ams.repositories.UserRepository;
import ntnu.group03.idata2900.ams.security.AccessUserDetails;
import ntnu.group03.idata2900.ams.util.SecurityAccessUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private static final int MIN_PASSWORD_LENGTH = 4;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SiteRepository siteRepository;
    private final AssetOnSiteRepository assetOnSiteRepository;

    private final Optional<Role> admin;
    private final Iterable<Site> sites;

    /**
     * creates a new instance of userService.
     *
     * @param userRepository userRepository
     * @param roleRepository roleRepository
     * @param siteRepository siteRepository
     * @param assetOnSiteRepository assetOnSiteRepository
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository, SiteRepository siteRepository, AssetOnSiteRepository assetOnSiteRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.assetOnSiteRepository = assetOnSiteRepository;
        this.admin = this.roleRepository.findByName(SecurityAccessUtil.ADMIN);
        this.siteRepository = siteRepository;
        this.sites = siteRepository.findAll();
    }

    /**
     * Convert all users to SignupDTO and exclude password. Then return list of all signupDTOs.
     *
     * @return return list of all signupDTOs with password excluded
     */
    public List<SignUpDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> {
                    SignUpDto signUpDto = new SignUpDto();
                    signUpDto.setFirstName(user.getFirstName());
                    signUpDto.setLastName(user.getLastName());
                    signUpDto.setCompany(user.getCompany());
                    signUpDto.setEmail(user.getEmail());
                    signUpDto.setPhoneNumber(user.getPhoneNumber());
                    signUpDto.setActive(user.isActive());
                    signUpDto.setCreationDate(user.getCreationDate());
                    signUpDto.setSites(user.getSites());
                    signUpDto.setServicesCompleted(user.getServicesCompleted());
                    signUpDto.setRoles(user.getRoles());
                    signUpDto.setId(user.getId());
                    return signUpDto;
                }).toList();
    }

    /**
     * Converts all users to signup dto and returns them
     *
     * @param users users to be converted
     * @return returns list of all signup DTOs
     */
    public List<SignUpDto> convertAll(Set<User> users) {
        return users.stream()
                .map(user -> {
                    SignUpDto signUpDto = new SignUpDto();
                    signUpDto.setFirstName(user.getFirstName());
                    signUpDto.setLastName(user.getLastName());
                    signUpDto.setCompany(user.getCompany());
                    signUpDto.setEmail(user.getEmail());
                    signUpDto.setPhoneNumber(user.getPhoneNumber());
                    signUpDto.setActive(user.isActive());
                    signUpDto.setCreationDate(user.getCreationDate());
                    signUpDto.setSites(user.getSites());
                    signUpDto.setServicesCompleted(user.getServicesCompleted());
                    signUpDto.setRoles(user.getRoles());
                    signUpDto.setId(user.getId());
                    return signUpDto;
                })
                .toList();
    }

    /**
     * Creates a new user if params are valid and email is not yet registered.
     *
     * @param userInfo information provided by SignUpDto instance
     */
    public User createUserForSignUp(SignUpDto userInfo) {
        if (!validEmail(userInfo.getEmail())) {
            log.error("Invalid email format: {}", userInfo.getEmail());
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (!validPassword(userInfo.getPassword())) {
            log.error("Invalid password: {}", userInfo.getEmail());
            throw new IllegalArgumentException("Invalid password.");
        }

        if (userInfo.getFirstName().trim().equals("") || userInfo.getLastName().trim().equals("")) {
            log.error("Name fields must be filled out for user: {}", userInfo.getEmail());
            throw new IllegalArgumentException("Name fields must be filled out.");
        }

        try {
            // Thrown when user is found
            loadUserByUsername(userInfo.getEmail());
            log.error("Email already registered: {}", userInfo.getEmail());
            throw new IllegalArgumentException("Email already registered.");
        } catch (NullPointerException e) {
            userInfo.setPassword(createHash(userInfo.getPassword()));
            User user = new User(userInfo);

            setUserRole(user);

            userRepository.save(user);
            log.info("New user created with email: {}", userInfo.getEmail());
            return user;
        }
    }

    /**
     * Retrieves a user by ID, then convert it to a signupDto and return it.
     *
     * @param id The ID of the user.
     * @return returns signupDto
     */
    public SignUpDto getUserByIdThenConvertToSignupDto(int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            return null;
        }

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setFirstName(user.get().getFirstName());
        signUpDto.setLastName(user.get().getLastName());
        signUpDto.setCompany(user.get().getCompany());
        signUpDto.setEmail(user.get().getEmail());
        signUpDto.setPhoneNumber(user.get().getPhoneNumber());
        signUpDto.setActive(user.get().isActive());
        signUpDto.setCreationDate(user.get().getCreationDate());
        signUpDto.setSites(user.get().getSites());
        signUpDto.setServicesCompleted(user.get().getServicesCompleted());
        signUpDto.setRoles(user.get().getRoles());
        signUpDto.setId(user.get().getId());
        return signUpDto;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return Optional containing the user if found, or an empty Optional if the user is not found.
     */
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user.
     *
     * @param signUpDto The User object to be created.
     * @return The created User object.
     */
    public User createAdmin(SignUpDto signUpDto) {
        User user = new User(signUpDto);
        user.setPassword(createHash(user.getPassword()));
        setUserRole(user);
        // TODO: Should user be added to sites when created or after?
        //setSitesForUser(user, signUpDto.getSites());
        return userRepository.save(user);
    }

    /**
     * Creates a new user.
     *
     * @param signUpDto The User object to be created.
     * @return The created User object.
     */
    public User createUser(SignUpDto signUpDto){
        User user = new User(signUpDto);
        user.setPassword(createHash(user.getPassword()));
        setUserRole(user);
        setAdminRole(user);
        setAllSitesForAdmin(user);
        return userRepository.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id          The ID of the user to be updated.
     * @param updatedUser The updated User object.
     */
    public void updateUser(int id, SignUpDto updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());;
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setPassword(createHash(updatedUser.getPassword()));
            user.setUpdatedDate(LocalDateTime.now());
            if (this.admin.isPresent() && user.getRoles().contains(this.admin.get())){
                setAdminRole(user);
            }
            log.info("User updated: {}", updatedUser.getEmail());
            userRepository.save(user);
        }
        log.error("User not found with ID: {}", id);
    }

    /**
     * Deletes a user.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            log.info("User deleted with ID: {}", id);
        } else {
            log.error("User not found with ID: {}", id);
        }
    }


    /**
     * Adds user to given sites
     *
     * @param user user to be added to sites
     * @param sites sites the user is added to
     */
    public void setSitesForUser(User user, Set<Site> sites){
        sites.forEach(site -> user.getSites().add(site));
    }

    /**
     * Adds admin user to all sites
     *
     * @param user user to get all sites
     */
    public void setAllSitesForAdmin(User user){
        sites.forEach(site -> user.getSites().add(site));
    }

    /**
     * Adds user role to user
     *
     * @param user user
     */
    public void setUserRole(User user){
        Role userRole = roleRepository.findByName(SecurityAccessUtil.USER).orElseThrow(() -> new IllegalArgumentException("Role USER not found"));
        user.getRoles().add(userRole);
    }

    /**
     * Checks if user has access to the site
     *
     * @param user user to be checked for access
     * @param siteId site id to check
     * @return returns true if user has access, false if not
     */
    public boolean hasAccessToSite(User user, int siteId){
        Optional<Site> site = siteRepository.findById(siteId);
        return site.filter(value -> user.getSites().contains(value)).isEmpty();
    }

    /**
     * Checks if user has access to the given asset on site
     *
     * @param user user to be checked for access
     * @param siteId site id to be checked
     * @param aosId asset on site id to be checked
     * @return returns true if user has access, false if not
     */
    public boolean hasAccessToAssetOnSite(User user, int siteId, int aosId) {
        Optional<AssetOnSite> assetOnSite = assetOnSiteRepository.findById(aosId);
        Optional<Site> site = siteRepository.findById(siteId);
        if (site.isEmpty() || !user.getSites().contains(site.get()) || assetOnSite.isEmpty()){
            return false;
        }
        return site.get().getAssetOnSites().contains(assetOnSite.get());
    }

    /**
     * Removes site from user
     *
     * @param user user to have site removed
     * @param site site to be removed
     */
    public void removeSiteFromUser(User user, Site site){
        user.getSites().remove(site);
        userRepository.save(user);
    }

    /**
     * Adds user to site
     *
     * @param user user to have site added
     * @param site site to be added
     */
    public void addUserToSite(User user, Site site){
        user.getSites().add(site);
        userRepository.save(user);
    }

    /**
     * Adds admin role to the user
     *
     * @param user user to have admin added to it
     */
    public void setAdminRole(User user) {
        Role adminRole = roleRepository.findByName(SecurityAccessUtil.ADMIN).orElseThrow(() -> new IllegalArgumentException("Role ADMIN not found"));
        user.getRoles().add(adminRole);
    }
    public void removeAdminRoleFromUser(User user){
        Role adminRole = roleRepository.findByName(SecurityAccessUtil.ADMIN).orElseThrow(() -> new IllegalArgumentException("Role ADMIN not found"));
        user.getRoles().remove(adminRole);
    }

    /**
     * Returns a user registered with given email if registered.
     *
     * @param email email
     * @return user with given email
     * @throws NullPointerException if there is no user registered with given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws NullPointerException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new AccessUserDetails(user.get());
        } else {
            log.error("User with email: {} not found.", email);
            throw new NullPointerException("User with email: " + email + " not found.");
        }
    }

    /**
     * Create a secure hash of a password
     *
     * @param password Plaintext password
     * @return BCrypt hash, with random salt
     */
    private String createHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Validates if a submitted email adheres to a specified email format.
     * <b>Validation includes checks that:
     * - The email contains exactly one '@' symbol.
     * - The email does not contain any empty spaces.
     * - There is at least one character before the '@' symbol.
     * - The local part (before the '@') does not start or end with a dot ('.'),
     *   and does not contain consecutive dots.
     * - The domain part (after the '@') starts with a non-hyphen character,
     *   contains only letters, digits, or hyphens, and ends with a dot followed by
     *   a top-level domain (TLD) of at least two letters.
     * - The overall length of the local part does not exceed 64 characters.</b>
     *
     * @param potentialEmail The submitted email to validate.
     * @return true if the submitted email follows the valid format, false otherwise.
     */
    private static boolean validEmail(String potentialEmail) {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@*[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return potentialEmail.matches(emailRegex);
    }

    /**
     * Checks if a submitted password is valid.
     *
     * @param password submitted password
     * @return true if password is valid, false otherwise
     */
    private static boolean validPassword(String password) {
        return !password.contains(" ") && password.length() > MIN_PASSWORD_LENGTH;
    }

    /**
     * Returns the user of the curren session.
     *
     * @return user of current session, null if there is no user in session.
     */
    public User getSessionUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElse(null);
    }
}
