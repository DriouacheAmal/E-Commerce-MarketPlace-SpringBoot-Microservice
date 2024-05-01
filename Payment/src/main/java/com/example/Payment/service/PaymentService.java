package com.example.Payment.service;

import com.example.Payment.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> getAllPayments();
    PaymentDto SavePayment(PaymentDto paymentDto);
    PaymentDto getPaymentById(Long paymentId);
}
