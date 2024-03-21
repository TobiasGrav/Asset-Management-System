package ntnu.group03.idata2900.ams;

import ntnu.group03.idata2900.ams.model.Role;
import ntnu.group03.idata2900.ams.model.User;
import ntnu.group03.idata2900.ams.repositories.RoleRepository;
import ntnu.group03.idata2900.ams.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for populating database with dummy data for testing
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationEvent> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final Logger logger = LoggerFactory.getLogger("DummyInit");

    /**
     * Creates a new instance of DummyDataInitializer.
     *
     * @param userRepository    The repository for managing user entities.
     * @param roleRepository    The repository for managing role entities.
     */
    public DummyDataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        logger.info("Importing test data...");

        if (userRepository.count() == 0){


            Role user = new Role("user");
            Role admin = new Role("admin");

            Set<Role> setUserAdmin = new HashSet<>();
            Set<Role> setUserOnly = new HashSet<>();
            setUserAdmin.add(user);
            setUserAdmin.add(admin);

            setUserOnly.add(user);

            roleRepository.save(user);
            roleRepository.save(admin);

            // Test users
            User jon = new User(
                    "CompanyID#01",
                    "GroupID#01",
                    "Jon",
                    "Smith",
                    "Jons@ntnu.no",
                    BCrypt.hashpw("IDATA2024isbased", BCrypt.gensalt()),
                    "12345678",
                    LocalDateTime.now());

            jon.setRoles(setUserAdmin);
            userRepository.save(jon);

            User jenny = new User(
                    "CompanyID#02",
                    "GroupID#01",
                    "Jenny",
                    "Dow",
                    "Jend@ntnu.no",
                    BCrypt.hashpw("12RulesForLife", BCrypt.gensalt()),
                    "12345678",
                    LocalDateTime.now());

            jenny.setRoles(setUserOnly);

            userRepository.save(jenny);

            logger.info("DONE importing test data");
        } else {
            logger.info("Database already populated.");
        }
    }
}
