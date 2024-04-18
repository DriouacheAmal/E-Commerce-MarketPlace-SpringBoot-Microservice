package com.example.Catalogue.Dto;


import com.example.Catalogue.Entity.Product;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class SubCategoryRequestDto {
    private Long subCategoryId;
    private String subCategoryName;
    private String imageUrl;
    private Long categoryId;
}
