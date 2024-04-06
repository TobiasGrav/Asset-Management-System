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

@Service
public class SiteService {

    private final SiteRepository siteRepository;

    @PersistenceContext
    EntityManager entityManager;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public List<Site> getAll() {
        List<Site> sites = new LinkedList<>();
        this.siteRepository.findAll().forEach(sites::add);
        return sites;
    }

    public Optional<Site> getSite(int id){
        return this.siteRepository.findById(id);
    }

    public Site createSite(SiteDto siteDto){
        Site site = new Site(siteDto);
        // Need to use entityManager to make sure the company is in the database already and fetched in the correct session
        Company company = entityManager.find(Company.class, siteDto.getCompany().getId());
        site.setCompany(company);
        return this.siteRepository.save(site);
    }

    public void updateSite(Site site) {
        this.siteRepository.save(site);
    }

    public void deleteSite(int id){
        // Delete won't work due to foreign key constraint between site and site_users. Site should only be deactivated, not deleted.
        Site site = this.entityManager.find(Site.class, id);
        site.setActive(false);
        this.siteRepository.save(site);
        //this.siteRepository.deleteById(id);
    }
}
