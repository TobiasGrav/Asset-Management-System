package ntnu.group03.idata2900.ams;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.model.*;
import ntnu.group03.idata2900.ams.repositories.*;
import ntnu.group03.idata2900.ams.util.SecurityAccessUtil;
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

    private final ServiceRepository serviceRepository;

    private final CommentRepository commentRepository;

    private final ServiceCompletedRepository serviceCompletedRepository;

    private final SparePartRepository sparePartRepository;

    private final ServiceCommentRepository serviceCommentRepository;

    /**
     *
     * Creates a new instance of DummyDataInitializer.
     *
     * @param userRepository                The repository for managing user                entities.
     * @param roleRepository                The repository for managing role                entities.
     * @param assetRepository               The repository for managing asset               entities.
     * @param datasheetRepository           The repository for managing datasheet           entities.
     * @param categoryRepository            The repository for managing category            entities.
     * @param siteRepository                The repository for managing site                entities.
     * @param assetOnSiteRepository         The repository for managing assetOnSite         entities.
     * @param serviceRepository             The repository for managing service             entities.
     * @param commentRepository             The repository for managing comment             entities.
     * @param serviceCompletedRepository    The repository for managing serviceCompleted    entities.
     * @param sparePartRepository           The repository for managing sparePart           entities.
     * @param serviceCommentRepository      The repository for managing serviceComment      entities.
     */
    public DummyDataInitializer(UserRepository userRepository, RoleRepository roleRepository, AssetRepository assetRepository, DatasheetRepository datasheetRepository,
                                CategoryRepository categoryRepository, SiteRepository siteRepository, AssetOnSiteRepository assetOnSiteRepository, ServiceRepository serviceRepository,
                                CommentRepository commentRepository, ServiceCompletedRepository serviceCompletedRepository, SparePartRepository sparePartRepository, ServiceCommentRepository serviceCommentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.assetRepository = assetRepository;
        this.datasheetRepository = datasheetRepository;
        this.categoryRepository = categoryRepository;
        this.siteRepository = siteRepository;
        this.assetOnSiteRepository = assetOnSiteRepository;
        this.serviceRepository = serviceRepository;
        this.commentRepository = commentRepository;
        this.serviceCompletedRepository = serviceCompletedRepository;
        this.sparePartRepository = sparePartRepository;
        this.serviceCommentRepository = serviceCommentRepository;
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        log.info("Importing test data...");

        if (userRepository.count() == 0){

            // Setting up roles
            Role user = new Role(SecurityAccessUtil.USER);
            Role admin = new Role(SecurityAccessUtil.ADMIN);

            Set<Role> setUserAdmin = new HashSet<>();
            Set<Role> setUserOnly = new HashSet<>();
            setUserAdmin.add(user);
            setUserAdmin.add(admin);

            setUserOnly.add(user);

            roleRepository.save(user);
            roleRepository.save(admin);

            // Setting up test users
            User jon = new User(
                    "Jon",
                    "Smith",
                    "Jons@cflow.no",
                    BCrypt.hashpw("IDATA2024isbased", BCrypt.gensalt()),
                    "24865413",
                    LocalDateTime.now());

            jon.setRoles(setUserAdmin);

            User jenny = new User(
                    "Jenny",
                    "Dow",
                    "Jend@ntnu.no",
                    BCrypt.hashpw("12RulesForLife", BCrypt.gensalt()),
                    "54321547",
                    LocalDateTime.now());

            jenny.setRoles(setUserOnly);

            User mark = new User(
                    "Mark",
                    "Zuckerberg",
                    "bigboss@meta.com",
                    BCrypt.hashpw("1234", BCrypt.gensalt()),
                    "32156646",
                    LocalDateTime.now());

            mark.setRoles(setUserOnly);

            User gunnar = new User(
                    "Gunnar",
                    "Grav",
                    "gunnar@meta.com",
                    BCrypt.hashpw("1234", BCrypt.gensalt()),
                    "94561256",
                    LocalDateTime.now());

            gunnar.setRoles(setUserOnly);

            User bob = new User(
                    "Bob",
                    "Bobbingson",
                    "bob@meta.com",
                    BCrypt.hashpw("1234", BCrypt.gensalt()),
                    "75654125",
                    LocalDateTime.now());

            bob.setRoles(setUserOnly);

            User alice = new User(
                    "Alice",
                    "Waters",
                    "alice@ntnu.no",
                    BCrypt.hashpw("SecurePass123!", BCrypt.gensalt()),
                    "34865987",
                    LocalDateTime.now());
            alice.setRoles(setUserAdmin);

            User tom = new User(
                    "Tom",
                    "Hardy",
                    "tom@meta.com",
                    BCrypt.hashpw("BetterPassword456?", BCrypt.gensalt()),
                    "15648975",
                    LocalDateTime.now());
            tom.setRoles(setUserOnly);

            User maria = new User(
                    "Maria",
                    "Velasquez",
                    "maria@meta.com",
                    BCrypt.hashpw("Password789$", BCrypt.gensalt()),
                    "45612378",
                    LocalDateTime.now());
            maria.setRoles(setUserOnly);

            User nick = new User(
                    "Nick",
                    "Johnson",
                    "nick@cflow.no",
                    BCrypt.hashpw("UltraSecure0!", BCrypt.gensalt()),
                    "98234567",
                    LocalDateTime.now());
            nick.setRoles(setUserAdmin);

            User lily = new User(
                    "Lily",
                    "Evans",
                    "lily@ntnu.no",
                    BCrypt.hashpw("Lifeisgood123", BCrypt.gensalt()),
                    "12345678",
                    LocalDateTime.now());
            lily.setRoles(setUserOnly);

            User carl = new User(
                    "Carl",
                    "Mason",
                    "carl@cflow.no",
                    BCrypt.hashpw("RememberMe1234", BCrypt.gensalt()),
                    "87654321",
                    LocalDateTime.now());
            carl.setRoles(setUserAdmin);

            User ivy = new User(
                    "Ivy",
                    "Black",
                    "ivy@ntnu.no",
                    BCrypt.hashpw("IvyLeague#1", BCrypt.gensalt()),
                    "23456789",
                    LocalDateTime.now());
            ivy.setRoles(setUserOnly);

            User gary = new User(
                    "Gary",
                    "White",
                    "gary@meta.com",
                    BCrypt.hashpw("Secure!2024", BCrypt.gensalt()),
                    "98765432",
                    LocalDateTime.now());
            gary.setRoles(setUserOnly);

            User elsa = new User(
                    "Elsa",
                    "Morales",
                    "elsa@cflow.no",
                    BCrypt.hashpw("SnowQueen123", BCrypt.gensalt()),
                    "65432198",
                    LocalDateTime.now());
            elsa.setRoles(setUserAdmin);

            User danny = new User(
                    "Danny",
                    "Roberts",
                    "danny@meta.com",
                    BCrypt.hashpw("Doomsday2024", BCrypt.gensalt()),
                    "34567890",
                    LocalDateTime.now());
            danny.setRoles(setUserOnly);

            User zara = new User(
                    "Zara",
                    "Pitt",
                    "zara@ntnu.no",
                    BCrypt.hashpw("ZebraCrossing456", BCrypt.gensalt()),
                    "76543210",
                    LocalDateTime.now());
            zara.setRoles(setUserOnly);

            User hector = new User(
                    "Hector",
                    "Finn",
                    "hector@cflow.no",
                    BCrypt.hashpw("H3inz57Varieties!", BCrypt.gensalt()),
                    "43218765",
                    LocalDateTime.now());
            hector.setRoles(setUserAdmin);

            // Setting up sites
            Site site1 = new Site("Nordlandsbåten");
            Site site2 = new Site("Bodøbåten");
            Site site3 = new Site("Ålesundbåten");

            Set<Site> setAllSites = new HashSet<>();
            Set<Site> setSite1 = new HashSet<>();
            Set<Site> setSite2 = new HashSet<>();
            Set<Site> setSite3 = new HashSet<>();

            setAllSites.add(site1);
            setAllSites.add(site2);
            setAllSites.add(site3);

            setSite1.add(site1);
            setSite2.add(site2);
            setSite3.add(site3);

            // Setting up Companies

            Company company1 = new Company("CFlow");
            Company company2 = new Company("Facebook");
            Company company3 = new Company("NTNU");

            site1.setCompany(company1);
            site2.setCompany(company2);
            site3.setCompany(company3);

            siteRepository.save(site1);
            siteRepository.save(site2);
            siteRepository.save(site3);

            jon.setCompany(company1);
            nick.setCompany(company1);
            carl.setCompany(company1);
            elsa.setCompany(company1);
            hector.setCompany(company1);

            mark.setCompany(company2);
            gunnar.setCompany(company2);
            bob.setCompany(company2);
            tom.setCompany(company2);
            maria.setCompany(company2);
            gary.setCompany(company2);
            danny.setCompany(company2);

            alice.setCompany(company3);
            lily.setCompany(company3);
            jenny.setCompany(company3);
            ivy.setCompany(company3);
            zara.setCompany(company3);

            nick.setSites(setSite1);
            carl.setSites(setSite1);
            elsa.setSites(setSite1);
            hector.setSites(setSite1);

            mark.setSites(setSite2);
            bob.setSites(setSite2);
            tom.setSites(setSite2);
            maria.setSites(setSite2);

            alice.setSites(setSite3);
            ivy.setSites(setSite3);
            jenny.setSites(setSite3);

            userRepository.save(jon);
            userRepository.save(nick);
            userRepository.save(carl);
            userRepository.save(elsa);
            userRepository.save(hector);
            userRepository.save(mark);
            userRepository.save(gunnar);
            userRepository.save(bob);
            userRepository.save(tom);
            userRepository.save(maria);
            userRepository.save(gary);
            userRepository.save(danny);
            userRepository.save(alice);
            userRepository.save(lily);
            userRepository.save(jenny);
            userRepository.save(ivy);
            userRepository.save(zara);



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
            //Asset asset1 = new Asset(
            //        "Pump",
            //        "A random pump",
            //        LocalDateTime.now(),
            //        "12354.3245.2342");
//
            //Asset asset2 = new Asset(
            //        "Vent",
            //        "A random vent",
            //        LocalDateTime.now(),
            //        "1234.567.8.87..54.43");
//
            //Asset asset3 = new Asset(
            //        "Filter",
            //        "A random filter",
            //        LocalDateTime.now(),
            //        "1234.324.23.423.4.234.23");
//
            //Asset asset4 = new Asset(
            //        "Pump123",
            //        "A random pump321",
            //        LocalDateTime.now(),
            //        "1234.423.4.234.32.4.234.23");
//
            //Asset asset5 = new Asset(
            //        "Vent3333",
            //        "A random vent2222",
            //        LocalDateTime.now(),
            //        "1234.765..765.7.567.65.7.65");
//
            //Asset asset6 = new Asset(
            //        "Filter6565",
            //        "A random filter5455",
            //        LocalDateTime.now(),
            //        "1234..765.756.7.65.765.7.65");
//
            //Asset asset7 = new Asset(
            //        "Pump656565",
            //        "A random pump656565",
            //        LocalDateTime.now(),
            //        "12341.7.17.17.17.1.71.71");
//
            //Asset asset8 = new Asset(
            //        "Vent321321321",
            //        "A random vent12312321",
            //        LocalDateTime.now(),
            //        "12344.61.4.3.63.6.34.63.6.43");
//
            //Asset asset9 = new Asset(
            //        "Filter321321321",
            //        "A random filter321321321",
            //        LocalDateTime.now(),
            //        "1234..72.8..87.7.43.534");
//
            //Asset asset10 = new Asset(
            //        "Pump454545",
            //        "A random pump321321321",
            //        LocalDateTime.now(),
            //        "1234..1435.43.5.235.234.52345423");
//
            //Asset asset11 = new Asset(
            //        "Vent54654654",
            //        "A random vent654654654",
            //        LocalDateTime.now(),
            //        "1234..34.5.345.34534.5345.34.534.534");
//
            //Asset asset12 = new Asset(
            //        "Filter654654",
            //        "A random filter5454665",
            //        LocalDateTime.now(),
            //        "12341145.34.5.1345.135435.315.314534");

            // Setting up assets
            Asset asset1 = new Asset(
                    "HydroTech Bilge Pump 3.2L",
                    "Designed for rapid water evacuation from deep compartments, this high-capacity 3.2-liter pump is essential for maintaining the integrity of boat hulls under adverse conditions.",
                    LocalDateTime.now(),
                    "314159");

            Asset asset2 = new Asset(
                    "AeroFlow Ventilation Fan 200W",
                    "This 200-watt heavy-duty fan ensures efficient air circulation throughout enclosed spaces, preventing humidity and gas buildup critical in maintaining a safe maritime environment.",
                    LocalDateTime.now(),
                    "265358");

            Asset asset3 = new Asset(
                    "LubeMaster Oil Filter 15cm",
                    "High-efficiency 15 cm engine oil filter designed to remove contaminants and ensure engine longevity. Essential for high-performance engines.",
                    LocalDateTime.now(),
                    "979323");

            Asset asset4 = new Asset(
                    "AquaPump Water Pump 2.8L",
                    "An industrial-grade 2.8-liter water circulation pump, reliable for managing onboard water systems, supporting everything from cooling systems to hygiene facilities.",
                    LocalDateTime.now(),
                    "597565");

            Asset asset5 = new Asset(
                    "VentTech Air Vent 12cm",
                    "Adjustable 12 cm air vent capable of managing airflow in both intake and exhaust modes, optimized for both efficiency and noise reduction.",
                    LocalDateTime.now(),
                    "846264");

            Asset asset6 = new Asset(
                    "FuelSafe Fuel Filter 10cm",
                    "A 10 cm diesel engine fuel filter, crucial for preventing impurities from damaging engine components, thereby ensuring smooth and efficient fuel flow.",
                    LocalDateTime.now(),
                    "338327");

            Asset asset7 = new Asset(
                    "HydroTech Water Pump 1.5L",
                    "A compact 1.5-liter manual water pump, ideal for emergency situations where power is unavailable, ensuring water access is maintained.",
                    LocalDateTime.now(),
                    "950288");

            Asset asset8 = new Asset(
                    "AeroFlow Ventilation Fan 150W",
                    "A compact 150-watt fan designed for small, enclosed spaces, providing essential ventilation to prevent condensation and maintain air quality.",
                    LocalDateTime.now(),
                    "419716");

            Asset asset9 = new Asset(
                    "ClearWater Water Filter 8cm",
                    "Portable 8 cm water purification unit designed for on-the-go usage, ensuring safe and clean drinking water is available onboard at all times.",
                    LocalDateTime.now(),
                    "939937");

            Asset asset10 = new Asset(
                    "FireGuard Fire Pump 4.0L",
                    "A robust 4.0-liter fire suppression water pump designed for quick response in emergency fire situations, ensuring safety and minimizing damage risk.",
                    LocalDateTime.now(),
                    "510582");

            Asset asset11 = new Asset(
                    "AeroFlow Ventilation Fan 120W",
                    "Designed specifically for kitchen exhaust, this 120-watt fan promotes air circulation and heat removal, critical for maintaining a comfortable and safe cooking environment.",
                    LocalDateTime.now(),
                    "797496");

            Asset asset12 = new Asset(
                    "FuelSafe Fuel Filter 7cm",
                    "A smaller 7 cm auxiliary fuel system filter, designed to provide additional protection for secondary engines or backup systems, ensuring operational readiness.",
                    LocalDateTime.now(),
                    "442881");


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

            // Setting up SpareParts
            SparePart sparePart1 = new SparePart("https://sparePart1.pdf", 4);
            SparePart sparePart2 = new SparePart("https://sparePart2.pdf", 5);
            SparePart sparePart3 = new SparePart("https://sparePart3.pdf", 6);
            SparePart sparePart4 = new SparePart("https://sparePart4.pdf", 7);
            SparePart sparePart5 = new SparePart("https://sparePart5.pdf", 8);
            SparePart sparePart6 = new SparePart("https://sparePart6.pdf", 9);
            SparePart sparePart7 = new SparePart("https://sparePart7.pdf", 10);
            SparePart sparePart8 = new SparePart("https://sparePart8.pdf", 11);
            SparePart sparePart9 = new SparePart("https://sparePart9.pdf", 42);
            SparePart sparePart10 = new SparePart("https://sparePart10.pdf", 34);
            SparePart sparePart11 = new SparePart("https://sparePart11.pdf", 43);
            SparePart sparePart12 = new SparePart("https://sparePart12.pdf", 444);

            sparePart1.setAsset(asset1);
            sparePart2.setAsset(asset2);
            sparePart3.setAsset(asset3);
            sparePart4.setAsset(asset4);
            sparePart5.setAsset(asset5);
            sparePart6.setAsset(asset6);
            sparePart7.setAsset(asset7);
            sparePart8.setAsset(asset8);
            sparePart9.setAsset(asset9);
            sparePart10.setAsset(asset10);
            sparePart11.setAsset(asset11);
            sparePart12.setAsset(asset12);

            sparePartRepository.save(sparePart1);
            sparePartRepository.save(sparePart2);
            sparePartRepository.save(sparePart3);
            sparePartRepository.save(sparePart4);
            sparePartRepository.save(sparePart5);
            sparePartRepository.save(sparePart6);
            sparePartRepository.save(sparePart7);
            sparePartRepository.save(sparePart8);
            sparePartRepository.save(sparePart9);
            sparePartRepository.save(sparePart10);
            sparePartRepository.save(sparePart11);
            sparePartRepository.save(sparePart12);


            // Setting up AssetOnSite
            AssetOnSite assetOnSite1 = new AssetOnSite("111.222.333");
            AssetOnSite assetOnSite2 = new AssetOnSite("222.333.444");
            AssetOnSite assetOnSite3 = new AssetOnSite("333.444.555");
            AssetOnSite assetOnSite4 = new AssetOnSite("444.555.666");
            AssetOnSite assetOnSite5 = new AssetOnSite("555.666.777");
            AssetOnSite assetOnSite6 = new AssetOnSite("666.777.888");
            AssetOnSite assetOnSite7 = new AssetOnSite("777.888.999");
            AssetOnSite assetOnSite8 = new AssetOnSite("888.999.111");
            AssetOnSite assetOnSite9 = new AssetOnSite("999.111.222");

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


            // Setting up services
            Service service1 = new Service("Change Oil", "12 months", "change-oil.pdf");
            Service service2 = new Service("Change fuel", "5 weeks", "change-fuel.pdf");
            Service service3 = new Service("Change pump", "12 weeks", "change-pump.pdf");
            Service service4 = new Service("Change vent", "5 months", "change-vent.pdf");
            Service service5 = new Service("Change motor", "1 months", "change-motor.pdf");
            Service service6 = new Service("Change water", "2 months", "change-water.pdf");
            Service service7 = new Service("Change bolt", "3 months", "change-bolt.pdf");
            Service service8 = new Service("Change light", "4 months", "change-light.pdf");
            Service service9 = new Service("Change tank", "5 months", "change-tank.pdf");
            Service service10 = new Service("Change wheel", "6 months", "change-wheel.pdf");
            Service service11 = new Service("Change everything", "7 months", "change-everything.pdf");
            Service service12 = new Service("Change bed", "9 months", "change-bed.pdf");

            service1.setAsset(asset1);
            service2.setAsset(asset2);
            service3.setAsset(asset3);
            service4.setAsset(asset4);
            service5.setAsset(asset5);
            service6.setAsset(asset6);
            service7.setAsset(asset7);
            service8.setAsset(asset8);
            service9.setAsset(asset9);
            service10.setAsset(asset10);
            service11.setAsset(asset11);
            service12.setAsset(asset12);

            serviceRepository.save(service1);
            serviceRepository.save(service2);
            serviceRepository.save(service3);
            serviceRepository.save(service4);
            serviceRepository.save(service5);
            serviceRepository.save(service6);
            serviceRepository.save(service7);
            serviceRepository.save(service8);
            serviceRepository.save(service9);
            serviceRepository.save(service10);
            serviceRepository.save(service11);
            serviceRepository.save(service12);

            // Setting up comments
            Comment comment1 = new Comment("Test comment 1", LocalDateTime.now());
            Comment comment2 = new Comment("Test comment 2", LocalDateTime.now());
            Comment comment3 = new Comment("Test comment 3", LocalDateTime.now());
            Comment comment4 = new Comment("Test comment 4", LocalDateTime.now());
            Comment comment5 = new Comment("Test comment 5", LocalDateTime.now());

            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);
            commentRepository.save(comment4);
            commentRepository.save(comment5);

            // Setting up completedServices
            ServiceCompleted serviceCompleted1 = new ServiceCompleted(null, null);
            ServiceCompleted serviceCompleted2 = new ServiceCompleted(null, null);
            ServiceCompleted serviceCompleted3 = new ServiceCompleted(null, null);
            ServiceCompleted serviceCompleted4 = new ServiceCompleted(null, null);
            ServiceCompleted serviceCompleted5 = new ServiceCompleted(null, null);

            serviceCompleted1.setUser(jon);
            serviceCompleted2.setUser(jon);
            serviceCompleted3.setUser(jenny);
            serviceCompleted4.setUser(jon);
            serviceCompleted5.setUser(jenny);

            serviceCompleted1.setAssetOnSite(assetOnSite1);
            serviceCompleted2.setAssetOnSite(assetOnSite1);
            serviceCompleted3.setAssetOnSite(assetOnSite4);
            serviceCompleted4.setAssetOnSite(assetOnSite7);
            serviceCompleted5.setAssetOnSite(assetOnSite9);

            serviceCompleted1.setService(service1);
            serviceCompleted2.setService(service2);
            serviceCompleted3.setService(service3);
            serviceCompleted4.setService(service7);
            serviceCompleted5.setService(service4);

            serviceCompletedRepository.save(serviceCompleted1);
            serviceCompletedRepository.save(serviceCompleted2);
            serviceCompletedRepository.save(serviceCompleted3);
            serviceCompletedRepository.save(serviceCompleted4);
            serviceCompletedRepository.save(serviceCompleted5);


            // Setting up ServiceComments
            ServiceComment serviceComment1 = new ServiceComment();
            ServiceComment serviceComment2 = new ServiceComment();
            ServiceComment serviceComment3 = new ServiceComment();
            ServiceComment serviceComment4 = new ServiceComment();
            ServiceComment serviceComment5 = new ServiceComment();

            serviceComment1.setComment(comment1);
            serviceComment2.setComment(comment2);
            serviceComment3.setComment(comment3);
            serviceComment4.setComment(comment4);
            serviceComment5.setComment(comment5);

            serviceComment1.setServiceCompleted(serviceCompleted1);
            serviceComment2.setServiceCompleted(serviceCompleted2);
            serviceComment3.setServiceCompleted(serviceCompleted3);
            serviceComment4.setServiceCompleted(serviceCompleted4);
            serviceComment5.setServiceCompleted(serviceCompleted5);

            serviceCommentRepository.save(serviceComment1);
            serviceCommentRepository.save(serviceComment2);
            serviceCommentRepository.save(serviceComment3);
            serviceCommentRepository.save(serviceComment4);
            serviceCommentRepository.save(serviceComment5);


            log.info("DONE importing test data");
        } else {
            log.info("Database already populated.");
        }
    }
}
