package com.example.Catalogue.Dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CategoryResponseDto {
    private long categoryId;
    private String categoryName;
    private String imageUrl;
    private List<SubCategoryResponseDto> subCategories;
    //private List<ProductResponseDto> products;
}



