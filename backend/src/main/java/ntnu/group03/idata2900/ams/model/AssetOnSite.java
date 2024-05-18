package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.AssetOnSiteDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class AssetOnSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the assetOnSite")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "commissionDate", nullable = true, unique = false)
    @Schema(description = "Commission date of the assetOnSite")
    private LocalDateTime commissionDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "assetOnSiteTag", nullable = true, unique = true)
    @Schema(description = "assetOnSiteTag of the assetOnSite")
    private String assetOnSiteTag;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "assetOnSite")
    @Schema(description = "services completed on assetOnSite")
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

    public AssetOnSite(){

    }

    public AssetOnSite(String assetOnSiteTag) {
        this.assetOnSiteTag = assetOnSiteTag;
    }

    public AssetOnSite(AssetOnSiteDto assetOnSiteDto){
        this.commissionDate = assetOnSiteDto.getCommissionDate();
        this.site = assetOnSiteDto.getSite();
        this.assetOnSiteTag = assetOnSiteDto.getAssetOnSiteTag();
        this.asset = assetOnSiteDto.getAsset();
    }
}


