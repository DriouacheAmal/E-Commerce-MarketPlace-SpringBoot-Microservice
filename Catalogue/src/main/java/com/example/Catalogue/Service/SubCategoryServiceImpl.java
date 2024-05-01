package com.example.Catalogue.Service;


import com.example.Catalogue.Dto.SubCategoryRequestDto;
import com.example.Catalogue.Dto.SubCategoryResponseDto;
import com.example.Catalogue.Entity.SubCategory;
import com.example.Catalogue.Excaption.CategoryNotFoundException;
import com.example.Catalogue.Excaption.DataNotFoundException;
import com.example.Catalogue.Repository.CategoryRepository;
import com.example.Catalogue.Repository.SubCategoryRepo;
import com.example.Catalogue.Utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
public class SubCategoryServiceImpl implements SubCategoryService{
    @Autowired
    private final SubCategoryRepo subCategoryRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;


    @Override
    public SubCategoryResponseDto getSubCategoryById(Long subCategoryId) {
        var subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new DataNotFoundException("SubCategory not found"));
        return Mapper.mapToSubCategoryDto(subCategory);
    }

    @Override
    public List<SubCategoryResponseDto> getSubCategoriesByCategoryId(Long categoryId){
        if (!categoryRepository.existsById(categoryId)) {
            throw new DataNotFoundException("CategoryID " + categoryId +" not found: " );
        }

        // Fetch subcategories by category ID
        List<SubCategory> subCategories = subCategoryRepository.findByCategory_CategoryId(categoryId);

        // Convert the list of SubCategory entities to a list of SubCategoryResponseDto
        List<SubCategoryResponseDto> subCategoryResponseDtos = subCategories.stream()
                .map(Mapper::mapToSubCategoryDto)
                .collect(Collectors.toList());

        return subCategoryResponseDtos;
    }


    @Override
    public List<SubCategoryResponseDto> getAllSubCategories() {
        return subCategoryRepository.findAll()
                .stream()
                .map(Mapper::mapToSubCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubCategoryResponseDto createSubCategory(SubCategoryRequestDto subcategoryRequest) {
        Long categoryId = subcategoryRequest.getCategoryId();
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category with ID " + categoryId + " does not exist");
        }

        SubCategory subCategory = Mapper.mapToSubCategoryEntity(subcategoryRequest);
        return Mapper.mapToSubCategoryDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public SubCategoryResponseDto updateSubCategory(Long subCategoryId,SubCategoryRequestDto subcategoryRequest) {
        var subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new DataNotFoundException("SubCategory not found"));
        subCategory.setSubCategoryName(subcategoryRequest.getSubCategoryName());
        return Mapper.mapToSubCategoryDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public void deleteSubCategory(Long subCategoryId) throws Exception {
        var subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new Exception("SubCategory not found"));
        subCategoryRepository.delete(subCategory);
    }

}
