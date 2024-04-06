package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.SiteDto;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Site represents a boat where all the assets of that given boat is located", name = "site")
@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the site")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "name", nullable = false, unique = false)
    @Schema(description = "name of the site")
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If site is active or not")
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns(
            @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    @Schema(description = "site of the given company")
    private Company company;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @ManyToMany(mappedBy = "sites")
    @Schema(description = "Users connected to the the given site")
    private Set<User> users = new LinkedHashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "assetOnSites on the given site")
    private Set<AssetOnSite> assetOnSites = new LinkedHashSet<>();


    /**
     * Constructor with parameters
     *
     * @param name      site name
     */
    public Site(String name) {
        this.name = name;
        this.active = true;
    }

    public Site(SiteDto siteDto) {
        this.name = siteDto.getName();
        this.active = siteDto.isActive();
        this.company = siteDto.getCompany();
    }



    public Site(){

    }

}
