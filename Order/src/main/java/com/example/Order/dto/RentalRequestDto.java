package com.example.Order.dto;

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
public class RentalRequestDto {
    private Long rentalId;
    private Long customerId;
    private Date orderDate;
    //private BigDecimal totalPrice;

    private RentalStatus rentalStatus;
    private List<RentalItemRequestDto> rentalItems;
}
