package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Integer> {

    Optional<List<Service>> findServicesByAsset(Asset asset);
}
