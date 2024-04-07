package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SiteDto;
import ntnu.group03.idata2900.ams.dto.SparePartDto;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.model.SparePart;
import ntnu.group03.idata2900.ams.services.SparePartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/spareParts")
public class SparePartController {

    private final SparePartService sparePartService;

    private static final String SPARE_PART_NOT_FOUND = "SparePart not found with id: {}";

    /**
     * Creates a new instance of SparePartController.
     *
     * @param sparePartService sparePartService
     */
    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    /**
     * Returns list of all spareParts in database
     *
     * @return List of all spareParts in database
     */
    @GetMapping
    public List<SparePart> getAll() {
        return sparePartService.getAll();
    }

    /**
     * Get a sparePart from database matching given id if it exists.
     *
     * @param id potential id of a sparePart
     * @return a ModelAndView containing sparePart in JSON format
     */
    @GetMapping("/{id}")
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
     * Creates a new sparePart.
     *
     * @param sparePart The sparePart object to be created.
     * @return ResponseEntity containing the created sparePart and HTTP status code 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<SparePart> createSparePart(@RequestBody SparePartDto sparePart) {
        try {
            SparePart createdSite = sparePartService.createSparePart(sparePart);
            log.info("SparePart created with ID: {}", createdSite.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSite);
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
    @PutMapping("/{id}")
    public ResponseEntity<SparePart> updateSparePart(@PathVariable int id, @RequestBody SparePartDto updatedSparePart) {
        Optional<SparePart> existingSparePart = sparePartService.getSparePart(id);
        if (existingSparePart.isEmpty()) {
            log.warn(SPARE_PART_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            SparePart siteToUpdate = existingSparePart.get();
            siteToUpdate.setNumberOfParts(updatedSparePart.getNumberOfParts());
            siteToUpdate.setPositionDiagramUrl(updatedSparePart.getPositionDiagramUrl());
            siteToUpdate.setAsset(updatedSparePart.getAsset());
            sparePartService.updateSparePart(siteToUpdate);
            log.info("SparePart updated with ID: {}", id);
            return new ResponseEntity<>(siteToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a sparePart.
     *
     * @param id The ID of the sparePart to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the sparePart with the given ID doesn't exist.
     */
    @DeleteMapping("/{id}")
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
