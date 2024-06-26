package com.example.Order.dto;

import com.example.Order.entity.RentalItem;
import com.example.Order.enums.RentalStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponseDto {
    private Long rentalId;
    private Long customerId;
    private Date orderDate;
    private List<RentalItemResponseDto> rentalItems;
    private BigDecimal totalPrice;
    private RentalStatus rentalStatus;
}
