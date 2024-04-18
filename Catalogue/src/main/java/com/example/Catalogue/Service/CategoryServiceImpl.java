package com.example.Catalogue.Service;

import com.example.Catalogue.Dto.CategoryRequestDto;
import com.example.Catalogue.Dto.CategoryResponseDto;
import com.example.Catalogue.Entity.Category;
import com.example.Catalogue.Excaption.DataNotFoundException;
import com.example.Catalogue.Excaption.DuplicateKeyException;
import com.example.Catalogue.Repository.CategoryRepo;
import com.example.Catalogue.Utils.Mapper;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private final CategoryRepo categoryRepository;

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        return Mapper.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Mapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        try {
            Category category = Mapper.mapToCategoryEntity(categoryRequestDto);
            Category savedCategory = categoryRepository.save(category);
            return Mapper.mapToCategoryDto(savedCategory);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("Category name must be unique", e);
        }
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryID, CategoryRequestDto categoryRequest) {
        try{
            Category category= categoryRepository.findById(categoryID)
                    .orElseThrow(() -> new DataNotFoundException("Category not found"));
            category.setCategoryName(categoryRequest.getCategoryName());
            //category.setImageUrl(categoryRequest.getImageUrl()));
            return Mapper.mapToCategoryDto(categoryRepository.save(category));
        }catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("Category name must be unique", e);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) throws Exception {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new Exception("Category not found"));
        categoryRepository.delete(category);

    }
}
