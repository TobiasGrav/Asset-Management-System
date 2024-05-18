package ntnu.group03.idata2900.ams.model;

import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCompletedTest {

    private ServiceCompleted serviceCompleted;
    private ServiceCompletedDto serviceCompletedDto;
    private AssetOnSite assetOnSite;
    private Service service;
    private User user;

    @BeforeEach
    public void setUp() {
        assetOnSite = new AssetOnSite();
        service = new Service();
        user = new User();
        LocalDateTime testTimeCompleted = LocalDateTime.now();
        LocalDateTime testLastService = LocalDateTime.now().minusDays(1);

        serviceCompleted = new ServiceCompleted(testTimeCompleted, testLastService);
        serviceCompletedDto = new ServiceCompletedDto(testTimeCompleted, testLastService, assetOnSite, service, user);
    }

    @Test
    void testServiceCompletedConstructorWithParameters() {
        assertNotNull(serviceCompleted.getTimeCompleted());
        assertNotNull(serviceCompleted.getLastService());
    }

    @Test
    void testServiceCompletedConstructorWithDto() {
        ServiceCompleted newServiceCompleted = new ServiceCompleted(serviceCompletedDto);

        assertNotNull(newServiceCompleted.getTimeCompleted());
        assertNotNull(newServiceCompleted.getLastService());
        assertEquals(assetOnSite, newServiceCompleted.getAssetOnSite());
        assertEquals(service, newServiceCompleted.getService());
        assertEquals(user, newServiceCompleted.getUser());
    }

    @Test
    void testServiceCompletedDefaultConstructor() {
        ServiceCompleted defaultServiceCompleted = new ServiceCompleted();
        assertNull(defaultServiceCompleted.getTimeCompleted());
        assertNull(defaultServiceCompleted.getLastService());
        assertNull(defaultServiceCompleted.getAssetOnSite());
        assertNull(defaultServiceCompleted.getService());
        assertNull(defaultServiceCompleted.getUser());
    }

    @Test
    void testServiceCompletedSettersAndGetters() {
        LocalDateTime newTestTimeCompleted = LocalDateTime.now().plusDays(1);
        serviceCompleted.setTimeCompleted(newTestTimeCompleted);
        assertEquals(newTestTimeCompleted, serviceCompleted.getTimeCompleted());

        LocalDateTime newTestLastService = LocalDateTime.now();
        serviceCompleted.setLastService(newTestLastService);
        assertEquals(newTestLastService, serviceCompleted.getLastService());

        serviceCompleted.setAssetOnSite(assetOnSite);
        assertEquals(assetOnSite, serviceCompleted.getAssetOnSite());

        serviceCompleted.setService(service);
        assertEquals(service, serviceCompleted.getService());

        serviceCompleted.setUser(user);
        assertEquals(user, serviceCompleted.getUser());

        Set<ServiceComment> serviceComments = new LinkedHashSet<>();
        serviceCompleted.setServiceComments(serviceComments);
        assertEquals(serviceComments, serviceCompleted.getServiceComments());
    }

    @Test
    void testServiceCompletedDtoConstructorWithParameters() {
        assertNotNull(serviceCompletedDto.getTimeCompleted());
        assertNotNull(serviceCompletedDto.getLastService());
        assertEquals(assetOnSite, serviceCompletedDto.getAssetOnSite());
        assertEquals(service, serviceCompletedDto.getService());
        assertEquals(user, serviceCompletedDto.getUser());
    }

    @Test
    void testServiceCompletedDtoDefaultConstructor() {
        ServiceCompletedDto defaultServiceCompletedDto = new ServiceCompletedDto();
        assertNull(defaultServiceCompletedDto.getTimeCompleted());
        assertNull(defaultServiceCompletedDto.getLastService());
        assertNull(defaultServiceCompletedDto.getAssetOnSite());
        assertNull(defaultServiceCompletedDto.getService());
        assertNull(defaultServiceCompletedDto.getUser());
    }

    @Test
    void testServiceCompletedDtoSettersAndGetters() {
        LocalDateTime newTestTimeCompleted = LocalDateTime.now().plusDays(1);
        serviceCompletedDto.setTimeCompleted(newTestTimeCompleted);
        assertEquals(newTestTimeCompleted, serviceCompletedDto.getTimeCompleted());

        LocalDateTime newTestLastService = LocalDateTime.now();
        serviceCompletedDto.setLastService(newTestLastService);
        assertEquals(newTestLastService, serviceCompletedDto.getLastService());

        serviceCompletedDto.setAssetOnSite(assetOnSite);
        assertEquals(assetOnSite, serviceCompletedDto.getAssetOnSite());

        serviceCompletedDto.setService(service);
        assertEquals(service, serviceCompletedDto.getService());

        serviceCompletedDto.setUser(user);
        assertEquals(user, serviceCompletedDto.getUser());
    }
}
