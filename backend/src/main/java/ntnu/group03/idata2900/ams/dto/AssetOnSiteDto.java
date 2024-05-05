package ntnu.group03.idata2900.ams.dto;

import lombok.Getter;
import lombok.Setter;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.model.Site;

import java.time.LocalDateTime;

@Setter
@Getter
public class AssetOnSiteDto {

    private LocalDateTime commissionDate;

    private Site site;

    private Asset asset;
    private int amount;

    private String assetOnSiteTag;

    public AssetOnSiteDto(LocalDateTime commissionDate, Site site, Asset asset, int amount, String assetOnSiteTag) {
        this.commissionDate = commissionDate;
        this.site = site;
        this.asset = asset;
        this.amount = amount;
        this.assetOnSiteTag = assetOnSiteTag;
    }

    public AssetOnSiteDto(){

    }
}
