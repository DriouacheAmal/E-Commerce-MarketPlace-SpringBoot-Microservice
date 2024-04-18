package com.example.Catalogue.Dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class SubCategoryResponseDto {

    private Long subCategoryId;
    private String subCategoryName;
    private String imageUrl;
    private Long categoryId;
    private List<ProductResponseDto> products;

}
