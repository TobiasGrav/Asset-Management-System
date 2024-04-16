package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SiteDto;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.AssetOnSiteService;
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
public class SiteController {

    private final SiteService siteService;
    private final UserService userService;
    private final AssetOnSiteService assetOnSiteService;

    private static final String SITE_NOT_FOUND = "Site not found with id: {}";
    private static final String SITE_FOUND = "Site found with ID: {}";

    /**
     * Creates a new instance of SiteController.
     *
     * @param siteService siteService
     * @param userService userService
     * @param assetOnSiteService assetOnSiteService
     */
    public SiteController(SiteService siteService, UserService userService, AssetOnSiteService assetOnSiteService) {
        this.siteService = siteService;
        this.userService = userService;
        this.assetOnSiteService = assetOnSiteService;
    }

    /**
     * Returns list of all sites in database
     *
     * @return List of all sites in database
     */
    @GetMapping("/admin/sites")
    public List<Site> getAll() {
        return siteService.getAll();
    }

    /**
     * Returns list of all sites the user has access to
     *
     * @return set of all sites the user has access to
     */
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
     * Returns set of all assets on site matching given site id.
     *
     * @param id site id
     *
     * @return returns set of all assets on site
     */
    @GetMapping("/admin/sites/{id}/assetsOnSite")
    public ResponseEntity<Set<AssetOnSite>> getAllAssetsOnSite(@PathVariable int id){
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("All assets found with given site ID: {}", id);
            return new ResponseEntity<>(site.get().getAssetOnSites(), HttpStatus.OK);
        }
    }

    /**
     * Returns asset on site matching given id.
     *
     * @param id site id
     * @param aosId asset on site id
     * @return returns asset on site
     */
    @GetMapping("/admin/sites/{id}/assetsOnSite/{aosId}")
    public ResponseEntity<AssetOnSite> getAssetsOnSite(@PathVariable int id, @PathVariable int aosId){
        Optional<Site> site = this.siteService.getSite(id);
        Optional<AssetOnSite> asset = this.assetOnSiteService.getAssetOnSite(aosId);
        if (site.isEmpty() && asset.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Asset on site found with ID: {}", aosId);
            return new ResponseEntity<>(asset.get(), HttpStatus.OK);
        }
    }

    /**
     * Returns set of all users by site id
     *
     * @param id id of site
     * @return returns set of all users connected to given site id
     */
    @GetMapping( "/admin/sites/{id}/users")
    public ResponseEntity<Set<User>> getAllUsersBySite(@PathVariable int id){
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("All users found with given site ID: {}", id);
            return new ResponseEntity<>(site.get().getUsers(), HttpStatus.OK);
        }
    }

    /**
     * Get a site from database matching given id if it exists.
     *
     * @param id potential id of a site
     * @return a site object in JSON format
     */
    @GetMapping("/user/sites/{id}")
    public ResponseEntity<Site> getSiteUser(@PathVariable int id) {
        User user = userService.getSessionUser();
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!userService.hasAccessToSites(user, id)) {
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
    @PostMapping
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
     * Updates an existing site.
     *
     * @param id             The ID of the site to be updated.
     * @param updatedSite The updated site object.
     * @return ResponseEntity containing the updated site (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the site with the given ID doesn't exist.
     */
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
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
    @DeleteMapping("/admin/sites/{id}/users/{userId}")
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
