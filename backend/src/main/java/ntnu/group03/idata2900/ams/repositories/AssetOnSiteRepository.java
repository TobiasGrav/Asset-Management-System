package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.AssetOnSite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetOnSiteRepository extends CrudRepository<AssetOnSite, Integer> {
}
