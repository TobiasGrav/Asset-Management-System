package ntnu.group03.idata2900.ams.repositories;

import ntnu.group03.idata2900.ams.model.Asset;
import ntnu.group03.idata2900.ams.model.AssetOnSite;
import ntnu.group03.idata2900.ams.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Integer> {

    /**
     * Retrieves a list of assets from the database based on the provided asset name.
     *
     * @param assetName The name of the asset.
     * @return An Optional containing the asset list with the given name, or empty if no assets are found.
     */
    Optional<List<Asset>> findAssetsByName(String assetName);

    /**
     * Retrieves a list of assets from the database based on the provided category.
     *
     * @param category The category of the asset.
     * @return An Optional containing the asset list with the given category, or empty if no assets are found.
     */
    Optional<List<Asset>> findAssetsByCategory(Category category);

    /**
     * Retrieves an asset from the database based on the provided AssetOnSite instance.
     *
     * @param assetOnSite The AssetOnSite instance associated with the asset.
     * @return An Optional containing the asset associated with the given AssetOnSite, or empty if no asset is found.
     */
    Optional<Asset> findAssetByAssetOnSites(AssetOnSite assetOnSite);


    /**
     * Finding all assets using category and pagination
     *
     * @param category The category of the asset.
     * @param pageable pagination configuration using limit and offset.
     * @return return all assets with given category and paging
     */
    Page<Asset> findByCategory(Category category, Pageable pageable);

    /**
     * Finding all assets using pagination
     *
     * @param pageable pagination configuration using limit and offset.
     * @return return all assets with given paging
     */
    Page<Asset> findAll(Pageable pageable);

}
