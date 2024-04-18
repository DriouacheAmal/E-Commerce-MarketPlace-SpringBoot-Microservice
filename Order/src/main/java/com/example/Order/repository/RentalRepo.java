package com.example.Order.repository;

import com.example.Order.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepo extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomerId(Long customerId);
}
