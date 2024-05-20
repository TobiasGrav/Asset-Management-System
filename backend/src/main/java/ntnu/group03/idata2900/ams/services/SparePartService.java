package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.SparePartDto;
import ntnu.group03.idata2900.ams.model.SparePart;
import ntnu.group03.idata2900.ams.repositories.SparePartRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing spare parts.
 */
@Service
public class SparePartService {

    private final SparePartRepository sparePartRepository;

    /**
     * Constructor for SparePartService.
     *
     * @param sparePartRepository sparePartRepository
     */
    public SparePartService(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    /**
     * Fetches all SparePart entities from the database.
     *
     * @return a list of all SparePart.
     */
    public List<SparePart> getAll() {
        List<SparePart> spareParts = new LinkedList<>();
        this.sparePartRepository.findAll().forEach(spareParts::add);
        return spareParts;
    }

    /**
     * Fetches a SparePart by ID.
     *
     * @param id the ID of the SparePart.
     * @return an Optional containing the SparePart if found, or an empty Optional if not.
     */
    public Optional<SparePart> getSparePart(int id) {
        return this.sparePartRepository.findById(id);
    }

    /**
     * Creates a new SparePart entity based on the given DTO.
     *
     * @param sparePartDto the DTO containing data for the new SparePart.
     * @return the created SparePart.
     */
    public SparePart createSparePart(SparePartDto sparePartDto) {
        SparePart newSparePart = new SparePart(sparePartDto);
        return this.sparePartRepository.save(newSparePart);
    }

    /**
     * Updates an existing SparePart.
     *
     * @param sparePart the SparePart to update.
     */
    public void updateSparePart(SparePart sparePart) {
        this.sparePartRepository.save(sparePart);
    }

    /**
     * Deletes a SparePart by ID.
     *
     * @param id the ID of the SparePart to delete.
     */
    public void deleteSparePart(int id) {
        this.sparePartRepository.deleteById(id);
    }
}
