package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.ServiceDto;
import ntnu.group03.idata2900.ams.repositories.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing services.
 */
@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    /**
     * Constructor for ServiceService.
     *
     * @param serviceRepository serviceRepository
     */
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Fetches all Service entities from the database.
     *
     * @return a list of all Service.
     */
    public List<ntnu.group03.idata2900.ams.model.Service> getAll() {
        List<ntnu.group03.idata2900.ams.model.Service> services = new LinkedList<>();
        this.serviceRepository.findAll().forEach(services::add);
        return services;
    }

    /**
     * Fetches a Service by ID.
     *
     * @param id the ID of the Service.
     * @return an Optional containing the Service if found, or an empty Optional if not.
     */
    public Optional<ntnu.group03.idata2900.ams.model.Service> getService(int id){
        return this.serviceRepository.findById(id);
    }

    /**
     * Creates a new Service entity based on the given DTO.
     *
     * @param serviceDto the DTO containing data for the new Service.
     * @return the created Service.
     */
    public ntnu.group03.idata2900.ams.model.Service createService(ServiceDto serviceDto){
        ntnu.group03.idata2900.ams.model.Service newService = new ntnu.group03.idata2900.ams.model.Service(serviceDto);
        return this.serviceRepository.save(newService);
    }

    /**
     * Updates an existing Service.
     *
     * @param service the Service to update.
     */
    public void updateService(ntnu.group03.idata2900.ams.model.Service service) {
        this.serviceRepository.save(service);
    }

    /**
     * Deletes a Service by ID.
     *
     * @param id the ID of the Service to delete.
     */
    public void deleteService(int id){
        this.serviceRepository.deleteById(id);
    }
}
