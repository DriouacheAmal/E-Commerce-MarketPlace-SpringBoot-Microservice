package com.example.User.FeignClient;

import com.example.User.Entity.DTO.Category;
import com.example.User.Exceptions.UserNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="Category")
public interface CategoryClient {
    @GetMapping("/users/{userId}")
    List<Category> findByUserId(
            @PathVariable("userId") Long userId) throws UserNotFoundException;
    @DeleteMapping("/category/{id}")
    String deleteTask(
            @PathVariable Long id) throws UserNotFoundException;
}
