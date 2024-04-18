package com.example.Catalogue.Controller;

import com.example.Catalogue.Dto.SubCategoryRequestDto;
import com.example.Catalogue.Dto.SubCategoryResponseDto;
import com.example.Catalogue.Excaption.CategoryNotFoundException;
import com.example.Catalogue.Excaption.DataNotFoundException;
import com.example.Catalogue.Repository.CategoryRepository;
import com.example.Catalogue.Service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@AllArgsConstructor
public class SubCategoryController {
    @Autowired
    private final SubCategoryService subCategoryService;
    private final CategoryRepository categoryRepository;




    @GetMapping("/{subCategoryId}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable Long subCategoryId) {
        try {
            SubCategoryResponseDto subCategory = subCategoryService.getSubCategoryById(subCategoryId);
            return ResponseEntity.ok(subCategory);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<SubCategoryResponseDto>> getAllSubCategories() {
        List<SubCategoryResponseDto> subCategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(subCategories);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubCategory(@RequestBody SubCategoryRequestDto subCategoryRequest) {
        try {
            Long categoryId = subCategoryRequest.getCategoryId();
            if (!categoryRepository.existsById(categoryId)) {
                throw new CategoryNotFoundException("Category with ID " + categoryId + " does not exist");
            }
            SubCategoryResponseDto createdSubCategory = subCategoryService.createSubCategory(subCategoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubCategory);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the subcategory");
        }
    }

    @PutMapping("/{subCategoryId}")
    public ResponseEntity<?> updateSubCategory(@PathVariable Long subCategoryId, @RequestBody SubCategoryRequestDto subCategoryRequest) {
        try{
            SubCategoryResponseDto updatedSubCategory = subCategoryService.updateSubCategory(subCategoryId, subCategoryRequest);
            return ResponseEntity.ok(updatedSubCategory);
        }catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable Long subCategoryId) {
        try {
            subCategoryService.deleteSubCategory(subCategoryId);
            return ResponseEntity.ok().body("the SubCategory "+subCategoryId+" is deleted ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubCategory not found.");
        }
    }
}
