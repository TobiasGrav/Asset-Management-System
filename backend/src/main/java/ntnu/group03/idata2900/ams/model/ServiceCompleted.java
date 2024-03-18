package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Services completed on a given asset", title = "service completed")
@Entity
public class ServiceCompleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the serviceCompleted")
    private int id;

    @Column(name = "time_completed", nullable = false, unique = false)
    @Schema(description = "Time completed when service is done")
    private LocalDateTime timeCompleted;
    @Column(name = "last_service", nullable = true, unique = false)
    @Schema(description = "Time when last service is done")
    private LocalDateTime lastService;
    @Column(name = "number_Of_Services_Done", nullable = true, unique = false)
    @Schema(description = "Number of how many services is done on given asset")
    private int numberOfServicesDone;


    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "asset_id", referencedColumnName = "id")
    )
    @Schema(description = "asset the service was completed on")
    private Asset asset;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "service_id", referencedColumnName = "id")
    )
    @Schema(description = "service that was completed")
    private Service service;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Schema(description = "user that completed the service")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "serviceCompleted")
    @Schema(description = "comments connected to given services")
    private Set<ServiceComment> serviceComments = new LinkedHashSet<>();


    public int getId() {
        return this.id;
    }

    public LocalDateTime getTimeCompleted() {
        return this.timeCompleted;
    }

    public LocalDateTime getLastService() {
        return this.lastService;
    }

    public int getNumberOfServicesDone() {
        return this.numberOfServicesDone;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public Service getService() {
        return this.service;
    }

    public User getUser() {
        return this.user;
    }

    public Set<ServiceComment> getServiceComments() {
        return this.serviceComments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeCompleted(LocalDateTime timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public void setLastService(LocalDateTime lastService) {
        this.lastService = lastService;
    }

    public void setNumberOfServicesDone(int numberOfServicesDone) {
        this.numberOfServicesDone = numberOfServicesDone;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServiceComments(Set<ServiceComment> serviceComments) {
        this.serviceComments = serviceComments;
    }
}
