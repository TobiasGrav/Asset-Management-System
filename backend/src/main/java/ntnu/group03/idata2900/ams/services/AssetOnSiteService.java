package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.AssetOnSiteDto;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.repositories.AssetOnSiteRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetOnSiteService {

    private final AssetOnSiteRepository assetOnSiteRepository;

    public AssetOnSiteService(AssetOnSiteRepository assetOnSiteRepository) {
        this.assetOnSiteRepository = assetOnSiteRepository;
    }

    public List<AssetOnSite> getAll() {
        List<AssetOnSite> assetOnSites = new LinkedList<>();
        this.assetOnSiteRepository.findAll().forEach(assetOnSites::add);
        return assetOnSites;
    }

    public Optional<AssetOnSite> getAssetOnSite(int id){
        return this.assetOnSiteRepository.findById(id);
    }

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

    public void updateAssetOnSite(AssetOnSite assetOnSite) {
        this.assetOnSiteRepository.save(assetOnSite);
    }

    public void deleteAssetOnSite(int id){
        this.assetOnSiteRepository.deleteById(id);
    }
}
