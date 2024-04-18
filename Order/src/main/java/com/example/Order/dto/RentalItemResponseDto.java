package com.example.Order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalItemResponseDto {
    private Long rentalItemId;
    private Long productId;
    private Long rentalId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
    private int totalDays;
    private BigDecimal pricePerDay;
    private BigDecimal subTotal;

}




