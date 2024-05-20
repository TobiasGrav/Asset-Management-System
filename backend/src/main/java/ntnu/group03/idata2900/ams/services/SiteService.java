package ntnu.group03.idata2900.ams.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ntnu.group03.idata2900.ams.dto.SiteDto;
import ntnu.group03.idata2900.ams.model.Company;
import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.repositories.SiteRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing sites.
 */
@Service
public class SiteService {

    private final SiteRepository siteRepository;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Constructor for SiteService.
     *
     * @param siteRepository siteRepository
     */
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    /**
     * Fetches all Site entities from the database.
     *
     * @return a list of all Site.
     */
    public List<Site> getAll() {
        List<Site> sites = new LinkedList<>();
        this.siteRepository.findAll().forEach(sites::add);
        return sites;
    }

    /**
     * Fetches a Site by ID.
     *
     * @param id the ID of the Site.
     * @return an Optional containing the Site if found, or an empty Optional if not.
     */
    public Optional<Site> getSite(int id) {
        return this.siteRepository.findById(id);
    }

    /**
     * Creates a new Site entity based on the given DTO.
     *
     * @param siteDto the DTO containing data for the new Site.
     * @return the created Site.
     */
    public Site createSite(SiteDto siteDto) {
        Site site = new Site(siteDto);
        // Need to use entityManager to make sure the company is in the database already and fetched in the correct session
        Company company = entityManager.find(Company.class, siteDto.getCompany().getId());
        site.setCompany(company);
        return this.siteRepository.save(site);
    }

    /**
     * Updates an existing Site.
     *
     * @param site the Site to update.
     */
    public void updateSite(Site site) {
        this.siteRepository.save(site);
    }

    /**
     * Deletes a Site by ID.
     *
     * @param id the ID of the Site to delete.
     */
    public void deleteSite(int id) {
        // Delete won't work due to foreign key constraint between site and site_users. Site should only be deactivated, not deleted.
        Site site = this.entityManager.find(Site.class, id);
        site.setActive(false);
        this.siteRepository.save(site);
        //this.siteRepository.deleteById(id);
    }
}