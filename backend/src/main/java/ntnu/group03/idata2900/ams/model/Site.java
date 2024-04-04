package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Schema(description = "Site of the asset.", name = "site")
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
    @Column(name = "client_id", nullable = false, unique = false)
    @Schema(description = "client id connected to the site")
    private String clientId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If site is active or not")
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "company_id", referencedColumnName = "id")
    )
    @Schema(description = "site of the given company")
    private Company company;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Schema(description = "site of the given asset")
    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "site")
    @Schema(description = "assetSites on the given site")
    private Set<AssetSite> assetSites = new LinkedHashSet<>();


    /**
     * Constructor with parameters
     *
     * @param name      site name
     * @param clientId  site clientId
     */
    public Site(String name, String clientId) {
        this.name = name;
        this.clientId = clientId;
        this.active = true;
    }

    public Site(){

    }

}
