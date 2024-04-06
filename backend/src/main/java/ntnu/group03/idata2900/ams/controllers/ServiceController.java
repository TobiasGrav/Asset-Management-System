package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.ServiceDto;
import ntnu.group03.idata2900.ams.model.Service;
import ntnu.group03.idata2900.ams.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    private static final String SERVICE_NOT_FOUND = "Service not found with id: {}";

    /**
     * Creates a new instance of ServiceController.
     *
     * @param serviceService serviceService
     */
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * Returns list of all services in database
     *
     * @return List of all services in database
     */
    @GetMapping
    public List<Service> getAll() {
        return serviceService.getAll();
    }

    /**
     * Get a service from database matching given id if it exists.
     *
     * @param id potential id of a service
     * @return a ModelAndView containing service in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<Service> getService(@PathVariable int id) {
        Optional<Service> service = this.serviceService.getService(id);
        if (service.isEmpty()) {
            log.warn(SERVICE_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Service found with ID: {}", id);
            return new ResponseEntity<>(service.get(), HttpStatus.OK);
        }
    }


    /**
     * Creates a new service.
     *
     * @param service The service object to be created.
     * @return ResponseEntity containing the created service and HTTP status code 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody ServiceDto service) {
        try {
            log.error(service.getDescription());
            log.error(service.getIntervalName());
            log.error(service.getAsset().toString());
            Service createdService = serviceService.createService(service);
            log.info("Service created with ID: {}", createdService.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (Exception e) {
            log.error("Error creating service", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing service.
     *
     * @param id             The ID of the service to be updated.
     * @param updatedService The updated service object.
     * @return ResponseEntity containing the updated service (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the service with the given ID doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable int id, @RequestBody ServiceDto updatedService) {
        Optional<Service> existingService = serviceService.getService(id);
        if (existingService.isEmpty()) {
            log.warn(SERVICE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Service serviceToUpdate = existingService.get();
            serviceToUpdate.setDescription(updatedService.getDescription());
            serviceToUpdate.setIntervalName(updatedService.getIntervalName());
            serviceToUpdate.setAsset(updatedService.getAsset());
            serviceService.updateService(serviceToUpdate);
            log.info("Service updated with ID: {}", id);
            return new ResponseEntity<>(serviceToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a service.
     *
     * @param id The ID of the service to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the service with the given ID doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Service> deleteService(@PathVariable int id) {
        Optional<Service> existingService = serviceService.getService(id);
        if (existingService.isEmpty()) {
            log.warn(SERVICE_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            serviceService.deleteService(id);
            log.info("Service deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
