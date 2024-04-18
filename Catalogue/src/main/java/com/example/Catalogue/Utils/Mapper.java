package com.example.Catalogue.Utils;

import com.example.Catalogue.Dto.*;
import com.example.Catalogue.Entity.Category;
import com.example.Catalogue.Entity.Product;
import com.example.Catalogue.Entity.SubCategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();



    /*static {
        // Define explicit mapping for conflicting property setSubCategoryId() in ProductResponseDto
        modelMapper.addMappings(new PropertyMap<Product, ProductResponseDto>() {
            @Override
            protected void configure() {
                // Map Product's SubCategory's Category's CategoryId to ProductResponseDto's SubCategoryId
                map().setSubCategoryId(source.getSubCategory().getCategory().getCategoryId());
            }
        });


        modelMapper.addMappings(new PropertyMap<Product, ProductResponseDto>() {
            @Override
            protected void configure() {
                // Map Product's SubCategory's SubCategoryId to ProductResponseDto's SubCategoryId
                map().setSubCategoryId(source.getSubCategory().getSubCategoryId());
            }
        });
    }

     */

    /*static {
        // Define explicit mapping for conflicting property setSubCategoryId() in ProductResponseDto
        modelMapper.addMappings(new PropertyMap<Product, ProductResponseDto>() {
            @Override
            protected void configure() {
                // Map Product's SubCategory's Category's CategoryId to ProductResponseDto's SubCategoryId
                map().setSubCategoryId(source.getSubCategory().getCategory().getCategoryId());
            }
        });
    }

     */

    static {
    modelMapper.addMappings(new PropertyMap<Product, ProductResponseDto>() {
        @Override
        protected void configure() {
            // Map Product's SubCategory's SubCategoryId to ProductResponseDto's SubCategoryId
            map().setSubCategoryId(source.getSubCategory().getSubCategoryId());
        }
    });
}

    public static Product mapToProductEntity(ProductRequestDto productRequest){
        return modelMapper.map(productRequest, Product.class);
    }
    public static ProductResponseDto mapToProductDto(Product product){
        return modelMapper.map(product, ProductResponseDto.class);
    }
    public static Category mapToCategoryEntity(CategoryRequestDto categoryRequest){
        return modelMapper.map(categoryRequest, Category.class);
    }
    public static CategoryResponseDto mapToCategoryDto(Category category){
        return modelMapper.map(category, CategoryResponseDto.class);
    }
    public static SubCategory mapToSubCategoryEntity(SubCategoryRequestDto subcategoryDto){
        return modelMapper.map(subcategoryDto, SubCategory.class);
    }

    public static SubCategoryResponseDto mapToSubCategoryDto(SubCategory subcategory){
        return modelMapper.map(subcategory, SubCategoryResponseDto.class);
    }



    /*public static SubCategoryResponseDto mapToSubCategoryDto(SubCategory subcategory) {
        SubCategoryResponseDto subCategoryResponseDto = modelMapper.map(subcategory, SubCategoryResponseDto.class);

        // Map list of products to list of product response dto
        List<ProductResponseDto> productResponseDtos = subcategory.getProducts().stream()
                .map(product -> {
                    ProductResponseDto productDto = modelMapper.map(product, ProductResponseDto.class);
                    // Set the subCategoryId of the product to the subCategoryId of the parent subcategory
                    productDto.setSubCategoryId(subcategory.getSubCategoryId());
                    return productDto;
                })
                .collect(Collectors.toList());

        subCategoryResponseDto.setProducts(productResponseDtos);

        return subCategoryResponseDto;
    }

     */






    /*public static SubCategoryResponseDto mapToSubCategoryDto(SubCategory subcategory){
        SubCategoryResponseDto subCategoryResponseDto = modelMapper.map(subcategory, SubCategoryResponseDto.class);

        // Map list of products to list of product response dto
        List<ProductResponseDto> productResponseDtos = subcategory.getProducts().stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());

        subCategoryResponseDto.setProducts(productResponseDtos);

        return subCategoryResponseDto;
    }

     */

    /*public static SubCategoryResponseDto mapToSubCategoryDto(SubCategory subcategory) {
        SubCategoryResponseDto subCategoryResponseDto = modelMapper.map(subcategory, SubCategoryResponseDto.class);

        // Check if the list of products is not null before mapping
        if (subcategory.getProducts() != null) {
            // Map list of products to list of product response dto
            List<ProductResponseDto> productResponseDtos = subcategory.getProducts().stream()
                    .map(product -> {
                        ProductResponseDto productDto = modelMapper.map(product, ProductResponseDto.class);
                        // Set the subCategoryId of the product to the subCategoryId of the parent subcategory
                        productDto.setSubCategoryId(subcategory.getSubCategoryId()); // Assuming each product belongs to its parent subcategory
                        return productDto;
                    })
                    .collect(Collectors.toList());

            subCategoryResponseDto.setProducts(productResponseDtos);
        } else {
            // If the list of products is null, set an empty list for products in the response DTO
            subCategoryResponseDto.setProducts(Collections.emptyList());
        }

        return subCategoryResponseDto;
    }

     */



}