package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SiteDto;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.SiteService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Site API", description = "Endpoints for managing sites")
public class SiteController {

    private final SiteService siteService;
    private final UserService userService;

    private static final String SITE_NOT_FOUND = "Site not found with id: {}";
    private static final String SITE_FOUND = "Site found with ID: {}";

    /**
     * Creates a new instance of SiteController.
     *
     * @param siteService siteService
     * @param userService userService
     */
    public SiteController(SiteService siteService, UserService userService) {
        this.siteService = siteService;
        this.userService = userService;
    }

    /**
     * Returns list of all sites in database
     *
     * @return List of all sites in database
     */
    @Operation(summary = "Get all sites", description = "Retrieves a list of all sites.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @GetMapping("/admin/sites")
    public List<Site> getAll() {
        return siteService.getAll();
    }

    /**
     * Returns list of all sites the user has access to
     *
     * @return set of all sites the user has access to
     */
    @Operation(summary = "Get all sites for user", description = "Retrieves a set of all sites the user has access to.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @GetMapping("/user/sites")
    public Set<Site> getAllSitesForUser(){
        return userService.getSessionUser().getSites();
    }

    /**
     * Get a site from database matching given id if it exists.
     *
     * @param id potential id of a site
     * @return a site object in JSON format
     */
    @Operation(summary = "Get site by ID for admin", description = "Retrieves a site based on the provided ID for admin.")
    @ApiResponse(responseCode = "200", description = "Site found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @ApiResponse(responseCode = "404", description = "Site not found")
    @GetMapping("/admin/sites/{id}")
    public ResponseEntity<Site> getSiteAdmin(@PathVariable int id) {
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()) {
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info(SITE_FOUND, id);
            return new ResponseEntity<>(site.get(), HttpStatus.OK);
        }
    }

    /**
     * Get a site from database matching given id if it exists.
     *
     * @param id potential id of a site
     * @return a site object in JSON format
     */
    @Operation(summary = "Get site by ID for user", description = "Retrieves a site based on the provided ID for user.")
    @ApiResponse(responseCode = "200", description = "Site found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @ApiResponse(responseCode = "404", description = "Site not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user/sites/{id}")
    public ResponseEntity<Site> getSiteUser(@PathVariable int id) {
        User user = userService.getSessionUser();
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userService.hasAccessToSite(user, id)) {
            log.warn("User {} is not authorized to access site with ID {}", user.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info(SITE_FOUND, id);
        return ResponseEntity.ok(site.get());
    }


    /**
     * Creates a new site.
     *
     * @param site The site object to be created.
     * @return ResponseEntity containing the created site and HTTP status code 201 (CREATED).
     */
    @Operation(summary = "Create a new site", description = "Creates a new site.")
    @ApiResponse(responseCode = "201", description = "Site created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
    @PostMapping("/admin/sites")
    public ResponseEntity<Site> createSite(@RequestBody SiteDto site) {
        try {
            Site createdSite = siteService.createSite(site);
            log.info("Site created with ID: {}", createdSite.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSite);
        } catch (Exception e) {
            log.error("Error creating site", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Adding user to site.
     *
     * @param id site id
     * @param userId user id
     * @return ResponseEntity containing the HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the site or user with the given ID doesn't exist.
     */
    @Operation(summary = "Add user to site", description = "Adds a user to a site based on the provided site ID and user ID.")
    @ApiResponse(responseCode = "200", description = "User added to site")
    @ApiResponse(responseCode = "404", description = "Site or user not found")
    @PutMapping("/manager/sites/{id}/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @PathVariable int userId){
        Optional<Site> site = siteService.getSite(id);
        Optional<User> user = userService.getUserById(userId);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (user.isEmpty()){
            log.warn("User not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.addUserToSite(user.get(), site.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    /**
     * Updates an existing site.
     *
     * @param id             The ID of the site to be updated.
     * @param updatedSite The updated site object.
     * @return ResponseEntity containing the updated site (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the site with the given ID doesn't exist.
     */
    @Operation(summary = "Update an existing site", description = "Updates an existing site based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Site updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @ApiResponse(responseCode = "404", description = "Site not found")
    @PutMapping("/admin/sites/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable int id, @RequestBody SiteDto updatedSite) {
        Optional<Site> existingSite = siteService.getSite(id);
        if (existingSite.isEmpty()) {
            log.warn(SITE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Site siteToUpdate = existingSite.get();
            siteToUpdate.setName(updatedSite.getName());
            siteToUpdate.setActive(updatedSite.isActive());
            siteService.updateSite(siteToUpdate);
            log.info("Site updated with ID: {}", id);
            return new ResponseEntity<>(siteToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a site.
     *
     * @param id The ID of the site to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the site with the given ID doesn't exist.
     */
    @Operation(summary = "Delete a site", description = "Deletes a site based on the provided ID.")
    @ApiResponse(responseCode = "204", description = "Site deleted")
    @ApiResponse(responseCode = "404", description = "Site not found")
    @DeleteMapping("/admin/sites/{id}")
    public ResponseEntity<Site> deleteSite(@PathVariable int id) {
        Optional<Site> existingSite = siteService.getSite(id);
        if (existingSite.isEmpty()) {
            log.warn(SITE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            siteService.deleteSite(id);
            log.info("Site deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Removes a user from site.
     *
     * @param id The ID of the site.
     * @param userId the ID of the user
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the user with the given ID doesn't exist.
     */
    @Operation(summary = "Remove user from site", description = "Removes a user from a site based on the provided site ID and user ID.")
    @ApiResponse(responseCode = "204", description = "User removed from site")
    @ApiResponse(responseCode = "404", description = "Site or user not found")
    @DeleteMapping("/manager/sites/{id}/users/{userId}")
    public ResponseEntity<Site> deleteUserFromSite(@PathVariable int id, @PathVariable int userId) {
        Optional<Site> existingSite = siteService.getSite(id);
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingSite.isEmpty() ) {
            log.warn(SITE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (existingUser.isEmpty()){
            log.warn("User not found with ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.removeSiteFromUser(existingUser.get(), existingSite.get());
            log.info("User removed from site with ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
