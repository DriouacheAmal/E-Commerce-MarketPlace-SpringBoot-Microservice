package com.example.Payment.service;

import com.example.Payment.dto.PaymentDto;
import com.example.Payment.entity.Payment;
import com.example.Payment.exception.PaymentNotFoundException;
import com.example.Payment.mapper.MappingProfile;
import com.example.Payment.repository.PaymentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepo paymentRepository;
    @Override
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(MappingProfile::mapToPaymentDto)
                .collect(Collectors.toList());
    }



    @Override
    public PaymentDto SavePayment(PaymentDto paymentDto) {
        Payment payment = MappingProfile.mapToPaymentEntity(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        return MappingProfile.mapToPaymentDto(savedPayment);
    }

    @Override
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with Id "+ paymentId +"not found"));
        return MappingProfile.mapToPaymentDto(payment);
    }
}
