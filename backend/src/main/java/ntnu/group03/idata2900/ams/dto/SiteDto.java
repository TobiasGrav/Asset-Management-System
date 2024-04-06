package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Company;

@Setter
@Getter
public class SiteDto {

    private String name;

    private boolean active;

    private Company company;

    public SiteDto(String name, boolean active, Company company) {
        this.name = name;
        this.active = active;
        this.company = company;
    }
}
