package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.AssetDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "asset which assets may have.", name = "categories")
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the asset")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "name", nullable = false, unique = false)
    @Schema(description = "name of the asset")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "description", nullable = false, unique = false)
    @Schema(description = "description of the asset")
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "creation_date", nullable = false, unique = false)
    @Schema(description = "the date asset was created")
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If user account is active or not")
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @Schema(description = "category of the given asset")
    private Category category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "datasheet_id", referencedColumnName = "id")
    )
    @Schema(description = "datasheet of the given asset")
    private Datasheet datasheet;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "asset")
    @Schema(description = "spare parts that builds / constructs given parent asset")
    private Set<SparePart> spareParts = new LinkedHashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "asset")
    @Schema(description = "services on a given asset")
    private Set<Service> services = new LinkedHashSet<>();


    /**
     * Constructor with parameters
     *
     * @param name              Asset name.
     * @param description       Asset description.
     * @param creationDate      Creation date of asset.
     */
    public Asset(String name, String description, LocalDateTime creationDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.active = true;
    }

    public Asset(AssetDto assetDto){
        this.name = assetDto.getName();
        this.description = assetDto.getDescription();
        this.creationDate = assetDto.getCreationDate();
        this.active = true;
    }

    public Asset() {

    }

}
