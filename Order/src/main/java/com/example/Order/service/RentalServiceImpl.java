package com.example.Order.service;

import com.example.Order.dto.*;
import com.example.Order.entity.Rental;
import com.example.Order.entity.RentalItem;
import com.example.Order.exception.*;
import com.example.Order.mapper.MappingProfile;
import com.example.Order.repository.ProductClient;
import com.example.Order.repository.RentalItemRepo;
import com.example.Order.repository.RentalRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService{
    @Autowired
    private final RentalRepo rentalRepository;
    private final RentalItemRepo rentalItemRepository;
    private ProductClient productClient;
    @Override
    public List<RentalResponseDto> getAllRentals() {
        List<RentalResponseDto> rentalResponseDtos = rentalRepository.findAll()
                .stream()
                .map(MappingProfile::mapToRentalDto)
                .collect(Collectors.toList());

        rentalResponseDtos.forEach(this::calculateTotalPrice);
        return rentalResponseDtos;
    }

    @Override
    public RentalResponseDto getRentalById(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));
        RentalResponseDto rentalResponseDto = MappingProfile.mapToRentalDto(rental);
        calculateTotalPrice(rentalResponseDto);
        return rentalResponseDto;
    }

    /*public RentalResponseDto createRental(RentalRequestDto rentalRequest) {
        Rental createRental = MappingProfile.mapToRentalEntity(rentalRequest);
        Rental savedRental = rentalRepository.save(createRental);
        RentalResponseDto rentalResponse
        Dto = MappingProfile.mapToRentalDto(savedRental);
        calculateTotalPrice(rentalResponseDto);
        return rentalResponseDto;
    }

     */


    @Override
    /*public RentalResponseDto createRental(RentalRequestDto rentalRequest) {
        // Create the Rental entity from the rental request DTO
        Rental createRental = MappingProfile.mapToRentalEntity(rentalRequest);

        // List to store rental items for the rental
        List<RentalItem> rentalItems = new ArrayList<>();
        List<RentalItemRequestDto> rentalItemRequests = rentalRequest.getRentalItems();

        // Loop through each rental item request DTO
        for (RentalItemRequestDto rentalItemRequest : rentalItemRequests) {
            // Perform verifications for each rental item
            Long rentalItemId = rentalItemRequest.getRentalItemId();
            Long productId = rentalItemRequest.getProductId();
            LocalDateTime pickupDateTime = rentalItemRequest.getPickupDateTime();
            LocalDateTime returnDateTime = rentalItemRequest.getReturnDateTime();

            // Perform the necessary checks (similar to the original createRentalItem method)
            validateRentalItem(rentalItemId, productId, pickupDateTime, returnDateTime);

            // Calculate the total duration and price
            long hours = pickupDateTime.until(returnDateTime, java.time.temporal.ChronoUnit.HOURS);
            int totalDays = (int) Math.ceil(hours / 24.0);

            BigDecimal pricePerDay = rentalItemRequest.getPricePerDay();
            BigDecimal subTotal = pricePerDay.multiply(BigDecimal.valueOf(totalDays));

            // Create the RentalItem entity from the rental item request DTO
            RentalItem rentalItem = MappingProfile.mapToRentalItemEntity(rentalItemRequest);
            rentalItem.setTotalDays(totalDays);
            rentalItem.setSubTotal(subTotal);

            // Associate the RentalItem with the Rental
            rentalItem.setRental(createRental);
            rentalItems.add(rentalItem);
        }

        // Add all rental items to the rental
        createRental.setRentalItems(rentalItems);

        // Save the rental (this will also save the rental items due to the cascade configuration)
        Rental savedRental = rentalRepository.save(createRental);

        // Map the saved Rental entity to a DTO and calculate the total price
        RentalResponseDto rentalResponseDto = MappingProfile.mapToRentalDto(savedRental);
        calculateTotalPrice(rentalResponseDto);

        return rentalResponseDto;
    }

     */

    public RentalResponseDto createRental(RentalRequestDto rentalRequest) {
        // Create the Rental entity from the rental request DTO
        Rental createRental = MappingProfile.mapToRentalEntity(rentalRequest);

        // List to store rental items for the rental
        List<RentalItem> rentalItems = new ArrayList<>();
        List<RentalItemRequestDto> rentalItemRequests = rentalRequest.getRentalItems();

        // Loop through each rental item request DTO
        for (RentalItemRequestDto rentalItemRequest : rentalItemRequests) {
            // Perform verifications for each rental item
            Long rentalItemId = rentalItemRequest.getRentalItemId();
            Long productId = rentalItemRequest.getProductId();
            LocalDateTime pickupDateTime = rentalItemRequest.getPickupDateTime();
            LocalDateTime returnDateTime = rentalItemRequest.getReturnDateTime();

            // Perform the necessary checks (similar to the original createRentalItem method)
            validateRentalItem(rentalItemId, productId, pickupDateTime, returnDateTime);

            // Fetch product details from the Catalog service using ProductClient
            ProductDto productDto = productClient.getProductById(productId);

            // Calculate the total duration and price
            long hours = pickupDateTime.until(returnDateTime, java.time.temporal.ChronoUnit.HOURS);
            int totalDays = (int) Math.ceil(hours / 24.0);

            // Use price from the fetched product details
            BigDecimal pricePerDay = productDto.getPrice();

            BigDecimal subTotal = pricePerDay.multiply(BigDecimal.valueOf(totalDays));

            // Create the RentalItem entity from the rental item request DTO
            RentalItem rentalItem = MappingProfile.mapToRentalItemEntity(rentalItemRequest);
            rentalItem.setTotalDays(totalDays);
            rentalItem.setSubTotal(subTotal);

            // Set the product information in the RentalItem entity
            rentalItem.setProductName(productDto.getProductName());
            //rentalItem.setImageUrl(productDto.getImageUrl());
            productDto.setImageUrl(productDto.getImageUrl());
            rentalItem.setPrice(productDto.getPrice());

            // Associate the RentalItem with the Rental
            rentalItem.setRental(createRental);
            rentalItems.add(rentalItem);
        }

        // Add all rental items to the rental
        createRental.setRentalItems(rentalItems);

        // Save the rental (this will also save the rental items due to the cascade configuration)
        Rental savedRental = rentalRepository.save(createRental);

        // Map the saved Rental entity to a DTO and calculate the total price
        RentalResponseDto rentalResponseDto = MappingProfile.mapToRentalDto(savedRental);
        calculateTotalPrice(rentalResponseDto);

        return rentalResponseDto;
    }



    private void validateRentalItem(Long rentalItemId, Long productId, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        // Your original validation logic goes here

        // Ensure the rental item times are valid
        if (pickupDateTime == null || returnDateTime == null || pickupDateTime.isAfter(returnDateTime)) {
            throw new IllegalArgumentException("Invalid pickupDateTime or returnDateTime");
        }

        // Ensure rental item duration is at least 24 hours
        long durationInHours = java.time.Duration.between(pickupDateTime, returnDateTime).toHours();
        if (durationInHours < 24) {
            throw new InvalidRentalRequestException("Pickup and return times must be at least 24 hours apart.");
        }

        // Ensure the reservation times are not in the past
        LocalDateTime currentTime = LocalDateTime.now();
        if (pickupDateTime.isBefore(currentTime) || returnDateTime.isBefore(currentTime)) {
            throw new InvalidRentalRequestException("Reservation times cannot be in the past.");
        }

        // Check availability
        if (!isCarAvailableForReservation(rentalItemId, productId, pickupDateTime, returnDateTime)) {
            throw new RentalItemNotAvailableException("The car is not available for reservation during the specified period.");
        }

        if (!isCarAvailableForReservation(null, productId, pickupDateTime, returnDateTime)) {
            throw new RentalItemAlreadyReservedException("The car is already reserved by another customer during the specified period.");
        }
    }

    private boolean isCarAvailableForReservation(Long rentalItemId, Long productId, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        List<RentalItem> overlappingRentals = rentalItemRepository.findOverlappingRentals(rentalItemId, productId, pickupDateTime, returnDateTime);
        return overlappingRentals.isEmpty();
    }


    @Override
    public RentalResponseDto updateRental(Long rentalId, RentalRequestDto rentalRequest) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));

        rental.setRentalStatus(rentalRequest.getRentalStatus());
        //rental.setOrderDate(rentalRequest.getOrderDate());

        Rental updatedRental = rentalRepository.save(rental);

        RentalResponseDto rentalResponseDto = MappingProfile.mapToRentalDto(updatedRental);

        calculateTotalPrice(rentalResponseDto);

        return rentalResponseDto;
    }


    @Override
    public void cancelRentalById(Long rentalId) {
        var rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));
        rentalRepository.delete(rental);
    }

    @Override
    public void deleteRentalItemById(Long rentalId, Long rentalItemId) {
        var rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));
        List<RentalItem> rentalItems = rental.getRentalItems();
        RentalItem rentalItemToRemove = null;

        for(RentalItem item : rentalItems){
            if(item.getRentalItemId().equals(rentalItemId)){
                rentalItemToRemove = item;
                break;
            }
        }
        if(rentalItemToRemove == null){
            throw new RentalItemNotFoundException("Rental item with ID " + rentalItemId + " not found in rental " + rentalId + ".");

        }
        //rentalItemRepository.deleteById(rentalItemId);
        //rentalRepository.deleteById(rentalItemId);
        rentalItems.remove(rentalItemToRemove);

    }



    @Override
    public List<RentalResponseDto> getRentalHistoryByCustomerId(Long customerId) {
        List<RentalResponseDto> rentalHistory = rentalRepository.findByCustomerId(customerId)
                .stream()
                .map(MappingProfile::mapToRentalDto)
                .collect(Collectors.toList());
        if (rentalHistory.isEmpty()) {
            throw new RentalNotFoundException("Rental history not found for customer with ID: " + customerId);
        }

        rentalHistory.forEach(this::calculateTotalPrice);
        return rentalHistory;
    }

    private void calculateTotalPrice(RentalResponseDto rental) {
        List<RentalItemResponseDto> rentalItems = rental.getRentalItems();
        if (rentalItems != null) {
            BigDecimal totalPrice = rentalItems.stream()
                    .map(RentalItemResponseDto::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            rental.setTotalPrice(totalPrice);
        } else {
            rental.setTotalPrice(BigDecimal.ZERO);
        }
    }

}
