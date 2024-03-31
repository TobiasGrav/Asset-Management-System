package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
