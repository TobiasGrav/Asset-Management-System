package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Asset;

@Getter
@Setter
public class SparePartDto {
    private String positionDiagramUrl;
    private int numberOfParts;
    private Asset asset;

    public SparePartDto(String positionDiagramUrl, int numberOfParts, Asset asset) {
        this.positionDiagramUrl = positionDiagramUrl;
        this.numberOfParts = numberOfParts;
        this.asset = asset;
    }
}
