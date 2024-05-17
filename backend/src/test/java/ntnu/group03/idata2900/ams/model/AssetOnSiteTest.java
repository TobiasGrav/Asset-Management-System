package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.AssetOnSiteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AssetOnSiteTest {

    private AssetOnSite assetOnSite;
    private AssetOnSiteDto assetOnSiteDto;
    private Site site;
    private Asset asset;

    @BeforeEach
    public void setUp() {
        site = new Site();
        asset = new Asset();
        LocalDateTime testCommissionDate = LocalDateTime.now();
        assetOnSite = new AssetOnSite("123.321.123.321");
        assetOnSite.setCommissionDate(testCommissionDate);
        assetOnSiteDto = new AssetOnSiteDto(testCommissionDate, site, asset, 1, "123.321.123.321");
    }

    @Test
    void testAssetOnSiteConstructorWithParameters() {
        assertEquals("123.321.123.321", assetOnSite.getAssetOnSiteTag());
    }

    @Test
    void testAssetOnSiteConstructorWithDto() {
        AssetOnSite newAssetOnSite = new AssetOnSite(assetOnSiteDto);

        assertEquals("123.321.123.321", newAssetOnSite.getAssetOnSiteTag());
        assertEquals(site, newAssetOnSite.getSite());
        assertEquals(asset, newAssetOnSite.getAsset());
        assertEquals(LocalDateTime.now().getYear(), newAssetOnSite.getCommissionDate().getYear());
    }

    @Test
    void testAssetOnSiteDefaultConstructor() {
        AssetOnSite defaultAssetOnSite = new AssetOnSite();
        assertNull(defaultAssetOnSite.getAssetOnSiteTag());
        assertNull(defaultAssetOnSite.getCommissionDate());
        assertNull(defaultAssetOnSite.getSite());
        assertNull(defaultAssetOnSite.getAsset());
    }

    @Test
    void testAssetOnSiteSettersAndGetters() {
        assetOnSite.setAssetOnSiteTag("321.123.321.123");
        assertEquals("321.123.321.123", assetOnSite.getAssetOnSiteTag());

        LocalDateTime newTestCommissionDate = LocalDateTime.now().plusDays(1);
        assetOnSite.setCommissionDate(newTestCommissionDate);
        assertEquals(newTestCommissionDate, assetOnSite.getCommissionDate());

        assetOnSite.setSite(site);
        assertEquals(site, assetOnSite.getSite());

        assetOnSite.setAsset(asset);
        assertEquals(asset, assetOnSite.getAsset());

        Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();
        assetOnSite.setServicesCompleted(servicesCompleted);
        assertEquals(servicesCompleted, assetOnSite.getServicesCompleted());
    }

    @Test
    void testAssetOnSiteDtoConstructorWithParameters() {
        assertEquals("123.321.123.321", assetOnSiteDto.getAssetOnSiteTag());
        assertEquals(site, assetOnSiteDto.getSite());
        assertEquals(asset, assetOnSiteDto.getAsset());
        assertEquals(LocalDateTime.now().getYear(), assetOnSiteDto.getCommissionDate().getYear());
    }

    @Test
    void testAssetOnSiteDtoDefaultConstructor() {
        AssetOnSiteDto defaultAssetOnSiteDto = new AssetOnSiteDto();
        assertNull(defaultAssetOnSiteDto.getAssetOnSiteTag());
        assertNull(defaultAssetOnSiteDto.getCommissionDate());
        assertNull(defaultAssetOnSiteDto.getSite());
        assertNull(defaultAssetOnSiteDto.getAsset());
    }

    @Test
    void testAssetOnSiteDtoSettersAndGetters() {
        assetOnSiteDto.setAssetOnSiteTag("321.123.321.123");
        assertEquals("321.123.321.123", assetOnSiteDto.getAssetOnSiteTag());

        LocalDateTime newTestCommissionDate = LocalDateTime.now().plusDays(1);
        assetOnSiteDto.setCommissionDate(newTestCommissionDate);
        assertEquals(newTestCommissionDate, assetOnSiteDto.getCommissionDate());

        assetOnSiteDto.setSite(site);
        assertEquals(site, assetOnSiteDto.getSite());

        assetOnSiteDto.setAsset(asset);
        assertEquals(asset, assetOnSiteDto.getAsset());
    }
}
