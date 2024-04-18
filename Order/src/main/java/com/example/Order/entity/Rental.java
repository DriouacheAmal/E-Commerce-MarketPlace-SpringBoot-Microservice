package com.example.Order.entity;

import com.example.Order.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;
    private Long customerId;
    private Date orderDate;
    private BigDecimal totalPrice;
    private RentalStatus rentalStatus;
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<RentalItem> rentalItems;

}
