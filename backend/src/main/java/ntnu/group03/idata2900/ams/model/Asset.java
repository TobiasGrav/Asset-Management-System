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
    private String description;
    @Column(name = "creation_date", nullable = false, unique = false)
    @Schema(description = "the date asset was created")
    private LocalDateTime creationDate;
    @Column(name = "commission_date", nullable = true, unique = false)
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
    @JoinColumns(
            @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @Schema(description = "category of the given asset")
    private Category category;

    @JsonBackReference
    @OneToMany(mappedBy = "asset")
    @Schema(description = "services that are completed on asset")
    private Set<ServiceCompleted> servicesCompleted = new LinkedHashSet<>();

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "datasheet_id", referencedColumnName = "id")
    )
    @Schema(description = "datasheet of the given asset")
    private Datasheet datasheet;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "site_id", referencedColumnName = "id")
    )
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
    public Asset(String name, String description, LocalDateTime creationDate, LocalDateTime commissionDate, String qrCode) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.commissionDate = commissionDate;
        this.qrCode = qrCode;
        this.active = true;
    }

    public Asset() {

    }

    /**
     * Returns asset id
     * @return returns asset id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns asset name
     * @return returns asset name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns asset description
     * @return returns asset description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns asset creationDate
     * @return returns asset creationDate
     */
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns asset commissionDate
     * @return returns asset commissionDate
     */
    public LocalDateTime getCommissionDate() {
        return this.commissionDate;
    }

    /**
     * Returns asset qrCode
     * @return returns asset qrCode
     */
    public String getQrCode() {
        return this.qrCode;
    }

    /**
     * Returns active
     * @return returns active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Returns asset category
     * @return returns asset category
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Returns asset servicesCompleted
     * @return returns asset servicesCompleted
     */
    public Set<ServiceCompleted> getServicesCompleted() {
        return this.servicesCompleted;
    }

    /**
     * Returns asset datasheet
     * @return returns asset datasheet
     */
    public Datasheet getDatasheet() {
        return this.datasheet;
    }

    /**
     * Returns asset site
     * @return returns asset site
     */
    public Site getSite() {
        return this.site;
    }

    /**
     * Returns asset spareParts
     * @return returns asset spareParts
     */
    public Set<SparePart> getSpareParts() {
        return this.spareParts;
    }


    /**
     * sets the value of id field to given value.
     *
     * @param id id of asset
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets the value of name field to given value.
     *
     * @param name name of asset
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the value of description field to given value.
     *
     * @param description description of asset
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * sets the value of creationDate field to given value.
     *
     * @param creationDate creationDate of asset
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * sets the value of commissionDate field to given value.
     *
     * @param commissionDate commissionDate of asset
     */
    public void setCommissionDate(LocalDateTime commissionDate) {
        this.commissionDate = commissionDate;
    }

    /**
     * sets the value of qrCode field to given value.
     *
     * @param qrCode qrCode of asset
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * sets the value of active field to given value.
     *
     * @param active active of asset
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * sets the value of category field to given value.
     *
     * @param category category of asset
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * sets the value of servicesCompleted field to given value.
     *
     * @param servicesCompleted servicesCompleted of asset
     */
    public void setServicesCompleted(Set<ServiceCompleted> servicesCompleted) {
        this.servicesCompleted = servicesCompleted;
    }

    /**
     * sets the value of datasheet field to given value.
     *
     * @param datasheet datasheet of asset
     */
    public void setDatasheet(Datasheet datasheet) {
        this.datasheet = datasheet;
    }

    /**
     * sets the value of site field to given value.
     *
     * @param site site of asset
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * sets the value of spareParts field to given value.
     *
     * @param spareParts spareParts of asset
     */
    public void setSpareParts(Set<SparePart> spareParts) {
        this.spareParts = spareParts;
    }
}
