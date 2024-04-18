package com.example.Order.controller;

import com.example.Order.dto.RentalItemRequestDto;
import com.example.Order.dto.RentalItemResponseDto;
import com.example.Order.dto.RentalRequestDto;
import com.example.Order.dto.RentalResponseDto;
import com.example.Order.exception.RentalItemNotFoundException;
import com.example.Order.exception.RentalNotFoundException;
import com.example.Order.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rental")
public class RentalController {
    @Autowired
    private final RentalService rentalService;


    @GetMapping("/")
    public ResponseEntity<List<RentalResponseDto>> getAllRentals(){
        List<RentalResponseDto> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<?> getRentalById(@PathVariable Long rentalId) throws Exception{
        try {
            RentalResponseDto rentalDto = rentalService.getRentalById(rentalId);
            return ResponseEntity.ok(rentalDto);
        } catch (RentalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental with ID " + rentalId + " not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<RentalResponseDto> createRental(@RequestBody RentalRequestDto rentalRequest){
        RentalResponseDto createRental = rentalService.createRental(rentalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRental);
    }

    @PutMapping("/{rentalId}")
    public ResponseEntity<?> updateRental(@PathVariable Long rentalId, @RequestBody RentalRequestDto rentalRequest){
        try {
            RentalResponseDto updateRental = rentalService.updateRental(rentalId, rentalRequest);
            return ResponseEntity.ok(updateRental);
        } catch (RentalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental with ID " + rentalId + " not found.");
        }
    }

    @DeleteMapping("/{rentalId}")
    public ResponseEntity<?> cancelRentalById(@PathVariable Long rentalId){
        try {
            rentalService.cancelRentalById(rentalId);
            return ResponseEntity.ok("Rental with ID " + rentalId + " has been deleted.");
        } catch (RentalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental with ID " + rentalId + " not found.");
        }
    }
    @GetMapping("/history/{customerId}")
    public ResponseEntity<?> getRentalHistoryByCustomerId(@PathVariable Long customerId) {
        List<RentalResponseDto> rentalHistory = rentalService.getRentalHistoryByCustomerId(customerId);
        return new ResponseEntity<>(rentalHistory, HttpStatus.OK);


        /*try {
            List<RentalResponseDto> rentalHistory = rentalService.getRentalHistoryByCustomerId(customerId);
            return ResponseEntity.ok(rentalHistory);
        } catch (RentalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body();
        }

         */
    }

}
