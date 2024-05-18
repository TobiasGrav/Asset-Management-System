package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.ServiceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;
    private ServiceDto serviceDto;
    private Asset asset;

    @BeforeEach
    public void setUp() {
        asset = new Asset();
        service = new Service("testDescription", "testInterval", "https://testUrl.com");
        serviceDto = new ServiceDto("testInterval", "testDescription", asset, "https://testUrl.com");
    }

    @Test
    void testServiceConstructorWithParameters() {
        assertEquals("testDescription", service.getDescription());
        assertEquals("testInterval", service.getIntervalName());
        assertEquals("https://testUrl.com", service.getServiceUrl());
    }

    @Test
    void testServiceConstructorWithDto() {
        Service newService = new Service(serviceDto);

        assertEquals("testDescription", newService.getDescription());
        assertEquals("testInterval", newService.getIntervalName());
        assertEquals(asset, newService.getAsset());
        assertEquals("https://testUrl.com", newService.getServiceUrl());
    }

    @Test
    void testServiceDefaultConstructor() {
        Service defaultService = new Service();
        assertNull(defaultService.getDescription());
        assertNull(defaultService.getIntervalName());
        assertNull(defaultService.getServiceUrl());
        assertNull(defaultService.getAsset());
    }

    @Test
    void testServiceSettersAndGetters() {
        service.setDescription("newTestDescription");
        assertEquals("newTestDescription", service.getDescription());

        service.setIntervalName("newTestInterval");
        assertEquals("newTestInterval", service.getIntervalName());

        service.setServiceUrl("newTestUrl");
        assertEquals("newTestUrl", service.getServiceUrl());

        service.setAsset(asset);
        assertEquals(asset, service.getAsset());

        Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();
        service.setServicesCompleted(servicesCompleted);
        assertEquals(servicesCompleted, service.getServicesCompleted());
    }

    @Test
    void testServiceDtoConstructorWithParameters() {
        assertEquals("testDescription", serviceDto.getDescription());
        assertEquals("testInterval", serviceDto.getIntervalName());
        assertEquals(asset, serviceDto.getAsset());
        assertEquals("https://testUrl.com", serviceDto.getServiceUrl());
    }

    @Test
    void testServiceDtoDefaultConstructor() {
        ServiceDto defaultServiceDto = new ServiceDto();
        assertNull(defaultServiceDto.getDescription());
        assertNull(defaultServiceDto.getIntervalName());
        assertNull(defaultServiceDto.getServiceUrl());
        assertNull(defaultServiceDto.getAsset());
    }

    @Test
    void testServiceDtoSettersAndGetters() {
        serviceDto.setDescription("newTestDescription");
        assertEquals("newTestDescription", serviceDto.getDescription());

        serviceDto.setIntervalName("newTestInterval");
        assertEquals("newTestInterval", serviceDto.getIntervalName());

        serviceDto.setServiceUrl("https://testUrl123.com");
        assertEquals("https://testUrl123.com", serviceDto.getServiceUrl());

        serviceDto.setAsset(asset);
        assertEquals(asset, serviceDto.getAsset());
    }
}
