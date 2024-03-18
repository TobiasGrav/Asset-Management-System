package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "asset which assets may have.", name = "categories")
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the asset")
    private int id;

    @Column(name = "name", nullable = false, unique = false)
    @Schema(description = "name of the asset")
    private String name;
    @Column(name = "description", nullable = false, unique = false)
    @Schema(description = "description of the asset")
    private int description;
    @Column(name = "creation_date", nullable = false, unique = false)
    @Schema(description = "the date asset was created")
    private LocalDateTime creationDate;
    @Column(name = "commission_date", nullable = false, unique = false)
    @Schema(description = "the date asset was commissioned")
    private LocalDateTime commissionDate;
    @Column(name = "qr_code", nullable = false, unique = true)
    @Schema(description = "the unique qr code of the asset")
    private String qrCode;
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If user account is active or not")
    private boolean active;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "category_id", referencedColumnName = "id")
    })
    @Schema(description = "category of the given asset")
    private Category category;

    @JsonBackReference
    @OneToMany(mappedBy = "asset")
    @Schema(description = "services that are completed on asset")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "datasheet_id", referencedColumnName = "id")
    })
    @Schema(description = "datasheet of the given asset")
    private Datasheet datasheet;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "site_id", referencedColumnName = "id")
    })
    @Schema(description = "site of the given asset")
    private Site site;

    @JsonBackReference
    @OneToMany(mappedBy = "asset")
    @Schema(description = "spare parts that builds / constructs given parent asset")
    private Set<SparePart> spareParts = new LinkedHashSet<>();


    /**
     * Constructor with parameters
     *
     * @param name              Asset name.
     * @param description       Asset description.
     * @param creationDate      Creation date of asset.
     * @param commissionDate    Commission date of asset.
     * @param qrCode            QR code of asset
     */
    public Asset(String name, int description, LocalDateTime creationDate, LocalDateTime commissionDate, String qrCode) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.commissionDate = commissionDate;
        this.qrCode = qrCode;
        this.active = true;
    }

    public Asset() {

    }
}
