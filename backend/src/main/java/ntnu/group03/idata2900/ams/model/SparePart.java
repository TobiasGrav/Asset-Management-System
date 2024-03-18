package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(description = "Site of the asset.", name = "SparePart")
@Entity
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the spare part")
    private int id;

    @Column(name = "position_diagram_url", nullable = false, unique = false)
    @Schema(description = "url of the position diagram to the spare part")
    private String positionDiagramUrl;
    @Column(name = "number_of_parts", nullable = false, unique = false)
    @Schema(description = "number of spare parts")
    private int numberOfParts;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "asset_id", referencedColumnName = "id")
    )
    @Schema(description = "asset that contains given spare parts")
    private Asset asset;

    public int getId() {
        return this.id;
    }

    public String getPositionDiagramUrl() {
        return this.positionDiagramUrl;
    }

    public int getNumberOfParts() {
        return this.numberOfParts;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPositionDiagramUrl(String positionDiagramUrl) {
        this.positionDiagramUrl = positionDiagramUrl;
    }

    public void setNumberOfParts(int numberOfParts) {
        this.numberOfParts = numberOfParts;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
