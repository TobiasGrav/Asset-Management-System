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

/**
 * Represents service for managing completed services.
 */
@Service
public class ServiceCompletedService {

    private final ServiceCompletedRepository serviceCompletedRepository;
    private final ServiceCommentRepository serviceCommentRepository;

    /**
     * Constructor for ServiceCompletedService.
     *
     * @param serviceCompletedRepository serviceCompletedRepository
     * @param serviceCommentRepository serviceCommentRepository
     */
    public ServiceCompletedService(ServiceCompletedRepository serviceCompletedRepository, ServiceCommentRepository serviceCommentRepository) {
        this.serviceCompletedRepository = serviceCompletedRepository;
        this.serviceCommentRepository = serviceCommentRepository;
    }

    /**
     * Fetches all ServiceCompleted entities from the database.
     *
     * @return a list of all ServiceCompleted.
     */
    public List<ServiceCompleted> getAll() {
        List<ServiceCompleted> completedServices = new LinkedList<>();
        this.serviceCompletedRepository.findAll().forEach(completedServices::add);
        return completedServices;
    }

    /**
     * Fetches a ServiceCompleted by ID.
     *
     * @param id the ID of the ServiceCompleted.
     * @return an Optional containing the ServiceCompleted if found, or an empty Optional if not.
     */
    public Optional<ServiceCompleted> getServiceCompleted(int id) {
        return this.serviceCompletedRepository.findById(id);
    }

    /**
     * Creates a new ServiceCompleted entity based on the given DTO.
     *
     * @param serviceCompletedDto the DTO containing data for the new ServiceCompleted.
     * @return the created ServiceCompleted.
     */
    public ServiceCompleted createServiceCompleted(ServiceCompletedDto serviceCompletedDto) {
        ServiceCompleted newServiceCompleted = new ServiceCompleted(serviceCompletedDto);
        return this.serviceCompletedRepository.save(newServiceCompleted);
    }

    /**
     * Updates an existing ServiceCompleted.
     *
     * @param serviceCompleted the ServiceCompleted to update.
     */
    public void updateServiceCompleted(ServiceCompleted serviceCompleted) {
        this.serviceCompletedRepository.save(serviceCompleted);
    }

    /**
     * Deletes a ServiceCompleted by ID.
     *
     * @param id the ID of the ServiceCompleted to delete.
     */
    public void deleteServiceCompleted(int id) {
        this.serviceCompletedRepository.deleteById(id);
    }

    /**
     * Fetches all Comments associated with a given ServiceCompleted.
     *
     * @param serviceCompleted the ServiceCompleted entity.
     * @return a list of Comments associated with the given ServiceCompleted.
     */
    public List<Comment> getAllCommentsByServiceCompleted(ServiceCompleted serviceCompleted) {
        return serviceCommentRepository.findAllByServiceCompleted(serviceCompleted)
                .stream()
                .map(ServiceComment::getComment)
                .toList();
    }
}