package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.Role;
import ntnu.group03.idata2900.ams.model.Site;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends CrudRepository<Site, Integer> {

    /**
     * Retrieves a site from the database based on the provided site name.
     *
     * @param siteName The name of the site.
     * @return An Optional containing the site with the given name, or empty if no site is found.
     */
    Optional<Site> findByName(String siteName);

}
