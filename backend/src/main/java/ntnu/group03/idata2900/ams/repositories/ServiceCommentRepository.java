package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.ServiceComment;
import ntnu.group03.idata2900.ams.model.ServiceCompleted;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCommentRepository extends CrudRepository<ServiceComment, Integer> {

    /**
     * Finds all comments associated with a given completed service.
     *
     * @param serviceCompleted the service completed to find comments
     * @return a list of comments associated with the specified completed service
     */
    List<ServiceComment> findAllByServiceCompleted(ServiceCompleted serviceCompleted);
}
