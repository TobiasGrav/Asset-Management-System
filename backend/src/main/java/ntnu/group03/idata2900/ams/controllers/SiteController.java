package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.SiteDto;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.services.SiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;

    private static final String SITE_NOT_FOUND = "Site not found with id: {}";

    /**
     * Creates a new instance of SiteController.
     *
     * @param siteService siteService
     */
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    /**
     * Returns list of all sites in database
     *
     * @return List of all sites in database
     */
    @GetMapping
    public List<Site> getAll() {
        return siteService.getAll();
    }

    /**
     * Get a site from database matching given id if it exists.
     *
     * @param id potential id of a site
     * @return a ModelAndView containing site in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<Site> getSite(@PathVariable int id) {
        Optional<Site> site = this.siteService.getSite(id);
        if (site.isEmpty()) {
            log.warn(SITE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Site found with ID: {}", id);
            return new ResponseEntity<>(site.get(), HttpStatus.OK);
        }
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
}