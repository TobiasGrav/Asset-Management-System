package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;
import ntnu.group03.idata2900.ams.model.Comment;
import ntnu.group03.idata2900.ams.model.ServiceComment;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import ntnu.group03.idata2900.ams.repositories.ServiceCommentRepository;
import ntnu.group03.idata2900.ams.repositories.ServiceCompletedRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCompletedService {

    private final ServiceCompletedRepository serviceCompletedRepository;
    private final ServiceCommentRepository serviceCommentRepository;

    public ServiceCompletedService(ServiceCompletedRepository serviceCompletedRepository, ServiceCommentRepository serviceCommentRepository) {
        this.serviceCompletedRepository = serviceCompletedRepository;
        this.serviceCommentRepository = serviceCommentRepository;
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

    public List<Comment> getAllCommentsByServiceCompleted(ServiceCompleted serviceCompleted) {
        return serviceCommentRepository.findAllByServiceCompleted(serviceCompleted)
                .stream()
                .map(ServiceComment::getComment)
                .toList();
    }
}
