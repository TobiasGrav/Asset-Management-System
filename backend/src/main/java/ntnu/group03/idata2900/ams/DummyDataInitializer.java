package ntnu.group03.idata2900.ams;

import ntnu.group03.idata2900.ams.model.*;
import ntnu.group03.idata2900.ams.repositories.*;
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

    private final AssetRepository assetRepository;

    private final DatasheetRepository datasheetRepository;

    private final CategoryRepository  categoryRepository;

    private final SiteRepository siteRepository;

    private final Logger logger = LoggerFactory.getLogger("DummyInit");

    /**
     * Creates a new instance of DummyDataInitializer.
     *
     * @param userRepository        The repository for managing user       entities.
     * @param roleRepository        The repository for managing role       entities.
     * @param assetRepository       The repository for managing asset      entities.
     * @param datasheetRepository   The repository for managing datasheet  entities.
     * @param categoryRepository    The repository for managing category   entities.
     * @param siteRepository        The repository for managing site       entities.
     */
    public DummyDataInitializer(UserRepository userRepository, RoleRepository roleRepository, AssetRepository assetRepository, DatasheetRepository datasheetRepository, CategoryRepository categoryRepository, SiteRepository siteRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.assetRepository = assetRepository;
        this.datasheetRepository = datasheetRepository;
        this.categoryRepository = categoryRepository;
        this.siteRepository = siteRepository;
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        logger.info("Importing test data...");

        if (userRepository.count() == 0){


            Role user = new Role("USER");
            Role admin = new Role("ADMIN");

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


            Category category1 = new Category("Pump");
            Category category2 = new Category("Vent");
            Category category3 = new Category("Filter");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);

            Datasheet datasheet1 = new Datasheet(
                    "Pump",
                    "1234",
                    "https://localhost:8080/fileserver/pump1234");
            Datasheet datasheet2 = new Datasheet(
                    "Vent",
                    "2345",
                    "https://localhost:8080/fileserver/vent2345");
            Datasheet datasheet3 = new Datasheet(
                    "Filter",
                    "3456",
                    "https://localhost:8080/fileserver/filter3456");

            datasheetRepository.save(datasheet1);
            datasheetRepository.save(datasheet2);
            datasheetRepository.save(datasheet3);

            Site site1 = new Site("Nordland", "#1234");
            Site site2 = new Site("Bodø", "#2345");
            Site site3 = new Site("Ålesund", "#3456");

            siteRepository.save(site1);
            siteRepository.save(site2);
            siteRepository.save(site3);


            Asset asset1 = new Asset(
                    "Pump",
                    "A random pump",
                    LocalDateTime.now());

            Asset asset2 = new Asset(
                    "Vent",
                    "A random vent",
                    LocalDateTime.now());

            Asset asset3 = new Asset(
                    "Filter",
                    "A random filter",
                    LocalDateTime.now());

            Asset asset4 = new Asset(
                    "Pump123",
                    "A random pump321",
                    LocalDateTime.now());

            Asset asset5 = new Asset(
                    "Vent3333",
                    "A random vent2222",
                    LocalDateTime.now());

            Asset asset6 = new Asset(
                    "Filter6565",
                    "A random filter5455",
                    LocalDateTime.now());

            Asset asset7 = new Asset(
                    "Pump656565",
                    "A random pump656565",
                    LocalDateTime.now());

            Asset asset8 = new Asset(
                    "Vent321321321",
                    "A random vent12312321",
                    LocalDateTime.now());

            Asset asset9 = new Asset(
                    "Filter321321321",
                    "A random filter321321321",
                    LocalDateTime.now());

            Asset asset10 = new Asset(
                    "Pump454545",
                    "A random pump321321321",
                    LocalDateTime.now());

            Asset asset11 = new Asset(
                    "Vent54654654",
                    "A random vent654654654",
                    LocalDateTime.now());

            Asset asset12 = new Asset(
                    "Filter654654",
                    "A random filter5454665",
                    LocalDateTime.now());

            asset1.setCategory(category1);
            asset2.setCategory(category2);
            asset3.setCategory(category3);
            asset4.setCategory(category1);
            asset5.setCategory(category2);
            asset6.setCategory(category3);
            asset7.setCategory(category1);
            asset8.setCategory(category2);
            asset9.setCategory(category3);
            asset10.setCategory(category1);
            asset11.setCategory(category2);
            asset12.setCategory(category3);

            asset1.setDatasheet(datasheet1);
            asset2.setDatasheet(datasheet2);
            asset3.setDatasheet(datasheet3);
            asset4.setDatasheet(datasheet1);
            asset5.setDatasheet(datasheet2);
            asset6.setDatasheet(datasheet3);
            asset7.setDatasheet(datasheet1);
            asset8.setDatasheet(datasheet2);
            asset9.setDatasheet(datasheet3);
            asset10.setDatasheet(datasheet1);
            asset11.setDatasheet(datasheet2);
            asset12.setDatasheet(datasheet3);

            assetRepository.save(asset1);
            assetRepository.save(asset2);
            assetRepository.save(asset3);
            assetRepository.save(asset4);
            assetRepository.save(asset5);
            assetRepository.save(asset6);
            assetRepository.save(asset7);
            assetRepository.save(asset8);
            assetRepository.save(asset9);
            assetRepository.save(asset10);
            assetRepository.save(asset11);
            assetRepository.save(asset12);


            logger.info("DONE importing test data");
        } else {
            logger.info("Database already populated.");
        }
    }
}
