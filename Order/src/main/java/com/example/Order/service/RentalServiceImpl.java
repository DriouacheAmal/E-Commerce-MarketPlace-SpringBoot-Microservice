package com.example.Order.service;

import com.example.Order.dto.RentalItemResponseDto;
import com.example.Order.dto.RentalRequestDto;
import com.example.Order.dto.RentalResponseDto;
import com.example.Order.entity.Rental;
import com.example.Order.exception.RentalNotFoundException;
import com.example.Order.mapper.MappingProfile;
import com.example.Order.repository.RentalRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService{
    @Autowired
    private final RentalRepo rentalRepository;
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

    @Override
    public RentalResponseDto createRental(RentalRequestDto rentalRequest) {
        Rental createRental = MappingProfile.mapToRentalEntity(rentalRequest);
        Rental savedRental = rentalRepository.save(createRental);
        RentalResponseDto rentalResponseDto = MappingProfile.mapToRentalDto(savedRental);
        calculateTotalPrice(rentalResponseDto);
        return rentalResponseDto;
    }

    @Override
    public RentalResponseDto updateRental(Long rentalId, RentalRequestDto rentalRequest) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));

        rental.setRentalStatus(rentalRequest.getRentalStatus());
        rental.setOrderDate(rentalRequest.getOrderDate());

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

    /*private void calculateTotalPrice(RentalResponseDto rental) {
        BigDecimal totalPrice = rental.getRentalItems().stream()
                .map(RentalItemResponseDto::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        rental.setTotalPrice(totalPrice);
    }

     */

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
