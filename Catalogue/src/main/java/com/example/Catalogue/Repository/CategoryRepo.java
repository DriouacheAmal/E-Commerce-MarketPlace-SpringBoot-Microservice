package com.example.Catalogue.Repository;

import com.example.Catalogue.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CategoryRepo extends JpaRepository<Category, Long> {
}
