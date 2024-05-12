package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SignUpDto;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.SiteService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final SiteService siteService;

    private static final String USER_NOT_FOUND = "User not found with id: {}";

    private static final String SITE_NOT_FOUND = "Site not found with id: {}";

    /**
     * Creates a new instance of UserController.
     *
     * @param userService userService
     * @param siteService siteService
     */
    public UserController(UserService userService, SiteService siteService) {
        this.userService = userService;
        this.siteService = siteService;
    }

    /**
     * Returns list of all users in database
     *
     * @return List of all users in database
     */
    @GetMapping("/admin/users")
    public List<SignUpDto> getAll() {
        return userService.getAll();
    }

    /**
     * Returns list of all users in company
     *
     * @return List of all users in company
     */
    @GetMapping("/manager/users")
    public List<SignUpDto> getAllUsersForManager() {
        return this.userService.convertAll(this.userService.getSessionUser().getCompany().getUsers());
    }

    /**
     * Get a user from database matching given id if it exists.
     *
     * @param id potential id of a user
     * @return a user object in JSON format
     */
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<SignUpDto> getUser(@PathVariable int id) {
        SignUpDto user = this.userService.getUserByIdThenConvertToSignupDto(id);
        if (user == null) {
            log.warn(USER_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("User found with ID: {}", id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /**
     * Returns set of all users by site id
     *
     * @param id id of site
     * @return returns set of all users connected to given site id
     */
    @GetMapping( "/admin/sites/{id}/users")
    public ResponseEntity<List<SignUpDto>> getAllUsersBySite(@PathVariable int id){
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            List<SignUpDto> users = this.userService.convertAll(site.get().getUsers());
            log.info("All users found with given site ID: {}", id);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    /**
     * Get a user from database matching given id if exists and return all its sites
     *
     * @param id potential id of user
     * @return all sites of a given user
     */
    @GetMapping("/admin/users/{id}/sites")
    public ResponseEntity<Set<Site>> getAllSitesOfUser(@PathVariable int id){
        SignUpDto user = this.userService.getUserByIdThenConvertToSignupDto(id);
        if (user == null){
            log.warn(USER_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("User found with ID: {}", id);
            return new ResponseEntity<>(user.getSites(), HttpStatus.OK);
        }
    }



    /**
     * Creates a new user.
     *
     * @param user The user object to be created.
     * @return ResponseEntity containing the created user and HTTP status code 201 (CREATED).
     */
    @PostMapping("/admin/users")
    public ResponseEntity<User> createUser(@RequestBody SignUpDto user) {
        try {
            User createdUser = userService.createUserForSignUp(user, null);
            log.info("User created with ID: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Error creating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Creates a new user.
     *
     * @param user The user object to be created.
     * @return ResponseEntity containing the created user and HTTP status code 201 (CREATED).
     */
    @PostMapping("/manager/users")
    public ResponseEntity<User> createUserAsManager(@RequestBody SignUpDto user) {
        try {
            User moderator = userService.getSessionUser();
            User createdUser = userService.createUserForSignUp(user, moderator.getCompany());
            log.info("User created with ID: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            log.error("Error creating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing user.
     *
     * @param id             The ID of the user to be updated.
     * @param updatedUser The updated user object.
     * @return ResponseEntity containing the updated user (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the user with the given ID doesn't exist.
     */
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody SignUpDto updatedUser) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isEmpty()) {
            log.warn(USER_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            User userToUpdate = existingUser.get();
            userService.updateUser(existingUser.get().getId(), updatedUser);
            log.info("User updated with ID: {}", id);
            return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a user.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the user with the given ID doesn't exist.
     */
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isEmpty()) {
            log.warn(USER_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.deleteUser(id);
            log.info("User deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
