package com.example.Order.service;

import com.example.Order.dto.RentalItemRequestDto;
import com.example.Order.dto.RentalItemResponseDto;
import com.example.Order.entity.RentalItem;
import com.example.Order.exception.RentalItemAlreadyReservedException;
import com.example.Order.exception.RentalItemNotAvailableException;
import com.example.Order.exception.RentalItemNotFoundException;
import com.example.Order.mapper.MappingProfile;
import com.example.Order.repository.RentalItemRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor

public class RentalItemServiceImpl implements RentalItemService{

    private final RentalItemRepo rentalItemRepository;
    @Override
    public RentalItemResponseDto getRentalItemById(Long rentalItemId) {
        var rentalItem = rentalItemRepository.findById(rentalItemId)
                .orElseThrow(() -> new RentalItemNotFoundException("RentalItem not found"));
        return MappingProfile.mapToRentalItemDto(rentalItem);
    }

    @Override
    public boolean isCarAvailableForReservation(Long rentalItemId, Long productId, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        List<RentalItem> overlappingRentals = rentalItemRepository.findOverlappingRentals(rentalItemId, productId, pickupDateTime, returnDateTime);
        return overlappingRentals.isEmpty();
    }

    @Override
    public RentalItemResponseDto createRentalItem(RentalItemRequestDto rentalItemRequest) {
        Long rentalItemId = rentalItemRequest.getRentalItemId();
        Long productId = rentalItemRequest.getProductId();
        LocalDateTime pickupDateTime = rentalItemRequest.getPickupDateTime();
        LocalDateTime returnDateTime = rentalItemRequest.getReturnDateTime();

        if (pickupDateTime == null || returnDateTime == null || pickupDateTime.isAfter(returnDateTime)) {
            throw new IllegalArgumentException("Invalid pickupDateTime or returnDateTime");
        }

        if (!isCarAvailableForReservation(rentalItemId, productId, pickupDateTime, returnDateTime)) {
            throw new RentalItemNotAvailableException("The car is not available for reservation during the specified period.");
        }

        if (!isCarAvailableForReservation(null, productId, pickupDateTime, returnDateTime)) {
            throw new RentalItemAlreadyReservedException("The car is already reserved by another customer during the specified period.");
        }
        long hours = pickupDateTime.until(returnDateTime, java.time.temporal.ChronoUnit.HOURS);
        int totalDays = (int) Math.ceil(hours / 24.0);

        BigDecimal pricePerDay = rentalItemRequest.getPricePerDay();
        if (pricePerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid pricePerDay value");
        }

        BigDecimal subTotal = pricePerDay.multiply(BigDecimal.valueOf(totalDays));

        RentalItem createRentalItem = MappingProfile.mapToRentalItemEntity(rentalItemRequest);
        createRentalItem.setTotalDays(totalDays); // Set totalDays in the entity
        createRentalItem.setSubTotal(subTotal); // Set subTotal in the entity
        RentalItem savedRentalItem = rentalItemRepository.save(createRentalItem);

        return MappingProfile.mapToRentalItemDto(savedRentalItem);
    }

    @Override
    public RentalItemResponseDto updateRentalItem(Long rentalItemId, RentalItemRequestDto rentalItemRequest) {
        RentalItem rentalItem = rentalItemRepository.findById(rentalItemId)
                .orElseThrow(() -> new RentalItemNotFoundException("RentalItem not found"));

        LocalDateTime newPickupDateTime = rentalItemRequest.getPickupDateTime();
        LocalDateTime newReturnDateTime = rentalItemRequest.getReturnDateTime();

        if (newPickupDateTime == null || newReturnDateTime == null || newPickupDateTime.isAfter(newReturnDateTime)) {
            throw new IllegalArgumentException("Invalid new pickupDateTime or new returnDateTime");
        }

        rentalItem.setPickupDateTime(newPickupDateTime);
        rentalItem.setReturnDateTime(newReturnDateTime);

        long hours = newPickupDateTime.until(newReturnDateTime, java.time.temporal.ChronoUnit.HOURS);
        int totalDays = (int) Math.ceil(hours / 24.0);

        BigDecimal pricePerDay = rentalItemRequest.getPricePerDay();
        BigDecimal subTotal = pricePerDay.multiply(BigDecimal.valueOf(totalDays));

        rentalItem.setTotalDays(totalDays);
        rentalItem.setSubTotal(subTotal);

        rentalItem.setProductId(rentalItemRequest.getProductId());
        rentalItem.setPricePerDay(rentalItemRequest.getPricePerDay());

        return MappingProfile.mapToRentalItemDto(rentalItemRepository.save(rentalItem));
    }
    @Override
    public void deleteRentalItemById(Long rentalItemId) {
        var rentalItem = rentalItemRepository.findById(rentalItemId)
                .orElseThrow(() -> new RentalItemNotFoundException("RentalItem not found"));
        rentalItemRepository.delete(rentalItem);

    }
}
