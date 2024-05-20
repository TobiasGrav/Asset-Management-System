package ntnu.group03.idata2900.ams.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ntnu.group03.idata2900.ams.dto.CompanyDto;
import ntnu.group03.idata2900.ams.model.Company;
import ntnu.group03.idata2900.ams.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing companies.
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructor for CompanyService.
     *
     * @param companyRepository companyRepository
     */
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Fetches all Company entities from the database.
     *
     * @return a list of all Company.
     */
    public List<Company> getAll() {
        List<Company> companies = new LinkedList<>();
        this.companyRepository.findAll().forEach(companies::add);
        return companies;
    }

    /**
     * Fetches a Company by ID.
     *
     * @param id the ID of the Company.
     * @return an Optional containing the Company if found, or an empty Optional if not.
     */
    public Optional<Company> getCompany(int id) {
        return this.companyRepository.findById(id);
    }

    /**
     * Creates a new Company entity based on the given DTO.
     *
     * @param companyDto the DTO containing data for the new Company.
     * @return the created Company.
     */
    public Company createCompany(CompanyDto companyDto) {
        Company newCompany = new Company(companyDto);
        return this.companyRepository.save(newCompany);
    }

    /**
     * Updates an existing Company.
     *
     * @param company the Company to update.
     */
    public void updateCompany(Company company) {
        this.companyRepository.save(company);
    }

    /**
     * Deletes a Company by ID.
     *
     * @param id the ID of the Company to delete.
     */
    public void deleteCompany(int id) {
        // TODO: Should be disabled and not deleted
        //Optional<Company> company = companyRepository.findById(id);
        //company.ifPresent(companyRepository::save);
        this.companyRepository.deleteById(id);
    }
}
