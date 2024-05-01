package com.example.Catalogue.Controller;

import com.example.Catalogue.Dto.CategoryRequestDto;
import com.example.Catalogue.Dto.CategoryResponseDto;
import com.example.Catalogue.Dto.ProductResponseDto;
import com.example.Catalogue.Entity.Category;
import com.example.Catalogue.Excaption.DataNotFoundException;
import com.example.Catalogue.Excaption.DuplicateKeyException;
import com.example.Catalogue.Service.CategoryService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/catalogues/api/categories")

public class CategoryController {
    @Autowired
    private final CategoryService categoryService;



    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        try{
            CategoryResponseDto categoryDto = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(categoryDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
        }

    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequest) {
        try {
            CategoryResponseDto createdCategory = categoryService.createCategory(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);

        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category name already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the category");
        }
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto categoryRequest) {
        /*CategoryResponseDto updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
        }

         */

        try {
            CategoryResponseDto updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category name must be unique");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the category");
        }
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok().body("the Category "+categoryId+" is deleted ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
        }
    }
}
