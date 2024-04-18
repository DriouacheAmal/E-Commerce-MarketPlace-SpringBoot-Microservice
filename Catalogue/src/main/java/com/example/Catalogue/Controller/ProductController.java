package com.example.Catalogue.Controller;

import com.example.Catalogue.Dto.*;
import com.example.Catalogue.Excaption.DataNotFoundException;
import com.example.Catalogue.Excaption.SubCategoryNotFoundException;
import com.example.Catalogue.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private final ProductService productService;


    @GetMapping("/{ProductId}")
    public ResponseEntity<?> getProductById(@PathVariable Long ProductId) {
        try{
            ProductResponseDto productDto = productService.getProductById(ProductId);
            return ResponseEntity.ok(productDto);
        }catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");

        }
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productResponseDto = productService.getAllProducts();
        return ResponseEntity.ok(productResponseDto);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        try {
            ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
        } catch (SubCategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the product");
        }
    }

    @PutMapping("/{ProductId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long ProductId, @RequestBody ProductRequestDto productRequestDto) {
        try{
            ProductResponseDto productResponseDto = productService.updateProduct(ProductId, productRequestDto);
            return ResponseEntity.ok(productResponseDto);
        }catch (DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.ok("RentalItem with ID " + productId + " has been deleted.");
        }catch (DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");

        }

    }
    @GetMapping("/pagination/{offset}/{pageSize}")
    private ProductAPIResponse<Page<ProductResponseDto>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<ProductResponseDto> productsWithPagination = productService.findProductsWithPagination(offset, pageSize);
        return new ProductAPIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProductsByName(@RequestParam String keyword) {
        try {
            List<ProductResponseDto> products = productService.searchProductsByName(keyword);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/getByName")
    public ResponseEntity<?> getProductByName(@RequestParam String productName) {
        try{
            ProductResponseDto productDto = productService.getProductByName(productName);
            return ResponseEntity.ok(productDto);
        }catch(DataNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
