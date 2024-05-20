package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.DatasheetDto;
import ntnu.group03.idata2900.ams.model.Datasheet;
import ntnu.group03.idata2900.ams.repositories.DatasheetRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing datasheets.
 */
@Service
public class DatasheetService {

    private final DatasheetRepository datasheetRepository;

    /**
     * Constructor for DatasheetService.
     *
     * @param datasheetRepository datasheetRepository
     */
    public DatasheetService(DatasheetRepository datasheetRepository) {
        this.datasheetRepository = datasheetRepository;
    }

    /**
     * Fetches all Datasheet entities from the database.
     *
     * @return a list of all Datasheet.
     */
    public List<Datasheet> getAll() {
        List<Datasheet> datasheets = new LinkedList<>();
        this.datasheetRepository.findAll().forEach(datasheets::add);
        return datasheets;
    }

    /**
     * Fetches a Datasheet by ID.
     *
     * @param id the ID of the Datasheet.
     * @return an Optional containing the Datasheet if found, or an empty Optional if not.
     */
    public Optional<Datasheet> getDatasheet(int id) {
        return this.datasheetRepository.findById(id);
    }

    /**
     * Creates a new Datasheet entity based on the given DTO.
     *
     * @param datasheetDto the DTO containing data for the new Datasheet.
     * @return the created Datasheet.
     */
    public Datasheet createDatasheet(DatasheetDto datasheetDto) {
        Datasheet newDatasheet = new Datasheet(datasheetDto);
        return this.datasheetRepository.save(newDatasheet);
    }

    /**
     * Updates an existing Datasheet.
     *
     * @param datasheet the Datasheet to update.
     */
    public void updateDatasheet(Datasheet datasheet) {
        this.datasheetRepository.save(datasheet);
    }

    /**
     * Deletes a Datasheet by ID.
     *
     * @param id the ID of the Datasheet to delete.
     */
    public void deleteDatasheet(int id) {
        this.datasheetRepository.deleteById(id);
    }
}
