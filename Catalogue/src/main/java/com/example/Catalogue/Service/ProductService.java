package com.example.Catalogue.Service;

import com.example.Catalogue.Dto.ProductRequestDto;
import com.example.Catalogue.Dto.ProductResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponseDto getProductById (Long productId);
    public List<ProductResponseDto> getProductsBySubCategoryId(Long subCategoryId);
    List<ProductResponseDto> getAllProducts ();
    ProductResponseDto createProduct (ProductRequestDto productRequest);
    ProductResponseDto updateProduct (Long productId, ProductRequestDto productRequest);
    void deleteProduct (Long productId);
    List<ProductResponseDto> searchProductsByName(String keyword);
    ProductResponseDto getProductByName(String productName);
    Page<ProductResponseDto> findProductsWithPagination(int offset, int pageSize);
}
