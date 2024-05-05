package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.AssetOnSiteDto;
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
public class AssetOnSiteController {

    private final AssetOnSiteService assetOnSiteService;
    private final UserService userService;
    private final SiteService siteService;

    private static final String ASSET_ON_SITE_NOT_FOUND = "AssetOnSite not found with id: {}";
    private static final String SITE_NOT_FOUND = "Site not found with id: {}";

    /**
     * Creates a new instance of AssetOnSiteController.
     *
     * @param assetOnSiteService assetOnSiteService
     * @param userService userService
     * @param siteService siteService
     */
    public AssetOnSiteController(AssetOnSiteService assetOnSiteService, UserService userService, SiteService siteService) {
        this.assetOnSiteService = assetOnSiteService;
        this.userService = userService;
        this.siteService = siteService;
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
     * Returns set of all assets on site matching given site id for admin.
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
     * Returns set of all assets on site matching given site id for user.
     *
     * @param id site id
     *
     * @return returns set of all assets on site
     */
    @GetMapping("/user/sites/{id}/assetsOnSite")
    public ResponseEntity<Set<AssetOnSite>> getAllAssetsOnSiteUser(@PathVariable int id){
        Optional<Site> site = this.siteService.getSite(id);
        User user = userService.getSessionUser();
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userService.hasAccessToSite(user, id)) {
            log.warn("User {} is not authorized to access site with ID {}", user.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("All assets found with given site ID: {}", id);
        return new ResponseEntity<>(site.get().getAssetOnSites(), HttpStatus.OK);
    }

    /**
     * Returns asset on site matching given id for admin.
     *
     * @param id site id
     * @param aosId asset on site id
     * @return returns asset on site
     */
    @GetMapping("/admin/sites/{id}/assetsOnSite/{aosId}")
    public ResponseEntity<AssetOnSite> getAssetsOnSiteAdmin(@PathVariable int id, @PathVariable int aosId){
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
     * Returns asset on site matching given id for user.
     *
     * @param id site id
     * @param aosId asset on site id
     * @return returns asset on site
     */
    @GetMapping("/user/sites/{id}/assetsOnSite/{aosId}")
    public ResponseEntity<AssetOnSite> getAssetsOnSiteUser(@PathVariable int id, @PathVariable int aosId){
        Optional<Site> site = this.siteService.getSite(id);
        Optional<AssetOnSite> asset = this.assetOnSiteService.getAssetOnSite(aosId);
        User user = userService.getSessionUser();

        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (asset.isEmpty()){
            log.warn("Asset on site not found with ID: {}", aosId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!userService.hasAccessToAssetOnSite(user, id, aosId)) {
            log.warn("User {} is not authorized to access site with ID {}", user.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("Asset on site found with ID: {}", aosId);
        return new ResponseEntity<>(asset.get(), HttpStatus.OK);
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
            assetOnSiteToUpdate.setAssetOnSiteTag(updatedAssetOnSite.getAssetOnSiteTag());
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
