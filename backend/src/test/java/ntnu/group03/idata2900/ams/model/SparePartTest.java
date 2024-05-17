package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.SparePartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparePartTest {

    private SparePart sparePart;
    private SparePartDto sparePartDto;
    private Asset asset;

    @BeforeEach
    public void setUp() {
        asset = new Asset();
        sparePart = new SparePart("https://test.com/position2.png", 10);
        sparePartDto = new SparePartDto("https://test.com/position2.png", 10, asset);
    }

    @Test
    void testSparePartConstructorWithParameters() {
        assertEquals("https://test.com/position2.png", sparePart.getPositionDiagramUrl());
        assertEquals(10, sparePart.getNumberOfParts());
    }

    @Test
    void testSparePartConstructorWithDto() {
        SparePart newSparePart = new SparePart(sparePartDto);

        assertEquals("https://test.com/position2.png", newSparePart.getPositionDiagramUrl());
        assertEquals(10, newSparePart.getNumberOfParts());
        assertEquals(asset, newSparePart.getAsset());
    }

    @Test
    void testSparePartDefaultConstructor() {
        SparePart defaultSparePart = new SparePart();
        assertNull(defaultSparePart.getPositionDiagramUrl());
        assertEquals(0, defaultSparePart.getNumberOfParts());
        assertNull(defaultSparePart.getAsset());
    }

    @Test
    void testSparePartSettersAndGetters() {
        sparePart.setPositionDiagramUrl("https://test.com/position2.png");
        assertEquals("https://test.com/position2.png", sparePart.getPositionDiagramUrl());

        sparePart.setNumberOfParts(20);
        assertEquals(20, sparePart.getNumberOfParts());

        sparePart.setAsset(asset);
        assertEquals(asset, sparePart.getAsset());
    }

    @Test
    void testSparePartDtoConstructorWithParameters() {
        assertEquals("https://test.com/position2.png", sparePartDto.getPositionDiagramUrl());
        assertEquals(10, sparePartDto.getNumberOfParts());
        assertEquals(asset, sparePartDto.getAsset());
    }

    @Test
    void testSparePartDtoDefaultConstructor() {
        SparePartDto defaultSparePartDto = new SparePartDto();
        assertNull(defaultSparePartDto.getPositionDiagramUrl());
        assertEquals(0, defaultSparePartDto.getNumberOfParts());
        assertNull(defaultSparePartDto.getAsset());
    }

    @Test
    void testSparePartDtoSettersAndGetters() {
        sparePartDto.setPositionDiagramUrl("https://test.com/position2.png");
        assertEquals("https://test.com/position2.png", sparePartDto.getPositionDiagramUrl());

        sparePartDto.setNumberOfParts(20);
        assertEquals(20, sparePartDto.getNumberOfParts());

        sparePartDto.setAsset(asset);
        assertEquals(asset, sparePartDto.getAsset());
    }
}