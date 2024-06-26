package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SparePartDto;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.model.SparePart;
import ntnu.group03.idata2900.ams.services.AssetService;
import ntnu.group03.idata2900.ams.services.SparePartService;
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
@Tag(name = "Spare Part API", description = "Endpoints for managing spare parts")
public class SparePartController {

    private final SparePartService sparePartService;
    private final AssetService assetService;

    private static final String SPARE_PART_NOT_FOUND = "SparePart not found with id: {}";

    /**
     * Creates a new instance of SparePartController.
     *
     * @param sparePartService sparePartService
     * @param assetService assetService
     */
    public SparePartController(SparePartService sparePartService, AssetService assetService) {
        this.sparePartService = sparePartService;
        this.assetService = assetService;
    }

    /**
     * Returns list of all spareParts in database
     *
     * @return List of all spareParts in database
     */
    @Operation(summary = "Get all spare parts", description = "Retrieves a list of all spare parts.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SparePart.class)))
    @GetMapping("/admin/spareParts")
    public List<SparePart> getAll() {
        return sparePartService.getAll();
    }

    /**
     * Get a sparePart from database matching given id if it exists.
     *
     * @param id potential id of a sparePart
     * @return a ModelAndView containing sparePart in JSON format
     */
    @Operation(summary = "Get spare part by ID", description = "Retrieves a spare part based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Spare part found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SparePart.class)))
    @ApiResponse(responseCode = "404", description = "Spare part not found")
    @GetMapping("/user/spareParts/{id}")
    public ResponseEntity<SparePart> getSparePart(@PathVariable int id) {
        Optional<SparePart> sparePart = this.sparePartService.getSparePart(id);
        if (sparePart.isEmpty()) {
            log.warn(SPARE_PART_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("SparePart found with ID: {}", id);
            return new ResponseEntity<>(sparePart.get(), HttpStatus.OK);
        }
    }

    /**
     * Returns set of all spare parts for given asset
     *
     * @param id asset id
     * @return returns set of all spare parts for given asset
     */
    @Operation(summary = "Get all spare parts for an asset", description = "Retrieves a set of all spare parts for a given asset.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SparePart.class)))
    @ApiResponse(responseCode = "404", description = "Asset not found")
    @GetMapping("/user/asset/{id}/spareParts")
    public ResponseEntity<Set<SparePart>> getAllSparePartsForAsset(@PathVariable int id){
        Optional<Asset> asset = this.assetService.getAsset(id);
        if (asset.isEmpty()){
            log.warn("Asset was not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Asset found with ID {}, the asset has {} spare parts", id, asset.get().getSpareParts().size());
            return new ResponseEntity<>(asset.get().getSpareParts(), HttpStatus.OK);
        }
    }


    /**
     * Creates a new sparePart.
     *
     * @param sparePart The sparePart object to be created.
     * @return ResponseEntity containing the created sparePart and HTTP status code 201 (CREATED).
     */
    @Operation(summary = "Create a new spare part", description = "Creates a new spare part.")
    @ApiResponse(responseCode = "201", description = "Spare part created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SparePart.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
    @PostMapping("/admin/spareParts")
    public ResponseEntity<SparePart> createSparePart(@RequestBody SparePartDto sparePart) {
        try {
            SparePart createdSparePart = sparePartService.createSparePart(sparePart);
            log.info("SparePart created with ID: {}", createdSparePart.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSparePart);
        } catch (Exception e) {
            log.error("Error creating sparePart", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing sparePart.
     *
     * @param id             The ID of the sparePart to be updated.
     * @param updatedSparePart The updated sparePart object.
     * @return ResponseEntity containing the updated sparePart (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the sparePart with the given ID doesn't exist.
     */
    @Operation(summary = "Update an existing spare part", description = "Updates an existing spare part based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Spare part updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SparePart.class)))
    @ApiResponse(responseCode = "404", description = "Spare part not found")
    @PutMapping("/admin/spareParts/{id}")
    public ResponseEntity<SparePart> updateSparePart(@PathVariable int id, @RequestBody SparePartDto updatedSparePart) {
        Optional<SparePart> existingSparePart = sparePartService.getSparePart(id);
        if (existingSparePart.isEmpty()) {
            log.warn(SPARE_PART_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            SparePart sparePartToUpdate = existingSparePart.get();
            sparePartToUpdate.setNumberOfParts(updatedSparePart.getNumberOfParts());
            sparePartToUpdate.setPositionDiagramUrl(updatedSparePart.getPositionDiagramUrl());
            sparePartToUpdate.setAsset(updatedSparePart.getAsset());
            sparePartService.updateSparePart(sparePartToUpdate);
            log.info("SparePart updated with ID: {}", id);
            return new ResponseEntity<>(sparePartToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a sparePart.
     *
     * @param id The ID of the sparePart to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the sparePart with the given ID doesn't exist.
     */
    @Operation(summary = "Delete a spare part", description = "Deletes a spare part based on the provided ID.")
    @ApiResponse(responseCode = "204", description = "Spare part deleted")
    @ApiResponse(responseCode = "404", description = "Spare part not found")
    @DeleteMapping("/admin/spareParts/{id}")
    public ResponseEntity<SparePart> deleteSparePart(@PathVariable int id) {
        Optional<SparePart> existingSparePart = sparePartService.getSparePart(id);
        if (existingSparePart.isEmpty()) {
            log.warn(SPARE_PART_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            sparePartService.deleteSparePart(id);
            log.info("SparePart deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
