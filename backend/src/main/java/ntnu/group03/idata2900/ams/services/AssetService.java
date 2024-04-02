package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.AssetDto;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.repositories.AssetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAll() {
        List<Asset> assets = new LinkedList<>();
        this.assetRepository.findAll().forEach(assets::add);
        return assets;
    }

    public Optional<Asset> getAsset(int id){
        return this.assetRepository.findById(id);
    }

    public Iterable<Asset> getFirstNAssetsFromDatabase(int n){
        return this.assetRepository.findAll(PageRequest.of(0,n));
    }

    public Asset createAsset(AssetDto assetDto){
        Asset newAsset = new Asset();
        newAsset.setCreationDate(LocalDateTime.now());
        newAsset.setActive(true);
        newAsset.setName(assetDto.getName());
        newAsset.setDescription(assetDto.getDescription());
        newAsset.setQrCode(assetDto.getQrCode());
        newAsset.setCategory(assetDto.getCategory());
        newAsset.setSite(assetDto.getSite());
        newAsset.setDatasheet(assetDto.getDatasheet());

        return this.assetRepository.save(newAsset);
    }

    public void updateAsset(Asset asset) {
        this.assetRepository.save(asset);
    }

    public void deleteAsset(int id){
        this.assetRepository.deleteById(id);
    }
}
