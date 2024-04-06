package ntnu.group03.idata2900.ams;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationEvent> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AssetRepository assetRepository;

    private final DatasheetRepository datasheetRepository;

    private final CategoryRepository  categoryRepository;

    private final SiteRepository siteRepository;

    private final AssetOnSiteRepository assetOnSiteRepository;

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
    public DummyDataInitializer(UserRepository userRepository, RoleRepository roleRepository, AssetRepository assetRepository, DatasheetRepository datasheetRepository,
                                CategoryRepository categoryRepository, SiteRepository siteRepository, AssetOnSiteRepository assetOnSiteRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.assetRepository = assetRepository;
        this.datasheetRepository = datasheetRepository;
        this.categoryRepository = categoryRepository;
        this.siteRepository = siteRepository;
        this.assetOnSiteRepository = assetOnSiteRepository;
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        log.info("Importing test data...");

        if (userRepository.count() == 0){

            // Setting up roles
            Role user = new Role("USER");
            Role admin = new Role("ADMIN");

            Set<Role> setUserAdmin = new HashSet<>();
            Set<Role> setUserOnly = new HashSet<>();
            setUserAdmin.add(user);
            setUserAdmin.add(admin);

            setUserOnly.add(user);

            roleRepository.save(user);
            roleRepository.save(admin);

            // Setting up test users
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

            // Setting up site
            Site site1 = new Site("Nordlandsbåten");
            Site site2 = new Site("Bodøbåten");
            Site site3 = new Site("Ålesundbåten");

            Set<Site> setAllSites = new HashSet<>();
            Set<Site> setSiteUsers1 = new HashSet<>();

            setAllSites.add(site1);
            setAllSites.add(site2);
            setAllSites.add(site3);

            setSiteUsers1.add(site1);

            Company company1 = new Company("CFlow");
            Company company2 = new Company("Facebook");
            Company company3 = new Company("NTNU");

            site1.setCompany(company1);
            site2.setCompany(company2);
            site3.setCompany(company3);

            siteRepository.save(site1);
            siteRepository.save(site2);
            siteRepository.save(site3);

            jon.setSites(setAllSites);

            jenny.setSites(setSiteUsers1);



            userRepository.save(jon);
            userRepository.save(jenny);

            // Setting up categories
            Category category1 = new Category("Pump");
            Category category2 = new Category("Vent");
            Category category3 = new Category("Filter");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);

            // Setting up datasheets
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

            // Setting up assets
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


            // Setting up AssetOnSite
            AssetOnSite assetOnSite1 = new AssetOnSite();
            AssetOnSite assetOnSite2 = new AssetOnSite();
            AssetOnSite assetOnSite3 = new AssetOnSite();
            AssetOnSite assetOnSite4 = new AssetOnSite();
            AssetOnSite assetOnSite5 = new AssetOnSite();
            AssetOnSite assetOnSite6 = new AssetOnSite();
            AssetOnSite assetOnSite7 = new AssetOnSite();
            AssetOnSite assetOnSite8 = new AssetOnSite();
            AssetOnSite assetOnSite9 = new AssetOnSite();

            assetOnSite1.setAsset(asset1);
            assetOnSite2.setAsset(asset2);
            assetOnSite3.setAsset(asset3);
            assetOnSite4.setAsset(asset4);
            assetOnSite5.setAsset(asset5);
            assetOnSite6.setAsset(asset6);
            assetOnSite7.setAsset(asset7);
            assetOnSite8.setAsset(asset8);
            assetOnSite9.setAsset(asset9);

            assetOnSite1.setSite(site1);
            assetOnSite2.setSite(site1);
            assetOnSite3.setSite(site1);
            assetOnSite4.setSite(site2);
            assetOnSite5.setSite(site2);
            assetOnSite6.setSite(site2);
            assetOnSite7.setSite(site3);
            assetOnSite8.setSite(site3);
            assetOnSite9.setSite(site3);

            assetOnSiteRepository.save(assetOnSite1);
            assetOnSiteRepository.save(assetOnSite2);
            assetOnSiteRepository.save(assetOnSite3);
            assetOnSiteRepository.save(assetOnSite4);
            assetOnSiteRepository.save(assetOnSite5);
            assetOnSiteRepository.save(assetOnSite6);
            assetOnSiteRepository.save(assetOnSite7);
            assetOnSiteRepository.save(assetOnSite8);
            assetOnSiteRepository.save(assetOnSite9);




            log.info("DONE importing test data");
        } else {
            log.info("Database already populated.");
        }
    }
}
