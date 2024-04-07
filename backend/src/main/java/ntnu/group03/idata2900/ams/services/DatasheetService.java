package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.DatasheetDto;
import ntnu.group03.idata2900.ams.model.Datasheet;
import ntnu.group03.idata2900.ams.repositories.DatasheetRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class DatasheetService {
    private final DatasheetRepository datasheetRepository;

    public DatasheetService(DatasheetRepository datasheetRepository) {
        this.datasheetRepository = datasheetRepository;
    }

    public List<Datasheet> getAll() {
        List<Datasheet> datasheets = new LinkedList<>();
        this.datasheetRepository.findAll().forEach(datasheets::add);
        return datasheets;
    }

    public Optional<Datasheet> getDatasheet(int id){
        return this.datasheetRepository.findById(id);
    }

    public Datasheet createDatasheet(DatasheetDto datasheetDto){
        Datasheet newDatasheet = new Datasheet(datasheetDto);
        return this.datasheetRepository.save(newDatasheet);
    }

    public void updateDatasheet(Datasheet datasheet) {
        this.datasheetRepository.save(datasheet);
    }

    public void deleteDatasheet(int id){
        this.datasheetRepository.deleteById(id);
    }
}
