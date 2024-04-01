package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.repositories.AssetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Asset createAsset(Asset asset){
        return this.assetRepository.save(asset);
    }

    public void updateAsset(Asset asset) {
        this.assetRepository.save(asset);
    }

    public void deleteAsset(int id){
        this.assetRepository.deleteById(id);
    }
}
