package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }
    public CategoryDto(){
        
    }
}
