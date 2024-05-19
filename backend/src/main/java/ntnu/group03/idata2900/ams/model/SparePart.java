package ntnu.group03.idata2900.ams.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.dto.SparePartDto;

@Setter
@Getter
@Schema(description = "spare part of a given asset.", name = "SparePart")
@Entity
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "ID of the spare part")
    private int id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "position_diagram_url", nullable = false, unique = false)
    @Schema(description = "url of the position diagram to the spare part")
    private String positionDiagramUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "number_of_parts", nullable = false, unique = false)
    @Schema(description = "number of spare parts")
    private int numberOfParts;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @ManyToOne()
    @JoinColumns(
            @JoinColumn(name = "asset_id", referencedColumnName = "id")
    )
    @Schema(description = "asset that contains given spare parts")
    private Asset asset;

    public SparePart(){

    }

    public SparePart(String positionDiagramUrl, int numberOfParts) {
        this.positionDiagramUrl = positionDiagramUrl;
        this.numberOfParts = numberOfParts;
    }

    public SparePart(SparePartDto sparePartDto){
        this.positionDiagramUrl = sparePartDto.getPositionDiagramUrl();
        this.numberOfParts = sparePartDto.getNumberOfParts();
        this.asset = sparePartDto.getAsset();
    }
}
