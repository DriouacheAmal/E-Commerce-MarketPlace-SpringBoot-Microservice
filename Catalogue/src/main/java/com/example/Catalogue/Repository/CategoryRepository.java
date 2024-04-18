package com.example.Catalogue.Repository;

import com.example.Catalogue.Entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    //Category getById(Long id);
}
