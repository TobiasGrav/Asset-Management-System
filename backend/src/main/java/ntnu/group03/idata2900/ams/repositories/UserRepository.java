package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Retrieves a user from the database based on the provided email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the user with the given email address, or empty if no user is found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds role by name and returns a set of all users with that role name
     *
     * @param name name of the role
     * @return returns a set of users with given role name
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :name")
    Set<User> findByRoleName(@Param("name") String name);

}
