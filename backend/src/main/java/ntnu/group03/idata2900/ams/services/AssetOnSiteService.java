package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.AssetOnSiteDto;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.repositories.AssetOnSiteRepository;
import ntnu.group03.idata2900.ams.repositories.AssetRepository;
import ntnu.group03.idata2900.ams.repositories.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a service class for managing assets on site
 */
@Service
public class AssetOnSiteService {

    private final AssetOnSiteRepository assetOnSiteRepository;
    private final ServiceRepository serviceRepository;

    private final AssetRepository assetRepository;

    /**
     * Constructor for AssetOnSiteService.
     *
     * @param assetOnSiteRepository assetOnSiteRepository
     * @param serviceRepository serviceRepository
     * @param assetRepository assetRepository
     */
    public AssetOnSiteService(AssetOnSiteRepository assetOnSiteRepository, ServiceRepository serviceRepository, AssetRepository assetRepository) {
        this.assetOnSiteRepository = assetOnSiteRepository;
        this.serviceRepository = serviceRepository;
        this.assetRepository = assetRepository;
    }

    /**
     * Fetches all AssetOnSites from the database.
     *
     * @return a list of all AssetOnSites.
     */
    public List<AssetOnSite> getAll() {
        List<AssetOnSite> assetOnSites = new LinkedList<>();
        this.assetOnSiteRepository.findAll().forEach(assetOnSites::add);
        return assetOnSites;
    }

    /**
     * Fetches the services associated with a specific AssetOnSite.
     *
     * @param assetOnSite the AssetOnSite.
     * @return a list of services associated with the given AssetOnSite.
     */
    public List<ntnu.group03.idata2900.ams.model.Service> getServicesFromAOS(AssetOnSite assetOnSite){
        Asset asset = this.assetRepository.findAssetByAssetOnSites(assetOnSite).orElse(null);
        return this.serviceRepository.findServicesByAsset(asset).orElse(null);
    }

    /**
     * Fetches an AssetOnSite by its ID.
     *
     * @param id the ID of the AssetOnSite entity.
     * @return an Optional containing the AssetOnSite entity if found, or an empty Optional if not.
     */
    public Optional<AssetOnSite> getAssetOnSite(int id){
        return this.assetOnSiteRepository.findById(id);
    }

    /**
     * Creates new AssetOnSite based on the given DTO.
     *
     * @param assetOnSiteDto the DTO containing data for the new AssetOnSite entities.
     * @return the last created AssetOnSite, or null if the amount is invalid.
     */
    public AssetOnSite createAssetOnSite(AssetOnSiteDto assetOnSiteDto){
        if (assetOnSiteDto.getAmount() > 1000 || assetOnSiteDto.getAmount() < 0){
            return null;
        }
        AssetOnSite assetOnSite = null;
        for (int i = 0; i < assetOnSiteDto.getAmount(); i++){
            assetOnSite = new AssetOnSite(assetOnSiteDto);
            this.assetOnSiteRepository.save(assetOnSite);
        }
        return assetOnSite;
    }

    /**
     * Updates an existing AssetOnSite.
     *
     * @param assetOnSite the AssetOnSite to update.
     */
    public void updateAssetOnSite(AssetOnSite assetOnSite) {
        this.assetOnSiteRepository.save(assetOnSite);
    }

    /**
     * Deletes an AssetOnSite by its ID.
     *
     * @param id the ID of the AssetOnSite to delete.
     */
    public void deleteAssetOnSite(int id){
        this.assetOnSiteRepository.deleteById(id);
    }
}
