package com.example.Order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalItemRequestDto {
    private Long rentalItemId;
    private Long productId;
    private Long rentalId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
    private BigDecimal pricePerDay;
}
