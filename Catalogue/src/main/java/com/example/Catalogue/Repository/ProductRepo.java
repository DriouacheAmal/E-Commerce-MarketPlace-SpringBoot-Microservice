package com.example.Catalogue.Repository;

import com.example.Catalogue.Dto.ProductResponseDto;
import com.example.Catalogue.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    List<ProductResponseDto> findProductByProductName(String productName);

    @Query("SELECT new com.example.Catalogue.Dto.ProductResponseDto(" +
            "p.productId, p.productName, p.description, p.price, " +
            "p.isAvailable, p.imageUrl, p.subCategory.subCategoryId, p.category.categoryId) " +
            "FROM Product p WHERE LOWER(p.productName) LIKE %:keyword%")
    List<ProductResponseDto> findByKeyword(@Param("keyword") String keyword);


}
