package com.example.Order.repository;

import com.example.Order.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Catalogue")
public interface ProductClient {
    @GetMapping("/catalogues/api/products/{productId}")
    ProductDto getProductById(@PathVariable("productId") Long productId);
}
