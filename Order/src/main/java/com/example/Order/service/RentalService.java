package com.example.Order.service;

import com.example.Order.dto.RentalRequestDto;
import com.example.Order.dto.RentalResponseDto;

import java.util.List;

public interface RentalService {
    List<RentalResponseDto> getAllRentals();
    RentalResponseDto getRentalById(Long rentalId);
    RentalResponseDto createRental(RentalRequestDto rentalRequest);
    RentalResponseDto updateRental(Long rentalId, RentalRequestDto rentalRequest);
    //void cancelRental();
    void cancelRentalById(Long rentalId);
    List<RentalResponseDto> getRentalHistoryByCustomerId(Long customerId);
}
