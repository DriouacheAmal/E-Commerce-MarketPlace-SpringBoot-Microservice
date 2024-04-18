package com.example.Catalogue.Dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private String imageUrl;
    private Long subCategoryId;
    private Long categoryId;
}
