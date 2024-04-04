package ntnu.group03.idata2900.ams.dto;



import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Category;
import ntnu.group03.idata2900.ams.model.Datasheet;

import java.time.LocalDateTime;

@Setter
@Getter
public class AssetDto {

    private String name;

    private String description;

    private LocalDateTime creationDate;

    private boolean active;
    private Category category;
    private Datasheet datasheet;

    public AssetDto(String name, String description, LocalDateTime creationDate, boolean active, Category category, Datasheet datasheet) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.active = active;
        this.category = category;
        this.datasheet = datasheet;
    }


}
