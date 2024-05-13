package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.ServiceCompletedService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ServiceCompletedController {

    private final ServiceCompletedService serviceCompletedService;
    private final UserService userService;

    private static final String SERVICE_COMPLETED_NOT_FOUND = "ServiceCompleted not found with id: {}";

    /**
     * Creates a new instance of ServiceCompletedController.
     *
     * @param serviceCompletedService serviceCompletedService
     * @param userService userService
     */
    public ServiceCompletedController(ServiceCompletedService serviceCompletedService, UserService userService) {
        this.serviceCompletedService = serviceCompletedService;
        this.userService = userService;
    }

    /**
     * Returns list of all completedServices in database
     *
     * @return List of all completedServices in database
     */
    @GetMapping("/admin/servicesCompleted")
    public List<ServiceCompleted> getAll() {
        return serviceCompletedService.getAll();
    }

    /**
     * Returns list of all completedServices in database
     *
     * @return List of all completedServices in database
     */
    @GetMapping("/technician/servicesCompleted")
    public ResponseEntity<Set<ServiceCompleted>> getAllByUser() {
        User user = userService.getSessionUser();
        if (userService.hasAccessToServiceCompleted(user, "TECHNICIAN")){
            log.info("Services completed found with user name: {}", user.getFirstName() + " " + user.getLastName());
            return new ResponseEntity<>(user.getServicesCompleted(), HttpStatus.OK);
        } else {
            log.warn("User with name {} does not have authority to access services completed", user.getFirstName() + " " + user.getLastName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Get a serviceCompleted from database matching given id if it exists.
     *
     * @param id potential id of a serviceCompleted
     * @return a ModelAndView containing serviceCompleted in JSON format
     */
    @GetMapping("/technician/servicesCompleted/{id}")
    public ResponseEntity<ServiceCompleted> getServiceCompleted(@PathVariable int id) {
        Optional<ServiceCompleted> serviceCompleted = this.serviceCompletedService.getServiceCompleted(id);
        User user = userService.getSessionUser();
        if (userService.hasAccessToGivenServiceCompleted(user, id)){
            log.warn("User with name {} does not have authority to access services completed", user.getFirstName() + " " + user.getLastName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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
    @PostMapping("/servicesCompleted")
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
     * Updates an existing serviceCompleted date.
     *
     * @param id             The ID of the serviceCompleted to be updated.
     * @return ResponseEntity containing the updated serviceCompleted (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the serviceCompleted with the given ID doesn't exist.
     */
    @PutMapping("/technician/servicesCompleted/{id}")
    public ResponseEntity<ServiceCompleted> updateServiceCompletedDate(@PathVariable int id) {
        Optional<ServiceCompleted> existingServiceCompleted = serviceCompletedService.getServiceCompleted(id);
        if (existingServiceCompleted.isEmpty()) {
            log.warn(SERVICE_COMPLETED_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ServiceCompleted serviceCompletedToUpdate = existingServiceCompleted.get();
            serviceCompletedToUpdate.setTimeCompleted(LocalDateTime.now());
            serviceCompletedService.updateServiceCompleted(serviceCompletedToUpdate);
            log.info("ServiceCompleted updated with ID: {}", id);
            return new ResponseEntity<>(serviceCompletedToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Updates an existing serviceCompleted user.
     *
     * @param id             The ID of the serviceCompleted to be updated.
     * @param userId         The ID of the user to be set
     * @return ResponseEntity containing the updated serviceCompleted (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the serviceCompleted with the given ID doesn't exist.
     */
    @PutMapping("/admin/servicesCompleted/{id}/users/{userId}")
    public ResponseEntity<ServiceCompleted> updateServiceCompletedUser(@PathVariable int id, @PathVariable int userId) {
        Optional<ServiceCompleted> existingServiceCompleted = serviceCompletedService.getServiceCompleted(id);
        Optional<User> user = userService.getUserById(userId);
        if (existingServiceCompleted.isEmpty()) {
            log.warn(SERVICE_COMPLETED_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (user.isEmpty()){
            log.warn("User not found with ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ServiceCompleted serviceCompletedToUpdate = existingServiceCompleted.get();
            serviceCompletedToUpdate.setUser(user.get());
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
    @DeleteMapping("/servicesCompleted/{id}")
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
