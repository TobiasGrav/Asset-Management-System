package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.AssetOnSiteService;
import ntnu.group03.idata2900.ams.services.ServiceCompletedService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Service Completed API", description = "Endpoints for managing completed services")
public class ServiceCompletedController {

    private final ServiceCompletedService serviceCompletedService;
    private final UserService userService;
    private final AssetOnSiteService assetOnSiteService;

    private static final String SERVICE_COMPLETED_NOT_FOUND = "ServiceCompleted not found with id: {}";

    /**
     * Creates a new instance of ServiceCompletedController.
     *
     * @param serviceCompletedService serviceCompletedService
     * @param userService userService
     * @param assetOnSiteService assetOnSiteService
     */
    public ServiceCompletedController(ServiceCompletedService serviceCompletedService, UserService userService, AssetOnSiteService assetOnSiteService) {
        this.serviceCompletedService = serviceCompletedService;
        this.userService = userService;
        this.assetOnSiteService = assetOnSiteService;
    }

    /**
     * Returns list of all completedServices in database
     *
     * @return List of all completedServices in database
     */
    @Operation(summary = "Get all completed services", description = "Retrieves a list of all completed services.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @GetMapping("/admin/servicesCompleted")
    public List<ServiceCompleted> getAll() {
        return serviceCompletedService.getAll();
    }

    /**
     * Returns list of all completedServices in database
     *
     * @return Set of all completedServices in database
     */
    @Operation(summary = "Get all completed services by technician", description = "Retrieves a set of all completed services for the currently authenticated technician.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/technician/servicesCompleted")
    public ResponseEntity<Set<ServiceCompleted>> getAllByTechnician() {
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
     * Returns set of all completedServices for given asset on site
     *
     * @return Set of all completedServices for given asset on site
     */
    @Operation(summary = "Get all completed services by asset on site", description = "Retrieves a set of all completed services for a given asset on site.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "404", description = "Asset on site not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user/assetsOnSite/{id}/servicesCompleted")
    public ResponseEntity<Set<ServiceCompleted>> getAllByAssetOnSite(@PathVariable int id) {
        Optional<AssetOnSite> assetOnSite = this.assetOnSiteService.getAssetOnSite(id);
        User user = userService.getSessionUser();
        if (assetOnSite.isEmpty()){
            log.warn("Asset on site not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!userService.hasAccessToAllServiceCompletedOnSite(user, id)){
            log.warn("User with ID {} does not have authority to access this asset on site", user.getId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("Services completed found with ID: {}",id);
        return new ResponseEntity<>(assetOnSite.get().getServicesCompleted(), HttpStatus.OK);
    }

    /**
     * Get a serviceCompleted from database matching given id if it exists.
     *
     * @param id potential id of a serviceCompleted
     * @return a ModelAndView containing serviceCompleted in JSON format
     */
    @Operation(summary = "Get completed service by ID for technician", description = "Retrieves a completed service based on the provided ID for the currently authenticated technician.")
    @ApiResponse(responseCode = "200", description = "Service completed found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
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
     * Get a serviceCompleted from database matching given id if it exists.
     *
     * @param id potential id of a serviceCompleted
     * @return a ModelAndView containing serviceCompleted in JSON format
     */
    @Operation(summary = "Get completed service by ID for user", description = "Retrieves a completed service based on the provided ID for the currently authenticated user.")
    @ApiResponse(responseCode = "200", description = "Service completed found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user/servicesCompleted/{id}")
    public ResponseEntity<ServiceCompleted> getServiceCompletedForUser(@PathVariable int id) {
        Optional<ServiceCompleted> serviceCompleted = this.serviceCompletedService.getServiceCompleted(id);
        User user = userService.getSessionUser();

        if (serviceCompleted.isEmpty()) {
            log.warn(SERVICE_COMPLETED_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!userService.hasAccessToAssetOnSite(user, serviceCompleted.get().getAssetOnSite().getSite().getId(), serviceCompleted.get().getAssetOnSite().getId())){
            log.warn("User with name {} does not have authority to access services completed", user.getFirstName() + " " + user.getLastName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info("ServiceCompleted found with ID: {}", id);
        return new ResponseEntity<>(serviceCompleted.get(), HttpStatus.OK);
    }

    /**
     * Get a serviceCompleted from database matching given id if it exists.
     *
     * @param id potential id of a serviceCompleted
     * @return a ModelAndView containing serviceCompleted in JSON format
     */
    @Operation(summary = "Get completed service by ID for admin", description = "Retrieves a completed service based on the provided ID for the admin.")
    @ApiResponse(responseCode = "200", description = "Service completed found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @GetMapping("/admin/servicesCompleted/{id}")
    public ResponseEntity<ServiceCompleted> getServiceCompletedForAdmin(@PathVariable int id) {
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
    @Operation(summary = "Create a new completed service", description = "Creates a new completed service.")
    @ApiResponse(responseCode = "201", description = "Service completed created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
    @PostMapping("/user/servicesCompleted")
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
    @Operation(summary = "Update the completion date of a service", description = "Updates the completion date of an existing service.")
    @ApiResponse(responseCode = "200", description = "Service completed updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "404", description = "Service completed not found")
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
    @Operation(summary = "Update the user of a completed service", description = "Updates the user associated with an existing completed service.")
    @ApiResponse(responseCode = "200", description = "Service completed updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCompleted.class)))
    @ApiResponse(responseCode = "404", description = "Service completed or user not found")
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
    @Operation(summary = "Delete a completed service", description = "Deletes a completed service based on the provided ID.")
    @ApiResponse(responseCode = "204", description = "Service completed deleted")
    @ApiResponse(responseCode = "404", description = "Service completed not found")
    @DeleteMapping("/admin/servicesCompleted/{id}")
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
