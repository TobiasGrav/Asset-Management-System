package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.ServiceDto;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "service to be done on an asset.", name = "service")
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the service")
    private int id;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "description", nullable = false, unique = false)
    @Schema(description = "description of the service")
    private String description;
    @Column(name = "interval_name", nullable = false, unique = false)
    @Schema(description = "interval of the service")
    private String intervalName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "service")
    @Schema(description = "services completed on asset")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "asset_id", referencedColumnName = "id")
    )
    @Schema(description = "asset of the given service")
    private Asset asset;


    public Service() {

    }

    public Service(String description, String intervalName) {
        this.description = description;
        this.intervalName = intervalName;
    }

    public Service(ServiceDto serviceDto) {
        this.description = serviceDto.getDescription();
        this.intervalName = serviceDto.getIntervalName();
        this.asset = serviceDto.getAsset();
    }
}
