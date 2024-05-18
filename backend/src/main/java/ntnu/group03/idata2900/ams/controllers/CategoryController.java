package ntnu.group03.idata2900.ams.controllers;

import lombok.extern.slf4j.Slf4j;
import ntnu.group03.idata2900.ams.dto.CategoryDto;
import ntnu.group03.idata2900.ams.model.Category;
import ntnu.group03.idata2900.ams.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private static final String CATEGORY_NOT_FOUND = "Category not found with id: {}";

    /**
     * Creates a new instance of CategoryController.
     *
     * @param categoryService categoryService
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns list of all categories in database
     *
     * @return List of all categories in database
     */
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    /**
     * Get a category from database matching given id if it exists.
     *
     * @param id potential id of a category
     * @return a ModelAndView containing category in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable int id) {
        Optional<Category> category = this.categoryService.getCategory(id);
        if (category.isEmpty()) {
            log.warn(CATEGORY_NOT_FOUND, id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Category found with ID: {}", id);
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
    }


    /**
     * Creates a new category.
     *
     * @param category The category object to be created.
     * @return ResponseEntity containing the created category and HTTP status code 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            log.info("Category created with ID: {}", createdCategory.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            log.error("Error creating category", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Updates an existing category.
     *
     * @param id             The ID of the category to be updated.
     * @param updatedCategory The updated category object.
     * @return ResponseEntity containing the updated category (Optional) and HTTP status code 200 (OK) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the category with the given ID doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody CategoryDto updatedCategory) {
        Optional<Category> existingCategory = categoryService.getCategory(id);
        if (existingCategory.isEmpty()) {
            log.warn(CATEGORY_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Category categoryToUpdate = existingCategory.get();
            categoryToUpdate.setName(updatedCategory.getName());
            categoryService.updateCategory(categoryToUpdate);
            log.info("Category updated with ID: {}", id);
            return new ResponseEntity<>(categoryToUpdate, HttpStatus.OK);
        }
    }

    /**
     * Deletes a category.
     *
     * @param id The ID of the category to be deleted.
     * @return ResponseEntity with HTTP status code 204 (NO_CONTENT) if successful,
     * or HTTP status code 404 (NOT_FOUND) if the category with the given ID doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable int id) {
        Optional<Category> existingCategory = categoryService.getCategory(id);
        if (existingCategory.isEmpty()) {
            log.warn(CATEGORY_NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            categoryService.deleteCategory(id);
            log.info("Category deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
