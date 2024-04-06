package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.model.Site;
import ntnu.group03.idata2900.ams.repositories.SiteRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    private final SiteRepository siteRepository;

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

    public Site createSite(Site site){
        return this.siteRepository.save(site);
    }

    public void updateSite(Site site) {
        this.siteRepository.save(site);
    }

    public void deleteSite(int id){
        this.siteRepository.deleteById(id);
    }
}
