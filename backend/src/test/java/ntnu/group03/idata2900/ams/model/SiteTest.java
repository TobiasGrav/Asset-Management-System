package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.SiteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {

    private Site site;
    private SiteDto siteDto;
    private Company company;

    @BeforeEach
    public void setUp() {
        company = new Company();
        site = new Site("TestSite");
        siteDto = new SiteDto("TestSiteDto", true, company);
    }

    @Test
    void testSiteConstructorWithParameters() {
        assertEquals("TestSite", site.getName());
        assertTrue(site.isActive());
    }

    @Test
    void testSiteConstructorWithDto() {
        Site newSite = new Site(siteDto);

        assertEquals("TestSiteDto", newSite.getName());
        assertTrue(newSite.isActive());
        assertEquals(company, newSite.getCompany());
    }

    @Test
    void testSiteDefaultConstructor() {
        Site defaultSite = new Site();
        assertNull(defaultSite.getName());
        assertFalse(defaultSite.isActive());
        assertNull(defaultSite.getCompany());
    }

    @Test
    void testSiteSettersAndGetters() {
        site.setName("NewTestSite");
        assertEquals("NewTestSite", site.getName());

        site.setActive(false);
        assertFalse(site.isActive());

        site.setCompany(company);
        assertEquals(company, site.getCompany());

        Set<User> users = new LinkedHashSet<>();
        site.setUsers(users);
        assertEquals(users, site.getUsers());

        Set<AssetOnSite> assetOnSites = new LinkedHashSet<>();
        site.setAssetOnSites(assetOnSites);
        assertEquals(assetOnSites, site.getAssetOnSites());
    }

    @Test
    void testSiteDtoConstructorWithParameters() {
        assertEquals("TestSiteDto", siteDto.getName());
        assertTrue(siteDto.isActive());
        assertEquals(company, siteDto.getCompany());
    }

    @Test
    void testSiteDtoDefaultConstructor() {
        SiteDto defaultSiteDto = new SiteDto();
        assertNull(defaultSiteDto.getName());
        assertFalse(defaultSiteDto.isActive());
        assertNull(defaultSiteDto.getCompany());
    }

    @Test
    void testSiteDtoSettersAndGetters() {
        siteDto.setName("NewTestSiteDto");
        assertEquals("NewTestSiteDto", siteDto.getName());

        siteDto.setActive(false);
        assertFalse(siteDto.isActive());

        siteDto.setCompany(company);
        assertEquals(company, siteDto.getCompany());
    }
}