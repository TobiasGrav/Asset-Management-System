package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Schema(description = "Services completed on a given asset", title = "ServicesCompleted")
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
    @JoinColumns({
            @JoinColumn(name = "asset_id", referencedColumnName = "id")
    })
    @Schema(description = "asset the service was completed on")
    private Asset asset;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "service_id", referencedColumnName = "id")
    })
    @Schema(description = "service that was completed")
    private Service service;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    })
    @Schema(description = "user that completed the service")
    private User user;

}
