package ntnu.group03.idata2900.ams.services;

import ntnu.group03.idata2900.ams.dto.CategoryDto;
import ntnu.group03.idata2900.ams.model.Category;
import ntnu.group03.idata2900.ams.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Represents service for managing categories
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructor for CategoryService.
     *
     * @param categoryRepository categoryRepository
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Fetches all Category entities from the database.
     *
     * @return a list of all Category.
     */
    public List<Category> getAll() {
        List<Category> categories = new LinkedList<>();
        this.categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    /**
     * Fetches a Category by ID.
     *
     * @param id the ID of the Category.
     * @return an Optional containing the Category if found, or an empty Optional if not.
     */
    public Optional<Category> getCategory(int id) {
        return this.categoryRepository.findById(id);
    }

    /**
     * Creates a new Category entity based on the given DTO.
     *
     * @param categoryDto the DTO containing data for the new Category.
     * @return the created Category.
     */
    public Category createCategory(CategoryDto categoryDto) {
        Category newCategory = new Category(categoryDto);
        return this.categoryRepository.save(newCategory);
    }

    /**
     * Updates an existing Category.
     *
     * @param category the Category to update.
     */
    public void updateCategory(Category category) {
        this.categoryRepository.save(category);
    }

    /**
     * Deletes a Category by ID.
     *
     * @param id the ID of the Category to delete.
     */
    public void deleteCategory(int id) {
        this.categoryRepository.deleteById(id);
    }
}
