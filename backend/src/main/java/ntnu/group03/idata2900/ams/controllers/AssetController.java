package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.AssetDto;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.repositories.AssetRepository;
import ntnu.group03.idata2900.ams.services.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/assets")
public class AssetController {
    private final AssetService assetService;

    private static final String ASSET_NOT_FOUND = "Asset not found with id: {}";

    /**
     * Creates a new instance of AssetController.
     *
     * @param assetService assetService
     */
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }


    /**
     * Returns list of all assets in database
     *
     * @return List of all assets in database
     */
    @GetMapping
    public List<Asset> getAll() {
        return assetService.getAll();
    }

    /**
     * Get an asset from database matching given id if it exists.
     *
     * @param id potential id of an asset
     * @return a ModelAndView containing asset in JSON format or page-not-found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAsset(@PathVariable int id) {
        Optional<Asset> asset = this.assetService.getAsset(id);
        if (asset.isEmpty()) {
            log.warn(ASSET_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Asset found with ID: {}", id);
            return new ResponseEntity<>(asset.get(), HttpStatus.OK);
        }
    }


    /**
     * Creates a new asset.
     *
     * @param asset The asset object to be created.
     * @return ResponseEntity containing the created asset and HTTP status code 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody AssetDto asset) {
        try {
            Asset createdAsset = assetService.createAsset(asset);
            log.info("Asset created with ID: {}", createdAsset.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
        } catch (Exception e) {
            log.error("Error creating asset", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing asset.
     *
     * @param id             The ID of the asset to be updated.
     * @param updatedAsset The updated asset object.
     * @return ResponseEntity containing the updated asset (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the asset with the given ID doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable int id, @RequestBody AssetDto updatedAsset) {
        Optional<Asset> existingAsset = assetService.getAsset(id);
        if (existingAsset.isEmpty()) {
            log.warn(ASSET_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Asset assetToUpdate = existingAsset.get();
            assetToUpdate.setName(updatedAsset.getName());
            assetToUpdate.setDescription(updatedAsset.getDescription());
            assetToUpdate.setActive(updatedAsset.isActive());
            assetService.updateAsset(assetToUpdate);
            log.info("Asset updated with ID: {}", id);
            return new ResponseEntity<>(assetToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a asset.
     *
     * @param id The ID of the asset to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the asset with the given ID doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Asset> deleteAsset(@PathVariable int id) {
        Optional<Asset> existingAsset = assetService.getAsset(id);
        if (existingAsset.isEmpty()) {
            log.warn(ASSET_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            assetService.deleteAsset(id);
            log.info("Asset deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
