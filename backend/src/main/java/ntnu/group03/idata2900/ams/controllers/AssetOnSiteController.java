package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.AssetOnSiteDto;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.AssetOnSiteService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class AssetOnSiteController {

    private final AssetOnSiteService assetOnSiteService;
    private final UserService userService;

    private static final String ASSET_ON_SITE_NOT_FOUND = "AssetOnSite not found with id: {}";

    /**
     * Creates a new instance of AssetOnSiteController.
     *
     * @param assetOnSiteService assetOnSiteService
     * @param userService userService
     */
    public AssetOnSiteController(AssetOnSiteService assetOnSiteService, UserService userService) {
        this.assetOnSiteService = assetOnSiteService;
        this.userService = userService;
    }

    /**
     * Returns list of all AssetOnSites in database
     *
     * @return List of all AssetOnSites in database
     */
    @GetMapping("/admin/assetOnSites")
    public List<AssetOnSite> getAll() {
        return assetOnSiteService.getAll();
    }

    /**
     * Get an AssetOnSite from database matching given id if it exists.
     *
     * @param id potential id of a AssetOnSite
     * @return a ModelAndView containing AssetOnSite in JSON format
     */
    @GetMapping("/admin/assetOnSites/{id}")
    public ResponseEntity<AssetOnSite> getSite(@PathVariable int id) {
        Optional<AssetOnSite> assetOnSite = this.assetOnSiteService.getAssetOnSite(id);
        if (assetOnSite.isEmpty()) {
            log.warn(ASSET_ON_SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("AssetOnSite found with ID: {}", id);
            return new ResponseEntity<>(assetOnSite.get(), HttpStatus.OK);
        }
    }

    /**
     * Get an AssetOnSite from database matching given id if it exists.
     *
     * @param id potential id of a AssetOnSite
     * @return a ModelAndView containing AssetOnSite in JSON format
     */
    @GetMapping("/user/assetOnSites/{id}")
    public ResponseEntity<AssetOnSite> getSiteUser(@PathVariable int id) {
        Optional<AssetOnSite> assetOnSite = this.assetOnSiteService.getAssetOnSite(id);
        User user = userService.getSessionUser();
        if (assetOnSite.isEmpty()) {
            log.warn(ASSET_ON_SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userService.hasAccessToAssetOnSite(user, id)) {
            log.warn("User {} is not authorized to access assetOnSite with ID {}", user.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("AssetOnSite found with ID: {}", id);
        return new ResponseEntity<>(assetOnSite.get(), HttpStatus.OK);

    }


    /**
     * Creates a new assetOnSite.
     *
     * @param assetOnSite The assetOnSite object to be created.
     * @return ResponseEntity containing the created assetOnSite and HTTP status code 201 (CREATED).
     */
    @PostMapping("/admin/assetOnSites")
    public ResponseEntity<AssetOnSite> createSite(@RequestBody AssetOnSiteDto assetOnSite) {
        try {
            AssetOnSite createdAssetOnSite = assetOnSiteService.createAssetOnSite(assetOnSite);
            log.info("AssetOnSite created with ID: {}", createdAssetOnSite.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssetOnSite);
        } catch (Exception e) {
            log.error("Error creating AssetOnSite", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing AssetOnSite.
     *
     * @param id             The ID of the AssetOnSite to be updated.
     * @param updatedAssetOnSite The updated AssetOnSite object.
     * @return ResponseEntity containing the updated AssetOnSite (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the AssetOnSite with the given ID doesn't exist.
     */
    @PutMapping("/admin/assetOnSites/{id}")
    public ResponseEntity<AssetOnSite> updateSite(@PathVariable int id, @RequestBody AssetOnSiteDto updatedAssetOnSite) {
        Optional<AssetOnSite> existingAssetOnSite = assetOnSiteService.getAssetOnSite(id);
        if (existingAssetOnSite.isEmpty()) {
            log.warn(ASSET_ON_SITE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AssetOnSite assetOnSiteToUpdate = existingAssetOnSite.get();
            assetOnSiteToUpdate.setCommissionDate(updatedAssetOnSite.getCommissionDate());
            assetOnSiteService.updateAssetOnSite(assetOnSiteToUpdate);
            log.info("AssetOnSite updated with ID: {}", id);
            return new ResponseEntity<>(assetOnSiteToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a AssetOnSite.
     *
     * @param id The ID of the AssetOnSite to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the AssetOnSite with the given ID doesn't exist.
     */
    @DeleteMapping("/admin/assetOnSites/{id}")
    public ResponseEntity<AssetOnSite> deleteSite(@PathVariable int id) {
        Optional<AssetOnSite> existingAssetOnSite = assetOnSiteService.getAssetOnSite(id);
        if (existingAssetOnSite.isEmpty()) {
            log.warn(ASSET_ON_SITE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            assetOnSiteService.deleteAssetOnSite(id);
            log.info("AssetOnSite deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
