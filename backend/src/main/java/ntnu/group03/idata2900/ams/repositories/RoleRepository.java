package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    /**
     * Retrieves a role from the database based on the provided role name.
     *
     * @param roleName The name of the role.
     * @return An Optional containing the role with the given name, or empty if no role is found.
     */
    Optional<Role> findByName(String roleName);

}
