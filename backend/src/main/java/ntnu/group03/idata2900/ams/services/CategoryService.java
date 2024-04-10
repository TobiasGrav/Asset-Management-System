package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.CategoryDto;
import ntnu.group03.idata2900.ams.model.Category;
import ntnu.group03.idata2900.ams.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        List<Category> categories = new LinkedList<>();
        this.categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Optional<Category> getCategory(int id){
        return this.categoryRepository.findById(id);
    }

    public Category createCategory(CategoryDto categoryDto){
        Category newCategory = new Category(categoryDto);
        return this.categoryRepository.save(newCategory);
    }

    public void updateCategory(Category category) {
        this.categoryRepository.save(category);
    }

    public void deleteCategory(int id){
        this.categoryRepository.deleteById(id);
    }
}
