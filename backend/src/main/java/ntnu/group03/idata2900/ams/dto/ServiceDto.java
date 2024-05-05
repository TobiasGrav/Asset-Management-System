package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Asset;

@Getter
@Setter
public class ServiceDto {

    private String intervalName;

    private String description;

    private Asset asset;

    private String serviceUrl;

    public ServiceDto(String intervalName, String description, Asset asset, String serviceUrl) {
        this.intervalName = intervalName;
        this.description = description;
        this.asset = asset;
        this.serviceUrl = serviceUrl;
    }

    public ServiceDto(){

    }
}
