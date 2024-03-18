package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Schema(description = "Site of the asset.", name = "site")
@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the site")
    private int id;

    @Column(name = "name", nullable = false, unique = false)
    @Schema(description = "name of the site")
    private String name;
    @Column(name = "client_id", nullable = false, unique = false)
    @Schema(description = "client id connected to the site")
    private int clientId;
    @Column(name = "active", nullable = false, unique = false, updatable = true)
    @Schema(description = "If site is active or not")
    private boolean active;

    @JsonBackReference
    @OneToMany(mappedBy = "site")
    @Schema(description = "assets that are located at the given site")
    private Set<Asset> assets = new LinkedHashSet<>();

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getClientId() {
        return this.clientId;
    }

    public boolean isActive() {
        return this.active;
    }

    public Set<Asset> getAssets() {
        return this.assets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
}
