package com.example.Order.service;

import com.example.Order.dto.RentalItemRequestDto;
import com.example.Order.dto.RentalItemResponseDto;
import com.example.Order.dto.RentalResponseDto;

import java.time.LocalDateTime;

public interface RentalItemService {
    RentalItemResponseDto getRentalItemById(Long rentalItemId);
    RentalItemResponseDto createRentalItem(RentalItemRequestDto rentalItemRequest);
    RentalItemResponseDto updateRentalItem(Long rentalItemId, RentalItemRequestDto rentalItemRequest);
    boolean isCarAvailableForReservation(Long rentalItemId,Long productId, LocalDateTime pickupDateTime, LocalDateTime returnDateTime);
    void deleteRentalItemById(Long rentalItemId) ;

}
