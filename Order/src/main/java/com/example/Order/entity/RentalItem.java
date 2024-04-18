package com.example.Order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalItemId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "pickup_date_time")
    private LocalDateTime pickupDateTime;
    @Column(name = "return_date_time")
    private LocalDateTime returnDateTime;
    private BigDecimal subTotal;
    private int totalDays;
    private BigDecimal pricePerDay;
    @ManyToOne
    private Rental rental;
}
