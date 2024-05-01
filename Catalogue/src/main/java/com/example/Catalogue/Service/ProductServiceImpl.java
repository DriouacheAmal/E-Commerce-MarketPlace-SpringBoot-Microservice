package com.example.Catalogue.Service;

import com.example.Catalogue.Dto.ProductRequestDto;
import com.example.Catalogue.Dto.ProductResponseDto;
import com.example.Catalogue.Dto.SubCategoryResponseDto;
import com.example.Catalogue.Entity.Product;
import com.example.Catalogue.Entity.SubCategory;
import com.example.Catalogue.Excaption.CategoryNotFoundException;
import com.example.Catalogue.Excaption.DataNotFoundException;
import com.example.Catalogue.Excaption.SubCategoryNotFoundException;
import com.example.Catalogue.Repository.ProductRepo;
import com.example.Catalogue.Repository.SubCategoryRepo;
import com.example.Catalogue.Utils.Mapper;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepo productRepository;
    private final SubCategoryRepo subCategoryRepository;


    @Override
    public ProductResponseDto getProductById(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        return Mapper.mapToProductDto(product);
    }

    @Override
    public List<ProductResponseDto> getProductsBySubCategoryId(Long subCategoryId){
        if (!subCategoryRepository.existsById(subCategoryId)) {
            throw new DataNotFoundException("SubcategoryID " + subCategoryId +" not found: " );
        }

        // Fetch subcategories by category ID
        List<Product> products = productRepository.findBySubCategory_SubCategoryId(subCategoryId);

        // Convert the list of SubCategory entities to a list of SubCategoryResponseDto
        List<ProductResponseDto> productsDtos = products.stream()
                .map(Mapper::mapToProductDto)
                .collect(Collectors.toList());

        return productsDtos;
    }


    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Mapper::mapToProductDto)
                .collect(Collectors.toList());

    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequest) {
        Long subCategoryId=  productRequest.getSubCategoryId();
        if (!subCategoryRepository.existsById(subCategoryId)) {
            throw new SubCategoryNotFoundException("SubCategory with ID " + subCategoryId + " does not exist");
        }
        var product = Mapper.mapToProductEntity(productRequest);
        return Mapper.mapToProductDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDto){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setAvailable(productDto.isAvailable());
        existingProduct.setDescription(productDto.getDescription());
        Product updatedProduct = productRepository.save(existingProduct);
        return Mapper.mapToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId){
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto getProductByName(String productName) {
        Product product = productRepository.findByProductName(productName);
        if (product == null) {
            throw new DataNotFoundException("Product not found with name: " + productName);
        }
        return Mapper.mapToProductDto(product);
    }

    @Override
    public List<ProductResponseDto> searchProductsByName(String keyword) {
        List<ProductResponseDto> products = productRepository.findByKeyword(keyword);
        return products;
    }

    @Override
    public Page<ProductResponseDto> findProductsWithPagination(int offset, int pageSize){
        Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize));
        return  products.map(Mapper::mapToProductDto);
    }
}
