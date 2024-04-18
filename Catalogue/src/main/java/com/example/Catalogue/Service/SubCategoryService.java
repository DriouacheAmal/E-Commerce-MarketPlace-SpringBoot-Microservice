package com.example.Catalogue.Service;

import com.example.Catalogue.Dto.SubCategoryRequestDto;
import com.example.Catalogue.Dto.SubCategoryResponseDto;
import com.example.Catalogue.Entity.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {


    SubCategoryResponseDto getSubCategoryById(Long subCategoryId);
    List<SubCategoryResponseDto> getAllSubCategories();
    SubCategoryResponseDto createSubCategory(SubCategoryRequestDto subcategoryRequest);
    SubCategoryResponseDto updateSubCategory(Long subCategoryId,SubCategoryRequestDto subcategoryRequest);
    void deleteSubCategory(Long subCategoryId) throws Exception;
}
