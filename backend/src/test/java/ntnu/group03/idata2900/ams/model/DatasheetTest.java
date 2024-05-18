package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.DatasheetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DatasheetTest {

    private Datasheet datasheet;
    private DatasheetDto datasheetDto;

    @BeforeEach
    public void setUp() {
        datasheet = new Datasheet("testName", "testReferenceNumber", "https://testPdfUrl.com");
        datasheetDto = new DatasheetDto("testName", "testReferenceNumber", "https://testPdfUrl.com");
    }

    @Test
    void testDatasheetConstructorWithParameters() {
        assertEquals("testName", datasheet.getName());
        assertEquals("testReferenceNumber", datasheet.getReferenceNumber());
        assertEquals("https://testPdfUrl.com", datasheet.getPdfUrl());
    }

    @Test
    void testDatasheetConstructorWithDto() {
        Datasheet newDatasheet = new Datasheet(datasheetDto);

        assertEquals("testName", newDatasheet.getName());
        assertEquals("testReferenceNumber", newDatasheet.getReferenceNumber());
        assertEquals("https://testPdfUrl.com", newDatasheet.getPdfUrl());
    }

    @Test
    void testDatasheetDefaultConstructor() {
        Datasheet defaultDatasheet = new Datasheet();
        assertNull(defaultDatasheet.getName());
        assertNull(defaultDatasheet.getReferenceNumber());
        assertNull(defaultDatasheet.getPdfUrl());
    }

    @Test
    void testDatasheetSettersAndGetters() {
        datasheet.setName("newTestName");
        assertEquals("newTestName", datasheet.getName());

        datasheet.setReferenceNumber("newTestReferenceNumber");
        assertEquals("newTestReferenceNumber", datasheet.getReferenceNumber());

        datasheet.setPdfUrl("https://testPdfUrl123.com");
        assertEquals("https://testPdfUrl123.com", datasheet.getPdfUrl());

        Set<Asset> assets = new LinkedHashSet<>();
        datasheet.setAssets(assets);
        assertEquals(assets, datasheet.getAssets());
    }

    @Test
    void testDatasheetDtoConstructorWithParameters() {
        assertEquals("testName", datasheetDto.getName());
        assertEquals("testReferenceNumber", datasheetDto.getReferenceNumber());
        assertEquals("https://testPdfUrl.com", datasheetDto.getPdfUrl());
    }

    @Test
    void testDatasheetDtoDefaultConstructor() {
        DatasheetDto defaultDatasheetDto = new DatasheetDto();
        assertNull(defaultDatasheetDto.getName());
        assertNull(defaultDatasheetDto.getReferenceNumber());
        assertNull(defaultDatasheetDto.getPdfUrl());
    }

    @Test
    void testDatasheetDtoSettersAndGetters() {
        datasheetDto.setName("Test123");
        assertEquals("Test123", datasheetDto.getName());

        datasheetDto.setReferenceNumber("TestReferenceNumber123");
        assertEquals("TestReferenceNumber123", datasheetDto.getReferenceNumber());

        datasheetDto.setPdfUrl("https://testPdfUrl123.com");
        assertEquals("https://testPdfUrl123.com", datasheetDto.getPdfUrl());
    }

}
