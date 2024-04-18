package com.example.Catalogue.Dto;

import com.example.Catalogue.Entity.Category;
import com.example.Catalogue.Entity.SubCategory;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto implements Serializable {
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private String imageUrl;
    private Long subCategoryId;
    private Long categoryId;
}
