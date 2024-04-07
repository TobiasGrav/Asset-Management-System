package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.SparePartDto;
import ntnu.group03.idata2900.ams.model.SparePart;
import ntnu.group03.idata2900.ams.repositories.SparePartRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SparePartService {

    private final SparePartRepository sparePartRepository;

    public SparePartService(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    public List<SparePart> getAll() {
        List<SparePart> spareParts = new LinkedList<>();
        this.sparePartRepository.findAll().forEach(spareParts::add);
        return spareParts;
    }

    public Optional<SparePart> getSparePart(int id){
        return this.sparePartRepository.findById(id);
    }


    public SparePart createSparePart(SparePartDto sparePartDto){
        SparePart newSparePart = new SparePart(sparePartDto);
        return this.sparePartRepository.save(newSparePart);
    }

    public void updateSparePart(SparePart sparePart) {
        this.sparePartRepository.save(sparePart);
    }

    public void deleteSparePart(int id){
        this.sparePartRepository.deleteById(id);
    }
}
