package com.example.Payment.dto;

import com.example.Payment.entity.PaymentStatus;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private Long userId;
    private Long orderId;
    private Boolean isPayed;
    private PaymentStatus paymentStatus;
}

