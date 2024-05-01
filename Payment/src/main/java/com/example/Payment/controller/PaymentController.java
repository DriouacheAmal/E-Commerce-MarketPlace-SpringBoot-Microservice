package com.example.Payment.controller;

import com.example.Payment.dto.PaymentDto;
import com.example.Payment.exception.PaymentNotFoundException;
import com.example.Payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RestControllerAdvice("/api/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> paymentDtos = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentDtos);
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto savedPayment = paymentService.SavePayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long paymentId) {
        try {
            PaymentDto paymentDto = paymentService.getPaymentById(paymentId);
            return ResponseEntity.ok(paymentDto);
        }catch (PaymentNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment Id " +paymentId+ " not found");
        }

    }
}

