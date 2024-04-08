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

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        List<Company> companies = new LinkedList<>();
        this.companyRepository.findAll().forEach(companies::add);
        return companies;
    }

    public Optional<Company> getCompany(int id){
        return this.companyRepository.findById(id);
    }

    public Company createCompany(CompanyDto companyDto){
        Company newCompany = new Company(companyDto);
        return this.companyRepository.save(newCompany);
    }

    public void updateCompany(Company company) {
        this.companyRepository.save(company);
    }

    public void deleteCompany(int id){
        // TODO: Should be disabled and not deleted
        //Optional<Company> company = companyRepository.findById(id);
        //company.ifPresent(companyRepository::save);
        this.companyRepository.deleteById(id);
    }
}
