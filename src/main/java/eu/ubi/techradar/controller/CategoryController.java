package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Category;
import eu.ubi.techradar.exceptions.CategoryNotFoundException;
import eu.ubi.techradar.repository.CategoryRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository repository;
    Logger logger = getLogger(CategoryController.class);

    CategoryController() {}


    @GetMapping(value = "/categories")
    public List<Category> getAll(HttpServletResponse response) {
        List<Category> categorys = repository.findAll();
        response.setHeader("connection", "keep-alive");
        return categorys;
    }


    @GetMapping(value = "/category/{id}")
    public Category getCategory(@PathVariable Long id, HttpServletResponse response) {
        Category level = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(" id: " + id));
        response.setHeader("connection", "keep-alive");
        return level;
    }


    @PostMapping("/category")
    @CrossOrigin(origins = "http://localhost:8080")
    public Category newCategory(@RequestBody Category newCategory, HttpServletResponse response) {
        Category category = repository.save(newCategory);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("connection", "keep-alive");
        logger.info("Save category: " + category.getName() + " Id: "+ category.getId() + " Order number: " + category.getOrderNumber());
        return category;
    }


    @PutMapping("/category/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Category replaceCategory(@RequestBody Category newCategory, @PathVariable Long id) {

        Category updatedCategory = repository.findById(id)
            .map(category -> {
                category.setName(newCategory.getName());
                category.setDetails(newCategory.getDetails());
                category.setOrderNumber(newCategory.getOrderNumber());
                return repository.save(category);
            }).orElseGet(() -> {
                newCategory.setId(id);
                return repository.save(newCategory);
            });
        return updatedCategory;
    }


    @DeleteMapping("/category/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Category deleteCategory(@PathVariable Long id) {
        Category level = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("id: " + id));
        repository.deleteById(id);
        return level;
    }

}
