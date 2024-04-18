package com.example.Catalogue.Service;

import com.example.Catalogue.Dto.CategoryRequestDto;
import com.example.Catalogue.Dto.CategoryResponseDto;
import com.example.Catalogue.Entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto getCategoryById(Long categoryId);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequest);
    CategoryResponseDto updateCategory(Long categoryID, CategoryRequestDto categoryRequest);

    void deleteCategory(Long categoryId) throws Exception;

}
