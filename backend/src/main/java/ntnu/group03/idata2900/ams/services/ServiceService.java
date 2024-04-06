package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.ServiceDto;
import ntnu.group03.idata2900.ams.repositories.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ntnu.group03.idata2900.ams.model.Service> getAll() {
        List<ntnu.group03.idata2900.ams.model.Service> services = new LinkedList<>();
        this.serviceRepository.findAll().forEach(services::add);
        return services;
    }

    public Optional<ntnu.group03.idata2900.ams.model.Service> getService(int id){
        return this.serviceRepository.findById(id);
    }

    public ntnu.group03.idata2900.ams.model.Service createService(ServiceDto serviceDto){
        ntnu.group03.idata2900.ams.model.Service newService = new ntnu.group03.idata2900.ams.model.Service(serviceDto);
        return this.serviceRepository.save(newService);
    }

    public void updateService(ntnu.group03.idata2900.ams.model.Service service) {
        this.serviceRepository.save(service);
    }

    public void deleteService(int id){
        this.serviceRepository.deleteById(id);
    }
}
