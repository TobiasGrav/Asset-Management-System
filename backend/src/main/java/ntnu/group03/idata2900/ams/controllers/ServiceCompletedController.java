package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.services.ServiceCompletedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/servicesCompleted")
public class ServiceCompletedController {

    private final ServiceCompletedService serviceCompletedService;

    private static final String SERVICE_COMPLETED_NOT_FOUND = "ServiceCompleted not found with id: {}";

    /**
     * Creates a new instance of ServiceCompletedController.
     *
     * @param serviceCompletedService serviceCompletedService
     */
    public ServiceCompletedController(ServiceCompletedService serviceCompletedService) {
        this.serviceCompletedService = serviceCompletedService;
    }

    /**
     * Returns list of all completedServices in database
     *
     * @return List of all completedServices in database
     */
    @GetMapping
    public List<ServiceCompleted> getAll() {
        return serviceCompletedService.getAll();
    }

    /**
     * Get a serviceCompleted from database matching given id if it exists.
     *
     * @param id potential id of a serviceCompleted
     * @return a ModelAndView containing serviceCompleted in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCompleted> getServiceCompleted(@PathVariable int id) {
        Optional<ServiceCompleted> serviceCompleted = this.serviceCompletedService.getServiceCompleted(id);
        if (serviceCompleted.isEmpty()) {
            log.warn(SERVICE_COMPLETED_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("ServiceCompleted found with ID: {}", id);
            return new ResponseEntity<>(serviceCompleted.get(), HttpStatus.OK);
        }
    }


    /**
     * Creates a new serviceCompleted.
     *
     * @param serviceCompleted The serviceCompleted object to be created.
     * @return ResponseEntity containing the created serviceCompleted and HTTP status code 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<ServiceCompleted> createServiceCompleted(@RequestBody ServiceCompletedDto serviceCompleted) {
        try {
            ServiceCompleted createdServiceCompleted = serviceCompletedService.createServiceCompleted(serviceCompleted);
            log.info("ServiceCompleted created with ID: {}", createdServiceCompleted.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdServiceCompleted);
        } catch (Exception e) {
            log.error("Error creating serviceCompleted", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing serviceCompleted.
     *
     * @param id             The ID of the serviceCompleted to be updated.
     * @param updatedServiceCompleted The updated serviceCompleted object.
     * @return ResponseEntity containing the updated serviceCompleted (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the serviceCompleted with the given ID doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceCompleted> updateServiceCompleted(@PathVariable int id, @RequestBody ServiceCompletedDto updatedServiceCompleted) {
        Optional<ServiceCompleted> existingServiceCompleted = serviceCompletedService.getServiceCompleted(id);
        if (existingServiceCompleted.isEmpty()) {
            log.warn(SERVICE_COMPLETED_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ServiceCompleted serviceCompletedToUpdate = existingServiceCompleted.get();
            serviceCompletedToUpdate.setLastService(updatedServiceCompleted.getLastService());
            serviceCompletedToUpdate.setTimeCompleted(updatedServiceCompleted.getTimeCompleted());
            serviceCompletedToUpdate.setService(updatedServiceCompleted.getService());
            serviceCompletedToUpdate.setAssetOnSite(updatedServiceCompleted.getAssetOnSite());
            serviceCompletedToUpdate.setUser(updatedServiceCompleted.getUser());
            serviceCompletedService.updateServiceCompleted(serviceCompletedToUpdate);
            log.info("ServiceCompleted updated with ID: {}", id);
            return new ResponseEntity<>(serviceCompletedToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a serviceCompleted.
     *
     * @param id The ID of the serviceCompleted to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the serviceCompleted with the given ID doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceCompleted> deleteServiceCompleted(@PathVariable int id) {
        Optional<ServiceCompleted> existingServiceCompleted = serviceCompletedService.getServiceCompleted(id);
        if (existingServiceCompleted.isEmpty()) {
            log.warn(SERVICE_COMPLETED_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            serviceCompletedService.deleteServiceCompleted(id);
            log.info("ServiceCompleted deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
