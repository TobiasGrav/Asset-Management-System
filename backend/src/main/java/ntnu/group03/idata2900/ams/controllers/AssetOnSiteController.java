package ntnu.group03.idata2900.ams.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Asset On Site API", description = "Endpoints for managing assets on site")
public class AssetOnSiteController {

    private final AssetOnSiteService assetOnSiteService;
    private final UserService userService;
    private final SiteService siteService;

    private static final String ASSET_ON_SITE_NOT_FOUND = "AssetOnSite not found with id: {}";

    private static final String ASSET_ON_SITE_FOUND = "Asset on site found with ID: {}";

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
    @Operation(summary = "Get all assets on site", description = "Retrieves a list of all assets on site.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @GetMapping("/admin/assetOnSites")
    public List<AssetOnSite> getAll() {
        return assetOnSiteService.getAll();
    }

    /**
     * Returns asset on site by given id
     *
     * @param id id of asset on site
     * @return returns asset on site by given id
     */
    @Operation(summary = "Get asset on site by ID", description = "Retrieves an asset on site based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Asset on site found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "404", description = "Asset on site not found")
    @GetMapping("/admin/assetOnSites/{id}")
    public ResponseEntity<AssetOnSite> getAssetOnSite(@PathVariable int id){
        Optional<AssetOnSite> assetOnSite = this.assetOnSiteService.getAssetOnSite(id);
        if (assetOnSite.isEmpty()){
            log.warn(ASSET_ON_SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info(ASSET_ON_SITE_FOUND, id);
            return new ResponseEntity<>(assetOnSite.get(), HttpStatus.OK);
        }
    }

    /**
     * Returns set of all assets on site matching given site id for admin.
     *
     * @param id site id
     *
     * @return returns set of all assets on site
     */
    @Operation(summary = "Get all assets on site by site ID for admin", description = "Retrieves a set of all assets on site based on the provided site ID for admin.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "404", description = "Site not found")
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
    @Operation(summary = "Get all assets on site by site ID for user", description = "Retrieves a set of all assets on site based on the provided site ID for user.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "404", description = "Site not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
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
    @Operation(summary = "Get asset on site by site ID and asset ID for admin", description = "Retrieves an asset on site based on the provided site ID and asset ID for admin.")
    @ApiResponse(responseCode = "200", description = "Asset on site found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "404", description = "Site or asset on site not found")
    @GetMapping("/admin/sites/{id}/assetsOnSite/{aosId}")
    public ResponseEntity<AssetOnSite> getAssetsOnSiteAdmin(@PathVariable int id, @PathVariable int aosId){
        Optional<Site> site = this.siteService.getSite(id);
        Optional<AssetOnSite> asset = this.assetOnSiteService.getAssetOnSite(aosId);
        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (asset.isEmpty()){
            log.warn(ASSET_ON_SITE_NOT_FOUND, aosId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info(ASSET_ON_SITE_FOUND, aosId);
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
    @ApiResponse(responseCode = "200", description = "Asset on site found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "404", description = "Site or asset on site not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user/sites/{id}/assetsOnSite/{aosId}")
    public ResponseEntity<AssetOnSite> getAssetsOnSiteUser(@PathVariable int id, @PathVariable int aosId){
        Optional<Site> site = this.siteService.getSite(id);
        Optional<AssetOnSite> asset = this.assetOnSiteService.getAssetOnSite(aosId);
        User user = userService.getSessionUser();

        if (site.isEmpty()){
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (asset.isEmpty()){
            log.warn(ASSET_ON_SITE_NOT_FOUND, aosId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!userService.hasAccessToAssetOnSite(user, id, aosId)) {
            log.warn("User {} is not authorized to access site with ID {}", user.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info(ASSET_ON_SITE_FOUND, aosId);
        return new ResponseEntity<>(asset.get(), HttpStatus.OK);
    }


    /**
     * Creates a new assetOnSite.
     *
     * @param assetOnSite The assetOnSite object to be created.
     * @return ResponseEntity containing the created assetOnSite and HTTP status code 201 (CREATED).
     */
    @Operation(summary = "Create a new asset on site", description = "Creates a new asset on site.")
    @ApiResponse(responseCode = "201", description = "Asset on site created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
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
    @Operation(summary = "Update an existing asset on site", description = "Updates an existing asset on site based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Asset on site updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetOnSite.class)))
    @ApiResponse(responseCode = "404", description = "Asset on site not found")
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
    @Operation(summary = "Delete an asset on site", description = "Deletes an asset on site based on the provided ID.")
    @ApiResponse(responseCode = "204", description = "Asset on site deleted")
    @ApiResponse(responseCode = "404", description = "Asset on site not found")
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
