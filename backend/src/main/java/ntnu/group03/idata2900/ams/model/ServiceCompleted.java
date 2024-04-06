package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.ServiceCompletedDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Services completed on a given asset", title = "service completed")
@Entity
public class ServiceCompleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the serviceCompleted")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "time_completed", nullable = true, unique = false)
    @Schema(description = "Time completed when service is done")
    private LocalDateTime timeCompleted;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "last_service", nullable = true, unique = false)
    @Schema(description = "Time when last service is done")
    private LocalDateTime lastService;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "assetSite_id", referencedColumnName = "id")
    )
    @Schema(description = "assetOnSite the service was completed on")
    private AssetOnSite assetOnSite;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "service_id", referencedColumnName = "id")
    )
    @Schema(description = "service that was completed")
    private Service service;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Schema(description = "user that completed the service")
    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "serviceCompleted")
    @Schema(description = "comments connected to given services")
    private Set<ServiceComment> serviceComments = new LinkedHashSet<>();


    public ServiceCompleted() {
    }

    public ServiceCompleted(LocalDateTime timeCompleted, LocalDateTime lastService) {
        this.timeCompleted = timeCompleted;
        this.lastService = lastService;
    }

    public ServiceCompleted(ServiceCompletedDto serviceCompletedDto) {
        this.timeCompleted = serviceCompletedDto.getTimeCompleted();
        this.lastService = serviceCompletedDto.getLastService();
        this.assetOnSite = serviceCompletedDto.getAssetOnSite();
        this.service = serviceCompletedDto.getService();
        this.user = serviceCompletedDto.getUser();
    }
}
