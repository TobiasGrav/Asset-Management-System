package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.AssetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AssetTest {

    private Asset asset;
    private AssetDto assetDto;
    private Category category;
    private Datasheet datasheet;

    @BeforeEach
    public void setUp() {
        category = new Category();
        datasheet = new Datasheet();
        LocalDateTime testCreationDate = LocalDateTime.now();
        asset = new Asset("testName", "testDescription", testCreationDate, "testPartNumber");
        assetDto = new AssetDto("testName",
                "testDescription",
                testCreationDate,
                true,
                category,
                datasheet,
                "testPartNumber");
    }

    @Test
    void testAssetConstructorWithParameters() {
        assertEquals("testName", asset.getName());
        assertEquals("testDescription", asset.getDescription());
        assertEquals(LocalDateTime.now().getYear(), asset.getCreationDate().getYear());
        assertEquals("testPartNumber", asset.getPartNumber());
        assertTrue(asset.isActive());
    }

    @Test
    void testAssetConstructorWithDto() {
        Asset newAsset = new Asset(assetDto);

        assertEquals("testName", newAsset.getName());
        assertEquals("testDescription", newAsset.getDescription());
        assertEquals(LocalDateTime.now().getYear(), newAsset.getCreationDate().getYear());
        assertEquals("testPartNumber", newAsset.getPartNumber());
        assertEquals(category, newAsset.getCategory());
        assertEquals(datasheet, newAsset.getDatasheet());
        assertTrue(newAsset.isActive());
    }

    @Test
    void testAssetDefaultConstructor() {
        Asset defaultAsset = new Asset();
        assertNull(defaultAsset.getName());
        assertNull(defaultAsset.getDescription());
        assertNull(defaultAsset.getCreationDate());
        assertNull(defaultAsset.getPartNumber());
        assertNull(defaultAsset.getCategory());
        assertNull(defaultAsset.getDatasheet());
        assertFalse(defaultAsset.isActive());
    }

    @Test
    void testAssetSettersAndGetters() {
        asset.setName("newTestName");
        assertEquals("newTestName", asset.getName());

        asset.setDescription("newTestDescription");
        assertEquals("newTestDescription", asset.getDescription());

        LocalDateTime newTestCreationDate = LocalDateTime.now().plusDays(1);
        asset.setCreationDate(newTestCreationDate);
        assertEquals(newTestCreationDate, asset.getCreationDate());

        asset.setPartNumber("newTestPartNumber");
        assertEquals("newTestPartNumber", asset.getPartNumber());

        asset.setCategory(category);
        assertEquals(category, asset.getCategory());

        asset.setDatasheet(datasheet);
        assertEquals(datasheet, asset.getDatasheet());

        Set<SparePart> spareParts = new LinkedHashSet<>();
        asset.setSpareParts(spareParts);
        assertEquals(spareParts, asset.getSpareParts());

        Set<Service> services = new LinkedHashSet<>();
        asset.setServices(services);
        assertEquals(services, asset.getServices());

        Set<AssetOnSite> assetOnSites = new LinkedHashSet<>();
        asset.setAssetOnSites(assetOnSites);
        assertEquals(assetOnSites, asset.getAssetOnSites());

        asset.setActive(false);
        assertFalse(asset.isActive());
    }

    @Test
    void testAssetDtoConstructorWithParameters() {
        assertEquals("testName", assetDto.getName());
        assertEquals("testDescription", assetDto.getDescription());
        assertEquals(LocalDateTime.now().getYear(), assetDto.getCreationDate().getYear());
        assertEquals("testPartNumber", assetDto.getPartNumber());
        assertEquals(category, assetDto.getCategory());
        assertEquals(datasheet, assetDto.getDatasheet());
        assertTrue(assetDto.isActive());
    }

    @Test
    void testAssetDtoDefaultConstructor() {
        AssetDto defaultAssetDto = new AssetDto();
        assertNull(defaultAssetDto.getName());
        assertNull(defaultAssetDto.getDescription());
        assertNull(defaultAssetDto.getCreationDate());
        assertNull(defaultAssetDto.getPartNumber());
        assertNull(defaultAssetDto.getCategory());
        assertNull(defaultAssetDto.getDatasheet());
        assertFalse(defaultAssetDto.isActive());
    }

    @Test
    void testAssetDtoSettersAndGetters() {
        assetDto.setName("newTestName");
        assertEquals("newTestName", assetDto.getName());

        assetDto.setDescription("newTestDescription");
        assertEquals("newTestDescription", assetDto.getDescription());

        LocalDateTime newTestCreationDate = LocalDateTime.now().plusDays(1);
        assetDto.setCreationDate(newTestCreationDate);
        assertEquals(newTestCreationDate, assetDto.getCreationDate());

        assetDto.setPartNumber("newTestPartNumber");
        assertEquals("newTestPartNumber", assetDto.getPartNumber());

        assetDto.setCategory(category);
        assertEquals(category, assetDto.getCategory());

        assetDto.setDatasheet(datasheet);
        assertEquals(datasheet, assetDto.getDatasheet());

        assetDto.setActive(false);
        assertFalse(assetDto.isActive());
    }
}
