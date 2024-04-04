package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class AssetSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the assetSite")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Commission date of the assetSite")
    private LocalDateTime commissionDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "assetSite")
    @Schema(description = "services completed on assetSite")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "asset_id", referencedColumnName = "id")
    )
    @Schema(description = "asset of the given assetSite")
    private Asset asset;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "site_id", referencedColumnName = "id")
    )
    @Schema(description = "site of the given assetSite")
    private Site site;

    public AssetSite(){

    }

    public AssetSite(LocalDateTime commissionDate) {
        this.commissionDate = commissionDate;
    }
}


