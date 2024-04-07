package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private String name;

    public CompanyDto(){

    }

    public CompanyDto(String name) {
        this.name = name;
    }
}
