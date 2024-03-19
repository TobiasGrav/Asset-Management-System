package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Retrieves a user from the database based on the provided email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the user with the given email address, or empty if no user is found.
     */
    Optional<User> findByEmail(String email);
}
