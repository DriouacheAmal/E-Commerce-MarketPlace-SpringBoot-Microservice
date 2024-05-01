package com.example.Catalogue.Repository;

import com.example.Catalogue.Dto.SubCategoryResponseDto;
import com.example.Catalogue.Entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findByCategory_CategoryId(Long categoryId);
}
