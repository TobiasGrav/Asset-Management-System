package ntnu.group03.idata2900.ams.dto;



import ntnu.group03.idata2900.ams.model.Category;
import ntnu.group03.idata2900.ams.model.Datasheet;
import ntnu.group03.idata2900.ams.model.Site;

import java.time.LocalDateTime;

public class AssetDto {

    private String name;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime commissionDate;

    private String qrCode;

    private boolean active;
    private Category category;
    private Site site;
    private Datasheet datasheet;

    public AssetDto(String name, String description, LocalDateTime creationDate, LocalDateTime commissionDate, String qrCode, boolean active, Category category, Site site, Datasheet datasheet) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.commissionDate = commissionDate;
        this.qrCode = qrCode;
        this.active = active;
        this.category = category;
        this.site = site;
        this.datasheet = datasheet;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getCommissionDate() {
        return commissionDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public boolean isActive() {
        return active;
    }

    public Category getCategory() {
        return category;
    }

    public Site getSite() {
        return site;
    }

    public Datasheet getDatasheet() {
        return datasheet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setCommissionDate(LocalDateTime commissionDate) {
        this.commissionDate = commissionDate;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public void setDatasheet(Datasheet datasheet) {
        this.datasheet = datasheet;
    }
}
