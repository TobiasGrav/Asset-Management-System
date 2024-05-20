package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.AssetDto;
import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.repositories.AssetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing assets
 */
@Service
public class AssetService {

    private final AssetRepository assetRepository;

    /**
     * Constructor for AssetService.
     *
     * @param assetRepository assetRepository
     */
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    /**
     * Fetches all Asset entities from the database.
     *
     * @return a list of all Asset entities.
     */
    public List<Asset> getAll() {
        List<Asset> assets = new LinkedList<>();
        this.assetRepository.findAll().forEach(assets::add);
        return assets;
    }

    /**
     * Fetches an Asset by ID.
     *
     * @param id the ID of the Asset.
     * @return an Optional containing the Asset entity if found, or an empty Optional if not.
     */
    public Optional<Asset> getAsset(int id) {
        return this.assetRepository.findById(id);
    }

    /**
     * Fetches the first N Asset entities from the database.
     *
     * @param n the number of Asset entities to retrieve.
     * @return an iterable containing the first N Assets.
     */
    public Iterable<Asset> getFirstNAssetsFromDatabase(int n) {
        return this.assetRepository.findAll(PageRequest.of(0, n));
    }

    /**
     * Creates a new Asset entity based on the given DTO.
     *
     * @param assetDto the DTO containing data for the new Asset.
     * @return the created Asset.
     */
    public Asset createAsset(AssetDto assetDto) {
        Asset newAsset = new Asset(assetDto);
        return this.assetRepository.save(newAsset);
    }

    /**
     * Updates an existing Asset.
     *
     * @param asset the Asset to update.
     */
    public void updateAsset(Asset asset) {
        this.assetRepository.save(asset);
    }

    /**
     * Deletes an Asset by ID.
     *
     * @param id the ID of the Asset to delete.
     */
    public void deleteAsset(int id) {
        this.assetRepository.deleteById(id);
    }
}
