package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.CompanyDto;
import ntnu.group03.idata2900.ams.dto.SignUpDto;
import ntnu.group03.idata2900.ams.model.Company;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.services.CompanyService;
import ntnu.group03.idata2900.ams.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Company API", description = "Endpoints for managing companies")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    private static final String COMPANY_NOT_FOUND = "Company not found with id: {}";

    /**
     * Creates a new instance of CompanyController.
     *
     * @param companyService companyService
     * @param userService userService
     */
    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    /**
     * Returns list of all companies in database
     *
     * @return List of all companies in database
     */
    @Operation(summary = "Get all companies", description = "Retrieves a list of all companies.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)))
    @GetMapping("/admin/companies")
    public List<Company> getAll() {
        return companyService.getAll();
    }

    /**
     * Get a company from database matching given id if it exists.
     *
     * @param id potential id of a company
     * @return a ModelAndView containing company in JSON format
     */
    @Operation(summary = "Get company by ID", description = "Retrieves a company based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Company found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @GetMapping("/admin/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable int id) {
        Optional<Company> company = this.companyService.getCompany(id);
        if (company.isEmpty()) {
            log.warn(COMPANY_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Company found with ID: {}", id);
            return new ResponseEntity<>(company.get(), HttpStatus.OK);
        }
    }

    /**
     * Returns set of all users in company and additional every technician that is not connected to a company
     *
     * @param id id of company
     * @return returns set of all users in company and all technicians
     */
    @Operation(summary = "Get all users and technicians in company", description = "Retrieves a set of all users in the company and all technicians not connected to any company.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpDto.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @GetMapping("/admin/companies/{id}/users")
    public ResponseEntity<List<SignUpDto>> getAllUsersAndTechniciansInCompany(@PathVariable int id){
        Optional<Company> company = this.companyService.getCompany(id);
        if (company.isEmpty()){
            log.warn(COMPANY_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Set<User> allUsersInCompany = new HashSet<>(company.get().getUsers());
            Set<User> technicianUsers = userService.getUsersByRole("TECHNICIAN");

            allUsersInCompany.addAll(technicianUsers);

            log.info("Company found with ID: {}", id);
            return new ResponseEntity<>(userService.convertAll(allUsersInCompany), HttpStatus.OK);
        }
    }

    /**
     * Returns set of all users in company
     *
     * @param id id of company
     * @return returns set of all users in company
     */
    @Operation(summary = "Get all users in company", description = "Retrieves a set of all users in the company.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpDto.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @GetMapping("/manager/companies/{id}/users")
    public ResponseEntity<List<SignUpDto>> getAllUsersInCompany(@PathVariable int id){
        Optional<Company> company = this.companyService.getCompany(id);
        if (company.isEmpty()){
            log.warn(COMPANY_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Company found with ID: {}", id);
            return new ResponseEntity<>(userService.convertAll(company.get().getUsers()), HttpStatus.OK);
        }
    }

    /**
     * Returns all set of all sites by company
     *
     * @param id company id
     * @return returns all set of all sites by company
     */
    @Operation(summary = "Get all sites for a company", description = "Retrieves a set of all sites associated with the company.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Site.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @GetMapping("/admin/companies/{id}/sites")
    public ResponseEntity<Set<Site>> getAllSitesToCompany(@PathVariable int id){
        Optional<Company> company = this.companyService.getCompany(id);
        if (company.isEmpty()){
            log.warn(COMPANY_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Company found with ID: {}", id);
            return new ResponseEntity<>(company.get().getSites(), HttpStatus.OK);
        }
    }


    /**
     * Creates a new company.
     *
     * @param company The company object to be created.
     * @return ResponseEntity containing the created company and HTTP status code 201 (CREATED).
     */
    @Operation(summary = "Create a new company", description = "Creates a new company.")
    @ApiResponse(responseCode = "201", description = "Company created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
    @PostMapping("/admin/companies")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto company) {
        try {
            Company createdCompany = companyService.createCompany(company);
            log.info("Company created with ID: {}", createdCompany.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
        } catch (Exception e) {
            log.error("Error creating company", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing company.
     *
     * @param id             The ID of the company to be updated.
     * @param updatedCompany The updated company object.
     * @return ResponseEntity containing the updated company (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the company with the given ID doesn't exist.
     */
    @Operation(summary = "Update an existing company", description = "Updates an existing company based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Company updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @PutMapping("/admin/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable int id, @RequestBody CompanyDto updatedCompany) {
        Optional<Company> existingCompany = companyService.getCompany(id);
        if (existingCompany.isEmpty()) {
            log.warn(COMPANY_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Company companyToUpdate = existingCompany.get();
            companyToUpdate.setName(updatedCompany.getName());
            companyService.updateCompany(companyToUpdate);
            log.info("Company updated with ID: {}", id);
            return new ResponseEntity<>(companyToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a company.
     *
     * @param id The ID of the company to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the company with the given ID doesn't exist.
     */
    @Operation(summary = "Delete a company", description = "Deletes a company based on the provided ID.")
    @ApiResponse(responseCode = "204", description = "Company deleted")
    @ApiResponse(responseCode = "404", description = "Company not found")
    @DeleteMapping("/admin/companies/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable int id) {
        Optional<Company> existingCompany = companyService.getCompany(id);
        if (existingCompany.isEmpty()) {
            log.warn(COMPANY_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            companyService.deleteCompany(id);
            log.info("Company deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
