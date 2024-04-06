package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.AssetDto;
import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.repositories.AssetRepository;
import ntnu.group03.idata2900.ams.repositories.ServiceCompletedRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCompletedService {

    private final ServiceCompletedRepository serviceCompletedRepository;

    public ServiceCompletedService(ServiceCompletedRepository serviceCompletedRepository) {
        this.serviceCompletedRepository = serviceCompletedRepository;
    }

    public List<ServiceCompleted> getAll() {
        List<ServiceCompleted> completedServices = new LinkedList<>();
        this.serviceCompletedRepository.findAll().forEach(completedServices::add);
        return completedServices;
    }

    public Optional<ServiceCompleted> getServiceCompleted(int id){
        return this.serviceCompletedRepository.findById(id);
    }

    public ServiceCompleted createServiceCompleted(ServiceCompletedDto serviceCompletedDto){
        ServiceCompleted newServiceCompleted = new ServiceCompleted(serviceCompletedDto);
        return this.serviceCompletedRepository.save(newServiceCompleted);
    }

    public void updateServiceCompleted(ServiceCompleted serviceCompleted) {
        this.serviceCompletedRepository.save(serviceCompleted);
    }

    public void deleteServiceCompleted(int id){
        this.serviceCompletedRepository.deleteById(id);
    }
}
