package com.example.Order.controller;

import com.example.Order.dto.RentalItemRequestDto;
import com.example.Order.dto.RentalItemResponseDto;
import com.example.Order.exception.RentalItemAlreadyReservedException;
import com.example.Order.exception.RentalItemNotAvailableException;
import com.example.Order.exception.RentalItemNotFoundException;
import com.example.Order.service.RentalItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/orders/api/rentalItems")
public class RentalItemController {
    @Autowired
    private final RentalItemService rentalItemService;

    @GetMapping("/{rentalItemId}")
    public ResponseEntity<?> getRentalItemById(@PathVariable Long rentalItemId){
        try {
            RentalItemResponseDto responseDto = rentalItemService.getRentalItemById(rentalItemId);
            return ResponseEntity.ok(responseDto);
        } catch (RentalItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RentalItem with ID " + rentalItemId + " not found.");
        }
    }
    /*@PostMapping("/create")
    /*public ResponseEntity<RentalItemResponseDto> createRentalItem(@RequestBody RentalItemRequestDto rentalItemRequest){
        RentalItemResponseDto createRentalItem = rentalItemService.createRentalItem(rentalItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRentalItem);
    }

     */

    /*public ResponseEntity<?> createRentalItem(@RequestBody RentalItemRequestDto rentalItemRequest) {
        try {
            RentalItemResponseDto responseDto = rentalItemService.createRentalItem(rentalItemRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (RentalItemNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (RentalItemAlreadyReservedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
    @PutMapping("/{rentalItemId}")
    public ResponseEntity<?> updateRentalItem(@PathVariable  Long rentalItemId, @RequestBody RentalItemRequestDto rentalItemRequest){
        try {
            RentalItemResponseDto updatedRentalItem = rentalItemService.updateRentalItem(rentalItemId, rentalItemRequest);
            return ResponseEntity.ok(updatedRentalItem);
        } catch (RentalItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RentalItem with ID " + rentalItemId + " not found.");
        }
    }

     */
    @DeleteMapping("/{rentalItemId}")
    public ResponseEntity<?> deleteRentalItemById(@PathVariable Long rentalItemId){
        try {
            rentalItemService.deleteRentalItemById(rentalItemId);
            return ResponseEntity.ok("RentalItem with ID " + rentalItemId + " has been deleted.");
        } catch (RentalItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RentalItem with ID " + rentalItemId + " not found.");
        }
    }

}
