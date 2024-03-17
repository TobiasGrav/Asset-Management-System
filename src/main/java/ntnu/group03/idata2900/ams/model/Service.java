package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "service to be done on an asset.", name = "service")
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the service")
    private int id;

    @Column(name = "description", nullable = false, unique = false)
    @Schema(description = "description of the service")
    private String description;
    @Column(name = "interval_name", nullable = false, unique = false)
    @Schema(description = "interval of the service")
    private String intervalName;

    @JsonBackReference
    @OneToMany(mappedBy = "service")
    @Schema(description = "services completed on asset")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();


    public Service() {

    }
}
