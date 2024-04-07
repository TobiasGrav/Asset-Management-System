package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.CompanyDto;
import ntnu.group03.idata2900.ams.model.Company;
import ntnu.group03.idata2900.ams.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    private static final String COMPANY_NOT_FOUND = "Company not found with id: {}";

    /**
     * Creates a new instance of CompanyController.
     *
     * @param companyService companyService
     */
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Returns list of all companies in database
     *
     * @return List of all companies in database
     */
    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    /**
     * Get a company from database matching given id if it exists.
     *
     * @param id potential id of a company
     * @return a ModelAndView containing company in JSON format
     */
    @GetMapping("/{id}")
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
     * Creates a new company.
     *
     * @param company The company object to be created.
     * @return ResponseEntity containing the created company and HTTP status code 201 (CREATED).
     */
    @PostMapping
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
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
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
